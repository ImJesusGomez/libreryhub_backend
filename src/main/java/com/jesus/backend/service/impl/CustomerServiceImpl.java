package com.jesus.backend.service.impl;

import com.jesus.backend.dto.request.CustomerCreateRequestDTO;
import com.jesus.backend.dto.request.CustomerUpdateRequestDTO;
import com.jesus.backend.dto.response.CustomerResponseDTO;
import com.jesus.backend.exception.ResourceAlreadyExistsException;
import com.jesus.backend.exception.ResourceNotFoundException;
import com.jesus.backend.mapper.CustomerMapper;
import com.jesus.backend.model.Customer;
import com.jesus.backend.model.enums.CustomerStatus;
import com.jesus.backend.repository.CustomerRepository;
import com.jesus.backend.service.CustomerService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final FileStorageService fileStorageService;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, FileStorageService fileStorageService, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.fileStorageService = fileStorageService;
        this.customerMapper = customerMapper;
    }

    @Transactional()
    @Override
    public CustomerResponseDTO register(CustomerCreateRequestDTO dto) {
        // Creamos el cliente
        Customer customerToCreate = customerMapper.toEntity(dto);

        // Validamos que sean únicos los atributos
        if (customerRepository.existsByEmail(customerToCreate.getEmail())) throw new ResourceAlreadyExistsException("Customer already exists with email: " + dto.email());
        if (customerRepository.existsByPhoneNumber(customerToCreate.getPhoneNumber())) throw new ResourceAlreadyExistsException("Customer already exists with phone number: " + dto.phoneNumber());

        // Modificamos su status
        customerToCreate.setStatus(CustomerStatus.ACTIVE);

        // Lo guardamos
        Customer customerCreated = customerRepository.save(customerToCreate);

        // Retornamos la respuesta
        return customerMapper.toCustomerResponseDTO(customerCreated);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponseDTO findById(UUID id) {
        return customerMapper.toCustomerResponseDTO(customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer Not Found")));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerResponseDTO> getCustomers(String firstName, String lastName, int page, int size, String sortBy, String sortDir) {
        Specification<Customer> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (firstName != null && !firstName.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
            }

            if (lastName != null && !lastName.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return customerRepository.findAll(spec, pageable).map(customerMapper::toCustomerResponseDTO);
    }

    @Transactional
    @Override
    public CustomerResponseDTO update(UUID id, CustomerUpdateRequestDTO dto) {
        // Buscamos el cliente a actualizar
        Customer customerToUpdate = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        // Limpiamos los datos
        String normalizeEmail = customerMapper.normalizeEmail(dto.email());
        String normalizePhoneNumber = customerMapper.normalizePhoneNumber(dto.phoneNumber());

        // Verificamos que los datos sean únicos
        if (normalizeEmail != null && !normalizeEmail.equals(customerToUpdate.getEmail()) && customerRepository.existsByEmail(normalizeEmail)) {
            throw new ResourceAlreadyExistsException("Customer already registered with email: " + normalizeEmail);
        }

        if (normalizePhoneNumber != null && !normalizePhoneNumber.equals(customerToUpdate.getPhoneNumber()) && customerRepository.existsByPhoneNumber(normalizePhoneNumber)) {
            throw new ResourceAlreadyExistsException("Customer already registered with phone number: " + normalizePhoneNumber);
        }

        // Actualizamos los datos
        if (dto.firstName() != null) customerToUpdate.setFirstName(customerMapper.normalizeName(dto.firstName()));
        if (dto.lastName() != null) customerToUpdate.setLastName(customerMapper.normalizeName(dto.lastName()));
        if (normalizeEmail != null) customerToUpdate.setEmail(normalizeEmail);
        if (normalizePhoneNumber != null) customerToUpdate.setPhoneNumber(normalizePhoneNumber);
        if (dto.status() != null) customerToUpdate.setStatus(dto.status());

        // Guardamos los cambios
        Customer customerUpdated = customerRepository.save(customerToUpdate);

        // Retornamos la respuesta
        return customerMapper.toCustomerResponseDTO(customerUpdated);
    }

    @Transactional
    @Override
    public void uploadCustomerImage(UUID id, MultipartFile image) {
        // Buscamos el cliente
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer Not Found"));

        // Guardar imagen
        String imageName = fileStorageService.saveImage(image);

        // Guardar nombre en BD
        customer.setImageUrl(imageName);
    }
}
