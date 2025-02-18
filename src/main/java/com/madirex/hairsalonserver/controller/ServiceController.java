package com.madirex.hairsalonserver.controller;

import com.madirex.hairsalonserver.config.APIConfig;
import com.madirex.hairsalonserver.dto.service.ServiceDTO;
import com.madirex.hairsalonserver.exceptions.GeneralBadRequestException;
import com.madirex.hairsalonserver.exceptions.ServiceNotFoundException;
import com.madirex.hairsalonserver.exceptions.service.ServiceBadRequestException;
import com.madirex.hairsalonserver.exceptions.service.ServicesNotFoundException;
import com.madirex.hairsalonserver.mapper.ServiceMapper;
import com.madirex.hairsalonserver.model.Service;
import com.madirex.hairsalonserver.repository.ServiceRepository;
import com.madirex.hairsalonserver.services.uploads.StorageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIConfig.API_PATH + "/services")
public class ServiceController {

    private final ServiceRepository serviceRepository;
    private final StorageService storageService;
    private final ServiceMapper serviceMapper;

    @Autowired
    public ServiceController(ServiceRepository repository, StorageService storageService, ServiceMapper serviceMapper) {
        this.serviceRepository = repository;
        this.storageService = storageService;
        this.serviceMapper = serviceMapper;
    }

    @ApiOperation(value = "Obtener todos los servicios", notes = "Obtiene todos los servicios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ServiceDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = ServicesNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/all")
    public ResponseEntity<List<ServiceDTO>> findAll(@RequestParam(name = "limit") Optional<String> limit) {
        List<Service> services = null;
        try {
            services = serviceRepository.findAll();

            if (limit.isPresent() && !services.isEmpty() && services.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(serviceMapper.toDTO(services.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!services.isEmpty()) {
                    return ResponseEntity.ok(serviceMapper.toDTO(services));
                } else {
                    throw new ServicesNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @ApiOperation(value = "Obtener servicio por nombre", notes = "Obtiene un servicio en base a su nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ServiceDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = ServicesNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<?> findByNameContainsIgnoreCase(@RequestParam(name = "searchQuery") Optional<String> searchQuery
    ) {
        List<Service> services;
        try {
            if (searchQuery.isPresent()) {
                services = serviceRepository.findByNameContainsIgnoreCase(searchQuery);
            } else {
                services = serviceRepository.findAll();
            }
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @ApiOperation(value = "Obtener un servicio por id", notes = "Obtiene un servicio por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ServiceDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = ServiceNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> findById(@PathVariable String id) {
        Service service = serviceRepository.findById(id).orElse(null);
        if (service == null) {
            throw new ServiceNotFoundException(id);
        } else {
            return ResponseEntity.ok(serviceMapper.toDTO(service));
        }
    }

    @ApiOperation(value = "Crear un servicio", notes = "Crea un servicio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = ServiceDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public ResponseEntity<ServiceDTO> newService(@RequestBody ServiceDTO newService) {
        Service service = serviceMapper.fromDTO(newService);
        checkServiceData(service);
        Service serviceInsert = serviceRepository.save(service);
        return ResponseEntity.ok(serviceMapper.toDTO(serviceInsert));
    }

    @ApiOperation(value = "Actualizar un servicio", notes = "Actualiza un servicio en base a su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ServiceDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = ServiceNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTO> update(@PathVariable String id, @RequestBody ServiceDTO newService) {
        try {
            Service serviceUpdated = serviceRepository.findById(id).orElse(null);
            if (serviceUpdated == null) {
                throw new ServiceNotFoundException(id);
            } else {
                checkServiceData(serviceUpdated);

                serviceUpdated.setName(newService.getName());
                serviceUpdated.setDescription(newService.getDescription());
                serviceUpdated.setImage(newService.getImage());
                serviceUpdated.setPrice(newService.getPrice());
                serviceUpdated.setStock(newService.getStock());
                serviceUpdated = serviceRepository.save(serviceUpdated);

                return ResponseEntity.ok(serviceMapper.toDTO(serviceUpdated));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el service. Campos incorrectos.");
        }
    }

    @ApiOperation(value = "Actualizar un servicio", notes = "Actualiza un servicio en base a su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ServiceDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = ServiceNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceDTO> deleteService(@PathVariable String id) {

        Service service = serviceRepository.findById(id).orElse(null);
        if (service == null) {
            throw new ServiceNotFoundException(id);
        }
        try {
            serviceRepository.delete(service);
            return ResponseEntity.ok(serviceMapper.toDTO(service));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el service");
        }
    }

    @ApiOperation(value = "Crea un servicio con imagen", notes = "Crea un servicio con imagen")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ServiceDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class),
    })
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> nuevoServicio(
            @RequestPart("service") ServiceDTO serviceDTO,
            @RequestPart("file") MultipartFile file) {

        Service service = serviceMapper.fromDTO(serviceDTO);
        checkServiceData(service);

        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            String urlImagen = storageService.getUrl(imagen);
            service.setImage(urlImagen);
        }
        try {
            Service serviceInsertado = serviceRepository.save(service);
            return ResponseEntity.ok(serviceMapper.toDTO(serviceInsertado));
        } catch (ServiceNotFoundException ex) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el servicio. Campos incorrectos");
        }
    }

    private void checkServiceData(Service service) {
        if (service.getName() == null || service.getName().isEmpty()) {
            throw new ServiceBadRequestException("Nombre", "El nombre es obligatorio");
        }
        if (service.getPrice() <= 0) {
            throw new ServiceBadRequestException("Precio", "El precio debe ser mayor que 0");
        }
        if (service.getStock() < 0) {
            throw new ServiceBadRequestException("Stock", "El stock debe ser mayor o igual que 0");
        }
    }
}
