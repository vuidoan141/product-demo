package com.vuidoan.product.dto;

import com.vuidoan.product.validation.annotation.UniqueCategory;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@UniqueCategory(message = "Category '${validatedValue.getName()}' is already existed!")
public class CategoryDTO {
    private int id;

    @NotBlank
    private String name;
}
