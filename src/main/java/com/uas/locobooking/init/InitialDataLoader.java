package com.uas.locobooking.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.uas.locobooking.constants.RoleConstant;
import com.uas.locobooking.models.Roles;
import com.uas.locobooking.models.Users;
import com.uas.locobooking.repositories.RolesRepository;
import com.uas.locobooking.repositories.UsersRepository;

@Component
public class InitialDataLoader implements ApplicationRunner {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Cek apakah role sudah ada
        List<Roles> roles = rolesRepository.findAll();
        if (roles.isEmpty()) {
            Roles adminRole = new Roles(null, RoleConstant.ADMIN_ROLE, "Admin role for loco booking system");
            Roles userRole = new Roles(null, RoleConstant.USER_ROLE, "User role for loco booking system");
            rolesRepository.saveAll(List.of(adminRole, userRole));

            // Buat admin pertama
            Users admin = new Users();
            admin.setUsername("admin@locobooking.com");
            admin.setPassword(passwordEncoder.encode("123")); // Default password
            admin.setRoles(adminRole);

            usersRepository.save(admin);
        }
    }
}
