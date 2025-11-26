package Arep.eci.edu.co.user.controller;

import Arep.eci.edu.co.user.model.User;
import Arep.eci.edu.co.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Guarda o actualiza el usuario desde el token de Cognito
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<User> saveUser(@RequestHeader("Authorization") String authHeader) {
        try {
            logger.info("Recibida petición POST /api/users");
            String token = authHeader.substring(7); // Remover "Bearer "
            logger.info("Token extraído correctamente");
            
            User savedUser = userService.saveUserFromToken(token);
            logger.info("Usuario guardado exitosamente: {}", savedUser.getId());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            logger.error("Error guardando usuario: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
