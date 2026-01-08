package med.voll.api.domain.paciente;

import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DatosListaPacienteModelAssembler implements RepresentationModelAssembler<DatosListaPaciente, EntityModel<DatosListaPaciente>> {
    // El metodo toModel convierte una instancia de DatosListaPaciente en un EntityModel,
    // que es una representación envolvente que proporciona una estructura estable para el JSON y puede incluir links adicionales.
    @Override
    @NonNull
    public EntityModel<DatosListaPaciente> toModel(@NonNull DatosListaPaciente datosListaPaciente) {
        return EntityModel.of(datosListaPaciente);
    }
}
