package cn.cjf.springboot.api;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {PasswordEqualsValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordEquals {

    String message() default "Password is not the same";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}