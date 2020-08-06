package com.vuidoan.product.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class ProductDTO {
    private int id;

    @NotBlank
    private String name;

    @Positive(message = "Price must be greater than 0")
    private double price;

    @Positive(message = "CategoryId is invalid!")
    private int categoryId;

}
