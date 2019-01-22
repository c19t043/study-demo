package cn.cjf.springboot.api;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@PasswordEquals
@Data
public class RegisterForm {

    @NotEmpty
    @Length(min = 5, max = 30)
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordConfirm;
}