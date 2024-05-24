package tech.ada.banco.login;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import tech.ada.banco.usuario.Usuario;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Component
public class JWTFactoryImpl  implements JWTFactory{

    @Value("${security.jwt.token.secret-key}")
    private static String secret;

    public String createJWT(UserDetails userDetails, Usuario usuario) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("roles",usuario.getRoles());
        claims.put("cpf",usuario.getCpf());
        final var now = LocalDateTime.now();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(convertFromLocalDateTime(now))
                .setExpiration(convertFromLocalDateTime(now.plusHours(1L)))
                .signWith(this.getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Date convertFromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Base64.getEncoder().encode(this.secret.getBytes()));
    }

}
