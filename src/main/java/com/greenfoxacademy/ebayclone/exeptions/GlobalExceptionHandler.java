package com.greenfoxacademy.ebayclone.exeptions;

import com.greenfoxacademy.ebayclone.dtos.MessageDTO;
import com.greenfoxacademy.ebayclone.exeptions.product.ProductNotFoundException;
import com.greenfoxacademy.ebayclone.exeptions.user.PasswordInvalidException;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class, ProductNotFoundException.class})
    public ResponseEntity<MessageDTO> handleUsernameNotFoundException(
            Exception exception
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO(exception.getMessage()));
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<MessageDTO> handlePasswordInvalidException(
            PasswordInvalidException passwordInvalidException
    ) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new MessageDTO(passwordInvalidException.getMessage())
        );
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<MessageDTO> handleUsernameAlreadyInUseException(
            UsernameAlreadyInUseException usernameAlreadyInUseException
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new MessageDTO(usernameAlreadyInUseException.getMessage())
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<MessageDTO> handleValidationException(
            ValidationException validationException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MessageDTO(validationException.getMessage().split("/:/"))
        );
    }

    @ExceptionHandler({IllegalArgumentException.class, NumberFormatException.class})
    public ResponseEntity<MessageDTO> handleIllegalArgumentAndNumberFormatExceptions(
            Exception exception
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MessageDTO(exception.getMessage())
        );
    }
}
