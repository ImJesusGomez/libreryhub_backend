package com.jesus.backend.data;

import com.jesus.backend.model.Employee;
import com.jesus.backend.model.Role;
import com.jesus.backend.repository.EmployeeRepository;
import com.jesus.backend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(EmployeeRepository employeeRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Creamos el rol de admin
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_ADMIN");
                    return roleRepository.save(newRole);
                });

        // Creamos el rol de employee
        Role employeeRole = roleRepository.findByName("ROLE_EMPLOYEE")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_EMPLOYEE");
                    return roleRepository.save(newRole);
                });

        // Primero, verificamos si existe un usuario tipo admin, en caso de no haberlo lo creamos
        if (employeeRepository.findByEmail("admin@gmail.com").isEmpty()) {
            // Creamos el admin
            Employee admin = new Employee();
            admin.setFirstName("admin");
            admin.setLastName("principal");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123456"));
            admin.setPhoneNumber("1234567890");

            // Agregamos sus roles
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminRoles.add(employeeRole);
            admin.setRoles(adminRoles);

            // Lo guardamos
            employeeRepository.save(admin);
        }

        // Primero, verificamos si existe un usuario tipo employee, en caso de no haberlo, lo creamos
        if (employeeRepository.findByEmail("employee@gmail.com").isEmpty()) {
            // Creamos el admin
            Employee employee = new Employee();
            employee.setFirstName("employee");
            employee.setLastName("principal");
            employee.setEmail("employee@gmail.com");
            employee.setPassword(passwordEncoder.encode("employee123456"));
            employee.setPhoneNumber("12345678906");

            // Agregamos sus roles
            Set<Role> employeeRoles = new HashSet<>();
            employeeRoles.add(employeeRole);
            employee.setRoles(employeeRoles);

            // Lo guardamos
            employeeRepository.save(employee);
        }

    }
}
