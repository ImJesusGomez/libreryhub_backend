package com.jesus.backend.security.controller;

import com.jesus.backend.dto.response.EmployeeResponseDTO;
import com.jesus.backend.mapper.EmployeeMapper;
import com.jesus.backend.model.Employee;
import com.jesus.backend.repository.EmployeeRepository;
import com.jesus.backend.repository.RoleRepository;
import com.jesus.backend.security.dto.JwtAuthResponseDTO;
import com.jesus.backend.security.dto.LoginRequestDTO;
import com.jesus.backend.security.dto.RegisterRequestDTO;
import com.jesus.backend.security.jwt.JwtGenerator;
import com.jesus.backend.security.mapper.EmployeeAuthMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeAuthMapper employeeAuthMapper;
    private final EmployeeMapper employeeMapper;

    public AuthController(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator, EmployeeRepository employeeRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, EmployeeAuthMapper employeeAuthMapper, EmployeeMapper employeeMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeAuthMapper = employeeAuthMapper;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginRequestDTO dto) throws ClassNotFoundException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        Employee employee = employeeRepository.findByEmail(dto.email()).orElseThrow(() -> new RuntimeException("Employee not found"));

        EmployeeResponseDTO employeeDTO = employeeMapper.toEmployeeResponseDTO(employee);

        return new ResponseEntity<>(new JwtAuthResponseDTO(token, employeeDTO), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerEmployee(@RequestBody RegisterRequestDTO dto) {
        if (employeeRepository.existsByEmail(dto.email())) {
            return new ResponseEntity<>("Employee already exists with email" + dto.email(), HttpStatus.BAD_REQUEST);
        }

        if (employeeRepository.existsByPhoneNumber(dto.phoneNumber())) {
            return new ResponseEntity<>("Employee already exists with phone number" + dto.phoneNumber(), HttpStatus.BAD_REQUEST);
        }


        Employee employee = employeeAuthMapper.registerDTOtoEmployee(dto);
        employee.setPassword(passwordEncoder.encode(dto.password()));
        employeeRepository.save(employee);

        return new ResponseEntity<>("Employee registered", HttpStatus.OK);
    }
}
