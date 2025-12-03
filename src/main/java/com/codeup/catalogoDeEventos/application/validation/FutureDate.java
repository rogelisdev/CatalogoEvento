package com.codeup.catalogoDeEventos.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom validation annotation to ensure a date is in the future
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDateValidator.class)
@Documented
public @interface FutureDate {

    String message() default "{validation.futuredate}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
