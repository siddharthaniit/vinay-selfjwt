package com.selfJwt.exception;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.selfJwt.Utils.AppKeys;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Component
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	ErrorMessage errorMessage;

	@Autowired
	Error error;

	@Autowired
	ErrorUtils errorUtils;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final List<Error> errors = new ArrayList<Error>();
		for (final FieldError fiErr : ex.getBindingResult().getFieldErrors()) {
			Error error = new Error(status.value(), status.getReasonPhrase(), fiErr.getDefaultMessage());
			errors.add(error);
		}
		for (final ObjectError objErr : ex.getBindingResult().getGlobalErrors()) {
			Error error = new Error(status.value(), status.getReasonPhrase(), objErr.getDefaultMessage());
			errors.add(error);
		}
		errorMessage.setStatus(AppStatus.ERROR);
		errorMessage.setErrors(errors);
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), status);
	}

	protected ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		final List<String> errors = new ArrayList<String>();
		errors.add(ex.getMessage());
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		List<String> errors = new ArrayList<String>();
		errors.add(error);
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		List<String> errors = new ArrayList<String>();
		errors.add(error);
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final List<Error> errors = new ArrayList<Error>();
		Error error = new Error(status.value(), status.getReasonPhrase(), ex.getLocalizedMessage());
		errors.add(error);
		errorMessage.setStatus(AppStatus.ERROR);
		errorMessage.setErrors(errors);
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final List<Error> errors = new ArrayList<Error>();
		Error error = new Error(status.value(), status.getReasonPhrase(), ex.getLocalizedMessage());
		errors.add(error);
		errorMessage.setStatus(AppStatus.ERROR);
		errorMessage.setErrors(errors);
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		errorMessage = buildError(status, "Required request body is missing");
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), status);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(DataIntegrityViolationException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String msg = "Already Exists";
		if (ex.getCause().getClass() == org.hibernate.exception.ConstraintViolationException.class) {
			org.hibernate.exception.ConstraintViolationException cv = (org.hibernate.exception.ConstraintViolationException) ex
					.getCause();
			if (cv.getCause().getClass() == BatchUpdateException.class) {
				BatchUpdateException bu = (BatchUpdateException) cv.getCause();
				String duplicateMsg = bu.getMessage();
				msg = processDuplicateException(msg, duplicateMsg);
			} else if (cv.getCause().getClass() == JdbcSQLException.class) {
				JdbcSQLException bu = (JdbcSQLException) cv.getCause();
				String duplicateMsg = bu.getMessage();
				msg = processDuplicateException(msg, duplicateMsg);
			}

		}
		errorMessage = buildError(status, msg);
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), status);
	}

	private String processDuplicateException(String msg, String duplicateMsg) {
		if (duplicateMsg != null) {
			if (AppKeys.DUPLICATE_MSG_MAP.containsKey(duplicateMsg)) {
				msg = String.join(" ", AppKeys.DUPLICATE_MSG_MAP.get(duplicateMsg), msg);
			} else {
				msg = "Record " + msg;
			}
		}
		return msg;
	}

	@ExceptionHandler(UserException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserException ex, WebRequest request) {
		errorMessage = buildError(ex.status, ex.getMessage());
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), ex.status);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex,final WebRequest request) {
		errorMessage = buildError(HttpStatus.BAD_REQUEST, "An error occured");
		return new ResponseEntity<Object>(errorMessage, HttpStatus.BAD_REQUEST);
	}


	public ErrorMessage buildError(final HttpStatus status, String message) {
		final List<Error> errors = new ArrayList<Error>();
		errors.add(error.build(status.value(), status.getReasonPhrase(), message));
		errorMessage.setStatus(AppStatus.ERROR);
		errorMessage.setErrors(errors);
		return errorMessage;
	}

}