package com.ortegapp.service;

import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import com.ortegapp.model.UserRole;
import com.ortegapp.model.dto.page.PageResponse;
import com.ortegapp.model.dto.producto.ProductoResponse;
import com.ortegapp.model.dto.user.CreateUserRequest;
import com.ortegapp.model.dto.user.EditUserEmailRequest;
import com.ortegapp.model.dto.user.UserResponse;
import com.ortegapp.repository.UserRepository;
import com.ortegapp.search.spec.UserSpecificationBuilder;
import com.ortegapp.search.util.SearchCriteria;
import com.ortegapp.search.util.SearchCriteriaExtractor;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User createUser(CreateUserRequest createUserRequest, EnumSet<UserRole> roles) {
        User user =  User.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .avatar(createUserRequest.getAvatar())
                .fullName(createUserRequest.getFullName())
                .telefono(createUserRequest.getTelefono())
                .email(createUserRequest.getEmail())
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    public User createUserWithUserRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, EnumSet.of(UserRole.USER));
    }

    public User createUserWithAdminRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, EnumSet.of(UserRole.ADMIN));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }


    public EditUserEmailRequest editPhoneName(EditUserEmailRequest user, User userAuth) {

        userAuth.setFullName(user.getFullName());
        userRepository.save(userAuth);
        return EditUserEmailRequest.builder()
                .fullName(user.getFullName())
                .build();
    }


    public Optional<User> editPassword(UUID userId, String newPassword) {

        // Aquí no se realizan comprobaciones de seguridad. Tan solo se modifica

        return userRepository.findById(userId)
                .map(u -> {
                    u.setPassword(passwordEncoder.encode(newPassword));
                    return userRepository.save(u);
                }).or(() -> Optional.empty());

    }

    public void delete(User user) {
        deleteById(user.getId());
    }

    public void deleteById(UUID id) {
        // Prevenimos errores al intentar borrar algo que no existe
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
    }

    public boolean passwordMatch(User user, String clearPassword) {
        return passwordEncoder.matches(clearPassword, user.getPassword());
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);}

    public PageResponse<UserResponse> findAll(String s, Pageable pageable) {
        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(s);
        PageResponse<UserResponse> res = search(params, pageable);
        if (res.getContent().isEmpty()) {
            throw new EntityNotFoundException("Productos no encontrados");
        }
        return res;
    }


    public PageResponse<UserResponse> search(List<SearchCriteria> params, Pageable pageable) {
        UserSpecificationBuilder genericSpecificationBuilder = new UserSpecificationBuilder(params);
        Specification<User> spec = genericSpecificationBuilder.build();
        Page<UserResponse> userResponsePage = userRepository.findAll(spec, pageable).map(UserResponse::fromUser);
        return new PageResponse<>(userResponsePage);

    }






}
