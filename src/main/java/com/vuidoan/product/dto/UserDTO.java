package com.vuidoan.product.dto;

import com.vuidoan.product.validation.annotation.MatchPassword;
import com.vuidoan.product.validation.annotation.UniqueUser;
import com.vuidoan.product.validation.group.OnCreate;
import com.vuidoan.product.validation.group.OnUpdate;
import com.vuidoan.product.validation.group.OnUpdatePassword;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@UniqueUser(message = "Username '${validatedValue.getUsername()}' is already existed!", groups = {OnUpdate.class,
        OnCreate.class})
@MatchPassword(groups = {OnUpdatePassword.class})
public class UserDTO {
    private int id;

    @NotNull(message = "User is required!")
    @Size(min = 4, max = 32, message = "The username '${validatedValue}' must be between {min} and {max} characters " +
            "long")
    private String username;

    @NotNull(message = "Password is required!")
    @Size(min = 6, message = "The password must be at least {min} character")
    private String password;

    @Size(min = 6, message = "The newPassword must be at least {min} character", groups = {OnUpdatePassword.class})
    private String newPassword;

    @Size(min = 6, message = "The confirmPassword must be at least {min} character", groups = {OnUpdatePassword.class})
    private String confirmPassword;

    private String role;
}
