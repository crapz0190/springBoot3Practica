package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.Direccion;

// se recibe como parametro Medico pero en realidad se quiere cada uno de los atributos
// de medico, y el constructor de DatosDetalleMedico es quien sabe como lidiar con
// una entidad JPA medico
public record DatosDetalleMedico(
        Long id,
        String nombre,
        String email,
        String documento,
        String telefono,
        Especialidad especialidad,
        Direccion direccion

) {
    public DatosDetalleMedico(Medico medico) {
        this(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getDocumento(),
                medico.getTelefono(),
                medico.getEspecialidad(),
                medico.getDireccion());
    }
}
