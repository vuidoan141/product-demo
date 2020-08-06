package com.vuidoan.product.specification;

import com.vuidoan.product.entity.Category;
import com.vuidoan.product.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

public class ProductSpecification {
    static Logger logger = LoggerFactory.getLogger(ProductSpecification.class);

    public static String containLowerCase(String searchText) {
        return "%" + searchText.toLowerCase() + "%";
    }

    public static Specification<Product> getCombineQuery(Map<String, String> queryMap) {
        return (root, query, cb) -> {
            query.distinct(true);
            Specification<Product> specification =
                    where(hasDeletedIsFalse())
                            .and(hasNameContains(queryMap))
                            .and(hasPriceInRange(queryMap))
                            .and(hasCategory(queryMap));

            return specification.toPredicate(root, query, cb);
        };
    }

    public static Specification<Product> hasDeletedIsFalse() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);
    }

    public static Specification<Product> hasNameContains(Map<String, String> queryMap) {
        String searchText = queryMap.get("name");
        if (searchText == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), containLowerCase(searchText));
    }

    public static Specification<Product> hasPriceInRange(Map<String, String> queryMap) {
        String minPriceFromQuery = queryMap.get("minPrice");
        String maxPriceFromQuery = queryMap.get("maxPrice");
        double minPrice = Double.parseDouble(Optional.ofNullable(minPriceFromQuery).orElse("0.0"));
        Specification<Product> specification = (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"),
                minPrice);

        if (maxPriceFromQuery != null) {
            double maxPrice = Double.parseDouble(maxPriceFromQuery);
            return specification.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        return specification;

    }

    public static Specification<Product> hasCategory(Map<String, String> queryMap) {
        String categoryName = queryMap.get("category");

        return (root, query, cb) -> {
            if (categoryName == null) {
                return null;
            }
            Join<Product, Category> category = root.join("category", JoinType.INNER);
            return cb.equal(category.get("name"), categoryName);
        };
    }
}