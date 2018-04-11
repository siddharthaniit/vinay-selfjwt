package com.selfJwt;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.selfJwt.model.Privilege;
import com.selfJwt.model.Roles;
import com.selfJwt.model.User;
import com.selfJwt.service.PrivilegesService;
import com.selfJwt.service.RolesService;
import com.selfJwt.service.UserService;

/**
 * @author vinayr
 *
 */
@SpringBootApplication
public class SelfJwtApplication implements CommandLineRunner {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddl;

	@Value("${admin.email}")
	private String adminEmail;

	@Value("${admin.password}")
	private String adminPassword;

	@Value("${admin.name}")
	private String adminName;

	@Autowired
	private PrivilegesService privilegesService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SelfJwtApplication.class, args);
	}
	
	private static Logger logger = LogManager.getLogger(SelfJwtApplication.class);
	
	@Override
	public void run(String... arg0) throws Exception {
		logger.info("Starting Spring Boot application..");
		if (ddl.equals("create")) {
			Privilege privilege_read = new Privilege();
			privilege_read.setName("READ");
			Privilege privilege_Write = new Privilege();
			privilege_Write.setName("WRITE");
			Privilege privilege_CP = new Privilege();
			privilege_CP.setName("Change password");
			Privilege privilege_RP = new Privilege();
			privilege_RP.setName("Reset password");
			Privilege read = privilegesService.create(privilege_read);
			Privilege write = privilegesService.create(privilege_Write);
			Privilege cp = privilegesService.create(privilege_CP);
			Privilege rp = privilegesService.create(privilege_RP);
			Set<Privilege> admin_privilages = new HashSet<Privilege>();
			admin_privilages.add(write);
			admin_privilages.add(read);
			admin_privilages.add(cp);
			admin_privilages.add(rp);
			Roles admin = new Roles();
			admin.setRolesName("ADMIN");
			admin.setPrivileges(admin_privilages);
			Set<Privilege> user_privilages = new HashSet<Privilege>();
			user_privilages.add(read);
			user_privilages.add(write);
			Roles user = new Roles();
			user.setRolesName("USER");
			user.setPrivileges(user_privilages);
			Set<Privilege> merchant_privilages = new HashSet<Privilege>();
			merchant_privilages.add(write);
			merchant_privilages.add(read);
			merchant_privilages.add(cp);
			Roles merchant = new Roles();
			merchant.setRolesName("MERCHANT");
			merchant.setPrivileges(merchant_privilages);
			Roles roleAdmin = rolesService.create(admin);
			rolesService.create(user);
			rolesService.create(merchant);
			User defaultAdmin = new User();
			defaultAdmin.setEmail(adminEmail);
			defaultAdmin.setPassword(adminPassword);
			defaultAdmin.setAge(25);
			defaultAdmin.setName(adminName);
			HashSet<Roles> hashSet = new HashSet<Roles>();
			hashSet.add(roleAdmin);
			defaultAdmin.setRoles(hashSet);
			userService.create(defaultAdmin);
		}
	}
}
