package Arep.eci.edu.co.user.service;

import Arep.eci.edu.co.user.model.User;
import Arep.eci.edu.co.user.repository.UserRepository;
import Arep.eci.edu.co.util.CognitoTokenDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final CognitoTokenDecoder cognitoTokenDecoder;

    @Autowired
    public UserService(UserRepository userRepository, CognitoTokenDecoder cognitoTokenDecoder) {
        this.userRepository = userRepository;
        this.cognitoTokenDecoder = cognitoTokenDecoder;
    }

    /**
     * Guarda o actualiza un usuario basado en el token de Cognito
     */
    public User saveUserFromToken(String token) {
        logger.info("Iniciando guardado de usuario desde token");
        
        CognitoTokenDecoder.CognitoUserInfo userInfo = cognitoTokenDecoder.extractUserInfo(token);
        logger.info("Información extraída del token - Sub: {}, Email: {}, Name: {}", 
                    userInfo.getSub(), userInfo.getEmail(), userInfo.getName());
        
        Optional<User> existingUser = userRepository.findBySub(userInfo.getSub());
        
        User user;
        if (existingUser.isPresent()) {
            logger.info("Usuario existente encontrado, actualizando...");
            user = existingUser.get();
            user.setEmail(userInfo.getEmail());
            user.setName(userInfo.getName());
        } else {
            logger.info("Usuario nuevo, creando...");
            user = new User();
            user.setSub(userInfo.getSub());
            user.setEmail(userInfo.getEmail());
            user.setName(userInfo.getName());
        }
        
        User savedUser = userRepository.save(user);
        logger.info("Usuario guardado exitosamente con ID: {}", savedUser.getId());
        return savedUser;
    }
}
