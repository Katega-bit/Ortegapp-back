package com.ortegapp.validation.annotation;

import com.ortegapp.validation.validator.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Documented
public @interface UniqueEmail {

    String message() default "El email ya esta een uso";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
