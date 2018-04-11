package com.selfJwt.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class ErrorUtils {

	@Autowired
	private ErrorMessage errorMessage;

	@Autowired
	Error error;

	@Autowired
	Gson gson;

	public String buildError(final HttpStatus status, String message) {
		final List<Error> errors = new ArrayList<Error>();
		errors.add(error.build(status.value(), status.getReasonPhrase(), message));
		errorMessage.setErrors(errors);
		return gson.toJson(errorMessage);
	}

	public String buildError(final int statusCode, String message) {
		HttpStatus status = HttpStatus.valueOf(statusCode);
		final List<Error> errors = new ArrayList<Error>();
		errors.add(error.build(status.value(), status.getReasonPhrase(), message));
		errorMessage.setErrors(errors);
		errorMessage.setStatus(AppStatus.ERROR);
		return gson.toJson(errorMessage);
	}

}
