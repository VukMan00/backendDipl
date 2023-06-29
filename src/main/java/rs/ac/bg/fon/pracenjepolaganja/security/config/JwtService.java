package rs.ac.bg.fon.pracenjepolaganja.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service used for JWT actions.
 * Extracts username, claims nad single claim from token.
 *
 * @author Vuk Manojlovic
 */
@Service
public class JwtService {

    /**
     * Secret key for creating sign in key.
     */
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    /**
     * Extract username from token
     *
     * @param token whose username is going to be extracted
     * @return extract username
     */
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    /**
     * Extracts one single claim that token has.
     *
     * @param token the token whose claim are going to be extract
     * @param claimsResolver Function interface with Claims as given type
     * @param <T> Generic Type
     * @return claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates token from userDetails with empty claims.
     *
     * @param userDetails user for generated token
     * @return String generated token
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }


    /**
     * Generates the JWT token.
     * Token has expiration date of 24h + 1000*60*24
     *
     * @param extractClaims claims for generated token
     * @param userDetails details for token of user
     * @return String generated token
     */
    public String generateToken(Map<String,Object> extractClaims, UserDetails userDetails){
        return buildToken(extractClaims,userDetails,1000*60*60*10);
    }

    /**
     * Generates refresh token.
     *
     * @param userDetails details of user that will refresh token have
     * @return String of generated refresh token
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, 1000*60*60);
    }

    /**
     * Builds the token.
     * @param extraClaims claims that token has.
     * @param userDetails user in token.
     * @param expiration date of token expiration.
     * @return String of build token.
     */
    private String buildToken(Map<String,Object> extraClaims,UserDetails userDetails,long expiration){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks if token is valid.
     *
     * @param token whose going to be validated.
     * @param userDetails user whose token is going to be validated.
     * @return boolean true if token is valid, false if token is not valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if token is expired.
     *
     * @param token whose token is checked
     * @return true if token is expired, false if token is not expired
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts expiration date of token.
     *
     * @param token whose expiration date is going to be extracted
     * @return date of expiration
     */
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    /**
     * Generate Claims which will be generated with JWT token.
     * It assures that token is generated from someone who claims to
     * be creator of the token.
     *
     * @param token generated token
     * @return claims of generatedToken
     */
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Generate the signing key using one of the algorithms.
     *
     * @return generated key
     */
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
