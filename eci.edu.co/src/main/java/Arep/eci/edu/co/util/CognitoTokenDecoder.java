package Arep.eci.edu.co.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class CognitoTokenDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Decodifica un token JWT de AWS Cognito sin verificar la firma
     * @param token Token JWT de Cognito
     * @return Claims del token
     * @throws JwtException si el token es inválido
     */
    public Claims decodeToken(String token) throws JwtException {
        try {
            // Remover "Bearer " si está presente
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // Parsear el token sin verificar la firma (ya que Cognito la maneja)
            // En un entorno de producción, deberías verificar la firma con la clave pública de Cognito
            return Jwts.parserBuilder()
                    .build()
                    .parseClaimsJwt(token.substring(0, token.lastIndexOf('.') + 1))
                    .getBody();
        } catch (Exception e) {
            throw new JwtException("Token inválido: " + e.getMessage());
        }
    }

    /**
     * Extrae información específica del usuario desde el token de Cognito
     * @param token Token JWT de Cognito
     * @return CognitoUserInfo con los datos del usuario
     */
    public CognitoUserInfo extractUserInfo(String token) {
        try {
            // Separar las partes del JWT
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new JwtException("Token JWT malformado");
            }

            // Decodificar el payload (segunda parte)
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JsonNode jsonNode = objectMapper.readTree(payload);

            CognitoUserInfo userInfo = new CognitoUserInfo();
            // "sub" es obligatorio en Cognito; si falta, lanzar excepción
            String sub = jsonNode.path("sub").asText(null);
            if (sub == null || sub.isBlank()) {
                // Algunos tokens usan "cognito:username" como identificador
                sub = jsonNode.path("cognito:username").asText(null);
            }
            if (sub == null || sub.isBlank()) {
                throw new JwtException("El token no contiene 'sub' ni 'cognito:username'");
            }
            userInfo.setSub(sub);

            // Campos opcionales: si no vienen en el token, dejarlos en null
            userInfo.setEmail(jsonNode.path("email").asText(null));
            userInfo.setName(jsonNode.path("name").asText(null));


            return userInfo;
        } catch (Exception e) {
            throw new JwtException("Error al extraer información del token: " + e.getMessage());
        }
    }

    /**
     * Valida si el token no ha expirado
     * @param token Token JWT de Cognito
     * @return true si el token es válido, false si ha expirado
     */
    public boolean isTokenValid(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return false;
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JsonNode jsonNode = objectMapper.readTree(payload);
            
            JsonNode expNode = jsonNode.get("exp");
            if (expNode != null) {
                long exp = expNode.asLong();
                long currentTime = System.currentTimeMillis() / 1000;
                return exp > currentTime;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clase interna para encapsular la información del usuario desde Cognito
     */
    public static class CognitoUserInfo {
        private String sub;
        private String email;
        private String name;

        // Getters y Setters
        public String getSub() { return sub; }
        public void setSub(String sub) { this.sub = sub; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        @Override
        public String toString() {
            return "CognitoUserInfo{" +
                    "sub='" + sub + '\'' +
                    ", email='" + email + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}