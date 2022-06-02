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
public class UserController {

    private UserService userService;
    private AuthorizationService authorizationService;

    @Autowired
    public UserController(@NonNull UserService userService, @NonNull AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/viewAllUsers")
    Iterable<UserAccount> viewAllUsers (@RequestParam UUID requestingUserToken) {
        return userService.viewAllUsers(requestingUserToken);

    }
    @GetMapping("/deleteUser")
    public void deleteUser(@RequestParam UUID requestingUserToken, @RequestParam Long idToBeDeleted) {
        userService.deleteUser(requestingUserToken, idToBeDeleted);

    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
