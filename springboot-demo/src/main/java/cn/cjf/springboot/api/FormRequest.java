package cn.cjf.springboot.api;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class FormRequest {

    @NotEmpty
    @Email
    private String email;

    @Pattern(regexp = "[a-zA-Z0-9_]{6,30}")
    private String name;

    @Min(5)
    @Max(199)
    private int age;
}