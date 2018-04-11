/**
 * 
 */
package com.selfJwt.exception;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author karthik
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class Error {

	private int code;
	private String status;
	private String message;
	private long timestamp = Calendar.getInstance().getTimeInMillis();

	public Error(int code, String status, String message) {
		super();
		this.code = code;
		this.status = status;
		this.message = message;
	}

	public Error build(int code, String status, String message) {
		this.code = code;
		this.status = status;
		this.message = message;
		return this;
	}

}
