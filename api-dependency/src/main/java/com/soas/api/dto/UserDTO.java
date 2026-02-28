package com.soas.api.dto;

import com.soas.api.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "email je obavezan")
    @Email(message = "email mora biti validan")
    private String email;

    @NotBlank(message = "Lozinka je obavezna")
    private String password;

    @NotNull(message = "Uloga je obavezna")
    private Role role;
}