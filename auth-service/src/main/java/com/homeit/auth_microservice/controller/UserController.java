package com.homeit.auth_microservice.controller;

import com.homeit.auth_microservice.dto.TokenRequest;
import com.homeit.auth_microservice.dto.TokenResponse;
import com.homeit.auth_microservice.dto.UserDTO;
import com.homeit.auth_microservice.service.ClientService;
import com.homeit.auth_microservice.service.JwtService;
import com.homeit.auth_microservice.service.ScopeService;
import com.homeit.auth_microservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final ClientService clientService;
    private final JwtService jwtService;
    private final ScopeService scopeService;

    public UserController(UserService userService, ClientService clientService, JwtService jwtService, ScopeService scopeService) {
        this.userService = userService;
        this.clientService = clientService;
        this.jwtService = jwtService;
        this.scopeService = scopeService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){
        return userService
                .createUser(userDTO.email(), userDTO.password(), userDTO.user_type())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping(value = "/token", produces = "application/json", consumes = "application/json")
    public ResponseEntity<TokenResponse> createToken(@RequestBody TokenRequest tokenRequest){

        if(!"password".equals(tokenRequest.grant_type())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(!clientService.validateClient(tokenRequest.client_id(), tokenRequest.client_secret())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(!userService.validateUser(tokenRequest.username(), tokenRequest.password())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO foundUser = userService.findByEmail(tokenRequest.username()).get();

        return ResponseEntity.ok(
                jwtService.getJWTToken(tokenRequest,
                        scopeService.findScope(
                                foundUser.user_type()),
                        foundUser.id()));
    }
}
