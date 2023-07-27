package module.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for handling JWT tokens.
 * It provides functionality for generating and validating JWT tokens, and extracting information from them.
 */
@Component
public class JwtUtil {
    private String SECRET_KEY = "AYiPZvYSN4wQKb8xvFcC2Zqoh1kDhtX+D1ljJwUdw6I=";

    /**
     * Extracts the username from a JWT token.
     *
     * @param token a JWT token.
     * @return the username from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token a JWT token.
     * @return the expiration date from the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from a JWT token.
     *
     * @param token a JWT token.
     * @param claimsResolver a function that extracts a claim from a Claims object.
     * @param <T> the type of the claim.
     * @return the claim from the token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token a JWT token.
     * @return a Claims object containing all claims from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if a JWT token has expired.
     *
     * @param token a JWT token.
     * @return true if the token has expired, false otherwise.
     */
    Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generates a JWT token for a user.
     *
     * @param userDetails the user's details.
     * @return a JWT token for the user.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Creates a JWT token with specific claims and subject.
     *
     * @param claims the claims to be included in the token.
     * @param subject the subject of the token.
     * @return a JWT token with the provided claims and subject.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Validates a JWT token.
     *
     * @param token a JWT token.
     * @param userDetails the details of the user the token was created for.
     * @return true if the token is valid and was created for the provided user, false otherwise.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

