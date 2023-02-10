package com.greenfoxacademy.ebayclone.services;

import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Service
public class BindingResultHandlerServiceImpl implements BindingResultHandlerService{
    @Override
    public void handleBindingResult(BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            var asd = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .reduce("", (string, snippet) -> string + (string.isBlank() || Objects.requireNonNull(snippet).isBlank() ? "" : "/:/") + snippet);
            throw new ValidationException(
                    asd
            );
        }
    }
}
