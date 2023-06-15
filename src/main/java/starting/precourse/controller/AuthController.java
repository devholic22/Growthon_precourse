package starting.precourse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import starting.precourse.dto.LoginDto;
import starting.precourse.dto.UserDto;
import starting.precourse.entity.User;
import starting.precourse.exception.*;
import starting.precourse.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto) {
        try {
            User newUser = userService.signup(userDto);
            return ResponseEntity.ok(newUser);
        } catch (NotQualifiedDtoException | AlreadyExistUserException e) {
            return errorMessage(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            return userService.login(loginDto);
        } catch (TargetNotFoundException | LoginException e) {
            return errorMessage(e);
        }
    }

    private static ResponseEntity<ExceptionResponse> errorMessage(RuntimeException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
