package com.backend.hackaton.customer.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {

    @NotBlank(message = "El nombre del cliente es requerido")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String name;

    @NotBlank(message = "El número de contacto es requerido")
    @Size(max = 50, message = "El número de contacto no puede exceder 50 caracteres")
    private String contactNumber;

    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
}

