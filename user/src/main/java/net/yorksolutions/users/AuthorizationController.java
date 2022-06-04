package net.yorksolutions.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class AuthorizationController {

    private AuthorizationService service;

    @Autowired
    public AuthorizationController(@NonNull AuthorizationService service) {
        this.service = service;
    }

    @GetMapping("/isAuthorized")
    public void isAuthorized(@RequestParam UUID token) {
        service.isAuthorized(token);
    }

    @CrossOrigin
    @GetMapping("/login")
    public UUID login(@RequestParam String username, @RequestParam String password) {
        return service.login(username,password);
    }

    @GetMapping("/registerOwner")
    public void registerOwner(@RequestParam String username, @RequestParam String password) {
        service.registerOwner(username, password);
    }
    @GetMapping("/registerCustomer")
    public void registerCustomer(@RequestParam String username, @RequestParam String password) {
        service.registerCustomer(username, password);
    }
    @GetMapping("/logout")
    public void logout(@RequestParam UUID token) {
        service.logout(token);
    }

    @GetMapping("/existingUserInDatabase")
    public Boolean existingUserInDatabase() {
        return service.isOwnerInDatabase();
    }






    public void setService(AuthorizationService service) {
        this.service = service;
    }
}
