package com.infotech.book.ticket.app.validator.custom;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Payload;

import javax.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SourceAndDestinationValidator.class)
public @interface SourceAndDestinationValidation {

	String message() default "not the valid source or destination";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
