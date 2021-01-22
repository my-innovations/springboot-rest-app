package com.infotech.book.ticket.app.validator.custom;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SourceAndDestinationValidator implements ConstraintValidator<SourceAndDestinationValidation, String> {

	List<String> places = Arrays.asList("Chennai", "Mumbai","Kolkata","Delhi");
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return places.contains(value);
	}
}