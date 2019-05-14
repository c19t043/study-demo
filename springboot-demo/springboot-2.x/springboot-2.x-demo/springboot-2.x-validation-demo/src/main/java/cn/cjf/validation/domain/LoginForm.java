package cn.cjf.validation.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author chenjunfan
 * @date 2019/5/14
 */
@Data
public class LoginForm {
    @NotBlank(message = "用户名不能空")
    @Email
    private String username;
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,message = "密码长度至少为6位")
    private String password;
}
