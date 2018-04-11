package com.selfJwt.exception;

import javax.servlet.http.HttpServletResponse;

//@Data
public class AppStatus {
	public static String SUCCESS = "success";
	public static String ERROR = "error";

	public static int UNAUTHORIZED = HttpServletResponse.SC_UNAUTHORIZED;
	public static int BAD_REQUEST = HttpServletResponse.SC_BAD_REQUEST;

	// @Getter(value = AccessLevel.NONE)
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
