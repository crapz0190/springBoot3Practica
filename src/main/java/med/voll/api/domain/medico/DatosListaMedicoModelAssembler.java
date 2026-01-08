package med.voll.api.domain.medico;

import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
// La anotación @Component indica que esta clase es un bean de Spring y se registrará automáticamente en el contexto de la aplicación.
public class DatosListaMedicoModelAssembler implements RepresentationModelAssembler<DatosListaMedico, EntityModel<DatosListaMedico>> {
    // El metodo toModel convierte una instancia de DatosListaMedico en un EntityModel,
    // que es una representación envolvente que proporciona una estructura estable para el JSON y puede incluir links adicionales.
    @Override
    @NonNull
    public EntityModel<DatosListaMedico> toModel(@NonNull DatosListaMedico datosListaMedico) {
        return EntityModel.of(datosListaMedico);
    }
}
