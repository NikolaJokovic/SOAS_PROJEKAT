package com.soas.api.proxy;

import com.soas.api.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "users-service")
public interface UserServiceProxy {

    @GetMapping("/users")
    List<UserDTO> getAllUsers();

    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable Long id);

    @GetMapping("/users/email/{email}")
    UserDTO getUserByEmail(@PathVariable String email);

    @PostMapping("/users")
    UserDTO createUser(@RequestBody UserDTO userDTO);

    @PutMapping("/users/{id}")
    UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO);

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id);
}
