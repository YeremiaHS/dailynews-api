package id.fazzbca.daily_news.config;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtil {
    private final String JWT_SECRET_KEY = "dailynewsfazztrackscretkeys";
    private final Long JWT_EXPIRATIONS_MS = 1 * 60 * 60 * 1000L;

    //create token froma email
    public String createToken(String email){
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
            Instant issuedAt = Instant.now();
            String token = JWT.create()
            .withIssuer("fazzbca")
            .withSubject("auth")
            .withIssuedAt(issuedAt)
            .withExpiresAt(issuedAt.plusMillis(JWT_EXPIRATIONS_MS))
            .withClaim("email", email)
            .sign(algorithm);

            return token;
        } catch (Exception e) {
            // invalid signing configuration / couldn't convert claims
            return null;
        }
    }

    //validate token
    public Boolean validateToken(String token) {
        try {
            verifyToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //private method untuk verify token yang mengembalikan decoded jwtnya
    private DecodedJWT verifyToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
            //specify an spesific claim validations
            .withIssuer("fazzbca")
            //reusable verifier instance
            .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        } catch (Exception e) {
            return null;
        }
    }

    //get email data from token -> untuk request selajutnya yang datang bersama token, dicek emailnya
    public String getEmailFromToken(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            String email = decodedJWT.getClaim("email").asString();
            return email;
        } catch (Exception e) {
            return null;
        }
    }
}
