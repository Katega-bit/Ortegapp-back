package com.ortegapp.controller;

import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import com.ortegapp.model.dto.page.PageResponse;
import com.ortegapp.model.dto.producto.ProductoResponse;
import com.ortegapp.model.dto.user.*;
import com.ortegapp.repository.ProductoRepository;
import com.ortegapp.security.jwt.access.JwtProvider;
import com.ortegapp.security.jwt.refresh.RefreshToken;
import com.ortegapp.security.jwt.refresh.RefreshTokenException;
import com.ortegapp.security.jwt.refresh.RefreshTokenRequest;
import com.ortegapp.security.jwt.refresh.RefreshTokenService;
import com.ortegapp.service.ProductoService;
import com.ortegapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProductoService productoService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final ProductoRepository productoRepository;

    @Operation(summary = "Registra un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado un nuevo usuario",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "a9fe0178-8679-1260-8186-7a8332e20001",
                                        "username": "user2",
                                        "avatar": null,
                                        "fullName": "Carlos Ortega",
                                        "createdAt": null
                                    }
                                    """))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido los datos correctamente",
                    content = @Content),
    })
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithUserRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @Operation(summary = "Registra un nuevo usuario administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado un nuevo usuario administrador",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "a9fe0178-8679-1260-8186-7a8332e20001",
                                        "username": "user2",
                                        "avatar": null,
                                        "fullName": "Carlos Ortega",
                                        "createdAt": null
                                    }
                                    """))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido los datos correctamente",
                    content = @Content),
    })
    @PostMapping("/auth/register/admin")
    public ResponseEntity<UserResponse> createUserWithAdminRole(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithAdminRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @Operation(summary = "Logea un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha logeado un usuario",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "a9fe0178-8679-1260-8186-7a8332e20001",
                                        "username": "user2",
                                        "avatar": null,
                                        "fullName": "Carlos Ortega",
                                        "createdAt": null
                                    }
                                    """))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido los datos correctamente",
                    content = @Content),
    })
    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {


        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        refreshTokenService.deleteByUser(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JwtUserResponse.of(user, token, refreshToken.getToken()));


    }
    @Operation(summary = "Refresca el token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha refrescado el token",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhOWZlMDE3OC04Njc5LTEyNjAtODE4Ni03OWQyOTc0OTAwMDAiLCJpYXQiOjE2NzcwOTMwNTEsImV4cCI6MTY3NzA5MzY1MX0.4n63Fi1p5JPRUKtKz78tDS80BeXI3o2T2U1QIBxAYxCmbSlVCcdApC2L_zJxDWf1h7HTHJikuyaypbQb9_V0Ew",
                                        "refreshToken": "955acd0e-c887-4836-b2d5-c3f2807e967c"
                                    }
                                    """))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido los datos correctamente",
                    content = @Content),
    })
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verify)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtProvider.generateToken(user);
                    refreshTokenService.deleteByUser(user);
                    RefreshToken refreshToken2 = refreshTokenService.createRefreshToken(user);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(JwtUserResponse.builder()
                                    .token(token)
                                    .refreshToken(refreshToken2.getToken())
                                    .build());
                })
                .orElseThrow(() -> new RefreshTokenException("Refresh token not found"));

    }


    @Operation(summary = "Cambia la contraseña de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha refrescado el token",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhOWZlMDE3OC04Njc5LTEyNjAtODE4Ni03OWQyOTc0OTAwMDAiLCJpYXQiOjE2NzcwOTMwNTEsImV4cCI6MTY3NzA5MzY1MX0.4n63Fi1p5JPRUKtKz78tDS80BeXI3o2T2U1QIBxAYxCmbSlVCcdApC2L_zJxDWf1h7HTHJikuyaypbQb9_V0Ew",
                                        "refreshToken": "955acd0e-c887-4836-b2d5-c3f2807e967c"
                                    }
                                    """))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el usuario o los datos introducidos son incorrectos",
                    content = @Content),
    })
    @PutMapping("/user/changePassword")
    public ResponseEntity<UserResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                                       @AuthenticationPrincipal User loggedUser) {

        try {
            if (userService.passwordMatch(loggedUser, changePasswordRequest.getOldPassword())) {
                Optional<User> modified = userService.editPassword(loggedUser.getId(), changePasswordRequest.getNewPassword());
                if (modified.isPresent())
                    return ResponseEntity.ok(UserResponse.fromUser(modified.get()));
            } else {

                throw new RuntimeException();
            }
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password Data Error");
        }

        return null;
    }
    @Operation(summary = "Este método muestra una caja por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la caja",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "a9fe0178-8679-1260-8186-7a8332e20001",
                                        "username": "user2",
                                        "avatar": null,
                                        "fullName": "Carlos Ortega",
                                        "createdAt": null
                                    }
                                    """
                            ))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado niguna caja con ese ID",
                    content = @Content),
    })
    @GetMapping("/me")
    public UserResponse getUser(@AuthenticationPrincipal User user){
        return UserResponse.fromUser(user);
    }

    @GetMapping("/user")
    public PageResponse<UserResponse> getAll(@RequestParam(value = "search", defaultValue = "") String search,
                                                 @PageableDefault(size = 20, page = 0) Pageable pageable){

        PageResponse<UserResponse> result = userService.findAll(search, pageable);

        return result;
    }



    @Operation(summary = "Este método borra un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha encontrado el usuario",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class)),
                            examples = @ExampleObject(value = """

                                    """
                            ))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado nigun producto con ese ID",
                    content = @Content),
    })
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(UUID id){
      userService.deleteById(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/melikes/")
    public PageResponse<ProductoResponse> melikes(@AuthenticationPrincipal User user,  @PageableDefault(size = 20, page = 0) Pageable pageable){
       return productoService.melike(user, pageable);

    }




}
