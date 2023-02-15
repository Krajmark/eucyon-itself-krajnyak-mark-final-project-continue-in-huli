package com.greenfoxacademy.ebayclone.dbinit;

import com.greenfoxacademy.ebayclone.dtos.user.UserCreationDTO;
import com.greenfoxacademy.ebayclone.models.User;
import com.greenfoxacademy.ebayclone.repositories.UserRepo;
import com.greenfoxacademy.ebayclone.services.UserManagementService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInit implements CommandLineRunner {

    private final UserRepo userRepo;
    private final UserManagementService userManagementService;

    public DatabaseInit(UserRepo userRepo, UserManagementService userManagementService) {
        this.userRepo = userRepo;
        this.userManagementService = userManagementService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (((List<User>) this.userRepo.findAll()).isEmpty()) {
            this.userManagementService.createNewUser(
                    "admin",
                    new UserCreationDTO("testAdmin", "asdasd"),
                    null);
            this.userManagementService.createNewUser(
                    "seller",
                    new UserCreationDTO("testSeller", "asdasd"),
                    null);
            this.userManagementService.createNewUser(
                    "buyer",
                    new UserCreationDTO("testBuyer", "asdasd"),
                    null);
        }
    }
}
