package med.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosDireccion(
        @NotBlank(message = "Calle es obligatorio") String calle,
        String numero,
        String complemento,
        @NotBlank(message = "Barrio es obligatorio") String barrio,
        @NotBlank(message = "Ciudad es obligatorio") String ciudad,
        @NotBlank(message = "Código Postal es obligatorio") @Pattern(regexp = "\\d{4}") String codigo_postal,
        @NotBlank(message = "Estado es obligatorio") String estado
) {
}
