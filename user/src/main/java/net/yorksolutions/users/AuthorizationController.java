package net.yorksolutions.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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






    public void setService(AuthorizationService service) {
        this.service = service;
    }
}
