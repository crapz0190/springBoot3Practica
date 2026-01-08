package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosActualizacionMedico(
        @NotNull(message = "{id.obligatorio}") Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion
) {
}
