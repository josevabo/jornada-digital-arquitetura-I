package tech.ada.banco.login;

import org.springframework.security.core.userdetails.UserDetails;
import tech.ada.banco.usuario.Usuario;

public interface JWTFactory {
    String createJWT(UserDetails userDetails, Usuario usuario);
}
