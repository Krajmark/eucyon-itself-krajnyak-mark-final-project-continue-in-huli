package com.greenfoxacademy.ebayclone.services;

import jakarta.validation.ValidationException;
import org.springframework.validation.BindingResult;

public interface BindingResultHandlerService {
    void handleBindingResult(BindingResult bindingResult) throws ValidationException;
}
