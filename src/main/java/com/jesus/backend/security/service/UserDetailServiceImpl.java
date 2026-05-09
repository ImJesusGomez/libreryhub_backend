package com.jesus.backend.security.service;

import com.jesus.backend.model.Employee;
import com.jesus.backend.model.Role;
import com.jesus.backend.repository.EmployeeRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    public UserDetailServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca el employee en la base de datos. si no existe, lanza una excepción
        Employee employee = employeeRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with email: " + username));

        // Devuelve un objeto UserDetails que Spring Security usa para la autenticación
        return new User(
                employee.getEmail(),
                employee.getPassword(),
                mapRolesToAuthorities(employee.getRoles())
        );
    }

    // Convierte los roles del employee en una lista de "autoridades" que entiende Spring Security
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        // Por cada rol, crea una autoridad con su nombre
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }
}
