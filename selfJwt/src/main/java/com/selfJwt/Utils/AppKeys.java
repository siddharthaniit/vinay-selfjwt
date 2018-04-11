/**
 * 
 */
package com.selfJwt.Utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author karthik
 *
 */
@Component
public class AppKeys {

	public static String UK_EMAIL = "UK_EMAIL";

	public static String UK_EMAIL_MSG = "User Email already Exists";

	public static final String TOKENHEADER = "X-AUTH-TOKEN";

	public static Map<String, String> DUPLICATE_MSG_MAP = new HashMap<>();

	static {
		DUPLICATE_MSG_MAP.put("UK_BRAND_NAME", "Brand");
		DUPLICATE_MSG_MAP.put("UK_PRODUCT_NAME", "Product");
		DUPLICATE_MSG_MAP.put("UK_CATEGORY_NAME", "Category");
		DUPLICATE_MSG_MAP.put("UK_SHIPPER_NAME", "Shipper");
		DUPLICATE_MSG_MAP.put("UK_ROLE_NAME", "Role");
		DUPLICATE_MSG_MAP.put(UK_EMAIL, UK_EMAIL_MSG);
	}

}
