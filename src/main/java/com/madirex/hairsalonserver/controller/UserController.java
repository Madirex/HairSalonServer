package com.madirex.hairsalonserver.controller;


import com.madirex.hairsalonserver.config.APIConfig;
import com.madirex.hairsalonserver.config.security.jwt.JwtTokenProvider;
import com.madirex.hairsalonserver.config.security.jwt.model.JwtUserResponse;
import com.madirex.hairsalonserver.config.security.jwt.model.LoginRequest;
import com.madirex.hairsalonserver.dto.user.CreateUserDTO;
import com.madirex.hairsalonserver.dto.user.UserDTO;
import com.madirex.hairsalonserver.exceptions.GeneralBadRequestException;
import com.madirex.hairsalonserver.exceptions.ServiceNotFoundException;
import com.madirex.hairsalonserver.exceptions.user.UserNotFoundByEmailException;
import com.madirex.hairsalonserver.exceptions.user.UserNotFoundByIdException;
import com.madirex.hairsalonserver.exceptions.user.UserNotFoundByUsernameException;
import com.madirex.hairsalonserver.exceptions.user.UsersNotFoundException;
import com.madirex.hairsalonserver.mapper.UserMapper;
import com.madirex.hairsalonserver.model.Login;
import com.madirex.hairsalonserver.model.User;
import com.madirex.hairsalonserver.model.UserRole;
import com.madirex.hairsalonserver.repository.LoginRepository;
import com.madirex.hairsalonserver.services.uploads.StorageService;
import com.madirex.hairsalonserver.services.users.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(APIConfig.API_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginRepository loginRepository;

    private final StorageService storageService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @ApiOperation(value = "Obtener todos los usuarios", notes = "Obtiene todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<?> findAll(
            @RequestParam("searchQuery") Optional<String> searchQuery
    ) {
        List<User> users;
        try {
            if (searchQuery.isPresent())
                users = userService.findByUsernameContainsIgnoreCase(searchQuery.get());
            else
                users = userService.findAll();
            return ResponseEntity.ok(userMapper.toDTO(users));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @ApiOperation(value = "Obtener un usuario por id", notes = "Obtiene un usuario en base al id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundByIdException(id);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    @ApiOperation(value = "Obtener un usuario por username", notes = "Obtiene un usuario en base al username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class)
    })
    @GetMapping("/name/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        User user = userService.findByUsernameIgnoreCase(username).orElse(null);
        if (user == null) {
            throw new UserNotFoundByUsernameException(username);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    @ApiOperation(value = "Obtener un usuario por email", notes = "Obtiene un usuario en base al email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class)
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundByEmailException(email);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }


    @CrossOrigin(origins = "http://localhost:3306")
    @ApiOperation(value = "Obtener un usuario", notes = "Obtiene un usuario que esta logueado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class)
    })
    @GetMapping("/me")
    public UserDTO me(@AuthenticationPrincipal User user) {
        return userMapper.toDTO(user);
    }

    @CrossOrigin(origins = "http://localhost:3306")
    @ApiOperation(value = "Loguear un usuario", notes = "Loguea un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/login")
    public JwtUserResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwtToken = tokenProvider.generateToken(authentication);
        Login login = new Login(jwtToken, Date.from(Instant.now()), user);
        loginRepository.save(login);
        return convertUserEntityAndTokenToJwtUserResponse(user, jwtToken);
    }

    @ApiOperation(value = "Crear un usuario", notes = "Crea un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public UserDTO nuevoUsuario(@RequestBody CreateUserDTO newUser) {
        return userMapper.toDTO(userService.save(newUser));
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> nuevoUsuario(
            @RequestPart("user") CreateUserDTO createUserDTO,
            @RequestPart("file") MultipartFile file) {

        User user = userMapper.fromDTOCreate(createUserDTO);

        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            String urlImagen = storageService.getUrl(imagen);
            user.setImage(urlImagen);
            createUserDTO.setImage(user.getImage());
        }
        try {
            User userInsertado = userService.save(createUserDTO);
            return ResponseEntity.ok(userMapper.toDTO(userInsertado));
        } catch (ServiceNotFoundException ex) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el usuario. Campos incorrectos");
        }
    }

    private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(User user, String jwtToken) {
        return JwtUserResponse
                .jwtUserResponseBuilder()
                .id(user.getId())
                .image(user.getImage())
                .name(user.getName())
                .image(user.getImage())
                .userRoles(user.getRoles().stream().map(UserRole::name).collect(Collectors.toSet()))
                .username(user.getUsername())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .gender(user.getGender())
                .token(jwtToken)
                .build();
    }
}