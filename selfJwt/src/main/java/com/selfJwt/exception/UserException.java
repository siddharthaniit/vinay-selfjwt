/**
 * 
 */
package com.selfJwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author karthik
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserException extends RuntimeException {
	public HttpStatus status;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserException(String message) {
		super(message);
	}

	public UserException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

}
