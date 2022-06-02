package net.yorksolutions.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
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
        ownerCheck(requestingUserToken);
        return userService.viewAllUsers();

    }
    @GetMapping("/deleteUser")
    public void deleteUser(@RequestParam UUID requestingUserToken, @RequestParam Long idToBeDeleted) {
        ownerCheck(requestingUserToken);
        userService.deleteUser(idToBeDeleted);
    }


    public void ownerCheck(UUID userToken) {
        final Long userId = authorizationService.getUserIdFromUuid(userToken);
        final Optional<UserAccount> user = userService.getOwner(userId);

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
