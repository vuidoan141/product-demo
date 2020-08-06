package com.vuidoan.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vuidoan.product.validation.annotation.UniqueUser;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Category extends Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private String name;
}
