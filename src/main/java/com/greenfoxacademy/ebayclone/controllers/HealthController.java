package com.greenfoxacademy.ebayclone.controllers;

import com.greenfoxacademy.ebayclone.dtos.estereggs.EsterEgg;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health/check")
    public ResponseEntity<?> healthCheck() {
        EsterEgg esterEgg = new EsterEgg();
        esterEgg.setWelcome("Welcome to our health check page, enjoy ;)");
        return ResponseEntity.ok(esterEgg);
    }

    @GetMapping({"/", "/index"})
    public ResponseEntity<?> indexPage() {
        return ResponseEntity.ok(new EsterEgg());
    }
}
