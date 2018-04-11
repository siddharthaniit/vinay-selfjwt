package com.selfJwt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.selfJwt.Utils.Utils;
import com.selfJwt.model.Roles;
import com.selfJwt.model.User;
import com.selfJwt.repo.UserRepo;

/**
 * @author vinayr
 *
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo repo;

	@Autowired
	private RolesService rolesService;
	
	
	/**
	 * @param user
	 * @return
	 */
	public User register(User user) {
		Roles byName = rolesService.getByName("USER");
		Set<Roles> roles = new HashSet<Roles>();
		roles.add(byName);
		String encode = Utils.passwordEncrypt(user.getPassword());
		user.setPassword(encode);
		user.setRoles(roles);
		User save = repo.save(user);
		return save;
	}

	public User byEmail(String email) {
		User byEmail = repo.findByEmail(email);
		return byEmail;
	}

	public User getById(String id) {
		User findById = repo.findOne(id);
		return findById;
	}

	public ArrayList<User> getAll() {
		Iterable<User> findAll = repo.findAll();
		ArrayList<User> arrayList = new ArrayList<User>();
		findAll.forEach(arrayList::add);
		return arrayList;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User byEmail = repo.findByEmail(email);
		return new org.springframework.security.core.userdetails.User(byEmail.getEmail(), byEmail.getPassword(),
				getAuthority(byEmail));
	}

	private List<SimpleGrantedAuthority> getAuthority(User byEmail) {
		SimpleGrantedAuthority authority = null;
		Collection<Roles> roles = byEmail.getRoles();
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		for (Roles role : roles) {
			if (role.getRolesName().equals("USER")) {
				authority = new SimpleGrantedAuthority(role.getRolesName());
				list.add(authority);
			} else if (role.getRolesName().equals("ADMIN")) {
				authority = new SimpleGrantedAuthority(role.getRolesName());
				list.add(authority);
			} else {
				authority = new SimpleGrantedAuthority(role.getRolesName());
				list.add(authority);
			}
		}
		return list;
	}

	public User create(User user) {
		Roles userRole = rolesService.getByName("ADMIN");
		String encode = Utils.passwordEncrypt(user.getPassword());
		user.setPassword(encode);
		User save = null;
		if (userRole != null) {
			Set<Roles> arrayList = new HashSet<Roles>();
			arrayList.add(userRole);
			user.setRoles(arrayList);
			save = repo.save(user);
		}
		return save;
	}
}
