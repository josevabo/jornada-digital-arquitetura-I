package tech.ada.banco.login;

import tech.ada.banco.usuario.Usuario;
import tech.ada.banco.usuario.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.token.secret-key}")
    private String secret;

    @Autowired
    private JWTFactory tokenFactory;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String createToken(UserDetails userDetails) {
        var usuario = this.usuarioRepository.findByUsername(userDetails.getUsername());

        return tokenFactory.createJWT(userDetails, usuario.get());
    }

    private static Date convertFromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Base64.getEncoder().encode(this.secret.getBytes()));
    }

    public String extractUsername(String token) {
        return this.extractClaims(token, Claims::getSubject);
    }

    public UserDetails getUserDetails(String token) {
        Claims claims = extractAllClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);
        String roles = (String) claims.get("roles");
        String cpf = (String) claims.get("cpf");

        String[] jwtSubject = subject.split(",");

        return Usuario.
                builder()
                .roles(roles)
                .cpf(cpf)
                .username(jwtSubject[0])
                .build();
    }

    private <T> T extractClaims(String token, Function<Claims, T> resolver) {
        final var claims = this.extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final var username = this.extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Date extractExpiration(String token) {
        return this.extractClaims(token, Claims::getExpiration);
    }

}
