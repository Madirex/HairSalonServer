package com.madirex.hairsalonserver.controller;

import com.madirex.hairsalonserver.config.APIConfig;
import com.madirex.hairsalonserver.dto.login.ListLoginPageDTO;
import com.madirex.hairsalonserver.dto.login.LoginDTO;
import com.madirex.hairsalonserver.exceptions.GeneralBadRequestException;
import com.madirex.hairsalonserver.exceptions.login.LoginBadRequestException;
import com.madirex.hairsalonserver.exceptions.login.LoginNotFoundException;
import com.madirex.hairsalonserver.exceptions.login.LoginsNotFoundException;
import com.madirex.hairsalonserver.mapper.LoginMapper;
import com.madirex.hairsalonserver.model.Login;
import com.madirex.hairsalonserver.repository.LoginRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConfig.API_PATH + "/logins")
public class LoginController {
    private final LoginRepository loginRepository;
    private final LoginMapper loginMapper;

    @Autowired
    public LoginController(LoginRepository loginRepository, LoginMapper loginMapper) {
        this.loginRepository = loginRepository;
        this.loginMapper = loginMapper;
    }

    @ApiOperation(value = "Obtener todos los logins", notes = "Obtiene todos los logins")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LoginDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = LoginNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<List<LoginDTO>> findAll() {
        List<Login> logins = null;
        try {
            logins = loginRepository.findAll();
            if (!logins.isEmpty()) {
                return ResponseEntity.ok(loginMapper.toDTO(logins));
            } else {
                throw new LoginsNotFoundException();
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @ApiOperation(value = "Obtiene una lista de logins", notes = "Obtiene una lista de logins paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ListLoginPageDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class),
            @ApiResponse(code = 401, message = "No autenticado"),
            @ApiResponse(code = 403, message = "No autorizado")
    })
    @CrossOrigin(origins = "http://localhost:3306")
    @GetMapping("/page")
    public ResponseEntity<ListLoginPageDTO> findAllLogins(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        try {
            Page<Login> pagedResult = loginRepository.findAll(pageable);
            ListLoginPageDTO listLoginPageDTO = ListLoginPageDTO.builder()
                    .data(loginMapper.toDTO(pagedResult.getContent()))
                    .totalPages(pagedResult.getTotalPages())
                    .totalElements(pagedResult.getTotalElements())
                    .currentPage(pagedResult.getNumber())
                    .build();
            return ResponseEntity.ok(listLoginPageDTO);
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos" + e.getMessage());
        }
    }

    @ApiOperation(value = "Obtener un login por id", notes = "Obtiene un login por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LoginDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LoginsNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<LoginDTO> findById(@PathVariable String id) {
        Login login = loginRepository.findById(id).orElse(null);
        if (login == null) {
            throw new LoginNotFoundException(id);
        } else {
            return ResponseEntity.ok(loginMapper.toDTO(login));
        }
    }

    @ApiOperation(value = "Crear un login", notes = "Crea un login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = LoginDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public ResponseEntity<LoginDTO> save(@RequestBody LoginDTO loginDTO) {
        try {
            Login login = loginMapper.fromDTO(loginDTO);
            checkLoginData(login);
            Login inserted = loginRepository.save(login);
            return ResponseEntity.ok(loginMapper.toDTO(inserted));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el login. Campos incorrectos.");
        }
    }

    @ApiOperation(value = "Actualizar un login", notes = "Actualiza un login por su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LoginDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LoginNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<LoginDTO> update(@PathVariable String id, @RequestBody Login login) {
        try {
            Login updated = loginRepository.findById(id).orElse(null);
            if (updated == null) {
                throw new LoginNotFoundException(id);
            } else {
                checkLoginData(login);
                updated.setInstant(login.getInstant());
                updated.setToken(login.getToken());
                updated.setUser(login.getUser());

                updated = loginRepository.save(updated);
                return ResponseEntity.ok(loginMapper.toDTO(updated));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el login. Campos incorrectos.");
        }
    }

    @ApiOperation(value = "Eliminar un login", notes = "Elimina un login en base a su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LoginDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LoginsNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<LoginDTO> delete(@PathVariable String id) {
        Login login = loginRepository.findById(id).orElse(null);
        if (login == null) {
            throw new LoginNotFoundException(id);
        }
        try {
            loginRepository.delete(login);
            return ResponseEntity.ok(loginMapper.toDTO(login));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el login");
        }
    }

    /**
     * Comprueba los campos obligatorios (requisitos)
     *
     * @param login DAO de Login
     */
    private void checkLoginData(Login login) {
        if (login.getToken() == null || login.getInstant() == null) {
            throw new LoginBadRequestException("Token", "El token es obligatorio");
        }
        if (login.getUser() == null) {
            throw new LoginBadRequestException("User", "El usuario es obligatorio");
        }
    }

}