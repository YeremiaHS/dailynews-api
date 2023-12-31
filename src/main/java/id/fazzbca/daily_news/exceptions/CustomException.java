package id.fazzbca.daily_news.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTCreationException;

import id.fazzbca.daily_news.payloads.res.ResponseHandler;

@RestControllerAdvice
public class CustomException {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> globalException(Exception e) {
        return ResponseHandler.responseError(500, e.getMessage(), null);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgEx(IllegalArgumentException e) {
        return ResponseHandler.responseError(404, e.getMessage(), null);
    }

    @ExceptionHandler(value = JWTCreationException.class)
    public ResponseEntity<?>
    handleJWTCreationExceptionEx(JWTCreationException e) {
        return ResponseHandler.responseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), null);
    }
}
