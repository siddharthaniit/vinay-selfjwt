package com.selfJwt.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.selfJwt.model.Privilege;
import com.selfJwt.model.Roles;
import com.selfJwt.service.RolesService;

/**
 * @author vinayr
 *
 */
@Component
public class BasePermissionEvaluator implements PermissionEvaluator {

	@Autowired
	private RolesService service;

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if (authentication != null && targetDomainObject instanceof String) {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority grantedAuthority : authorities) {
				Roles byName = service.getByName(grantedAuthority + "");
				Set<Privilege> privileges = byName.getPrivileges();
				for (Privilege privilege : privileges) {
					if (permission instanceof String && privilege.getName().equals(permission)) {
						return true;
					}
				}
			}
			return false;
		}
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		return false;
	}

}
