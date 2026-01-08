package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    //Dentro de PacienteController
    @Autowired // PagedResourcesAssembler se usa para convertir una Page en un PagedModel.
    private PagedResourcesAssembler<DatosListaPaciente> pagedResourcesAssembler;
    @Autowired // Inyectamos nuestro DatosListaPacienteModelAssembler para convertir DatosListaPaciente en EntityModel.
    private DatosListaPacienteModelAssembler datosListaPacienteModelAssembler;

    @Transactional
    @PostMapping
    public void registrar(@RequestBody @Valid DatosRegistroPaciente datos) {
        pacienteRepository.save(new Paciente(datos));
    }

    /*@GetMapping
    public Page<DatosListaPaciente> listar(@PageableDefault(page = 0, size = 10, sort = {"nombre"}) Pageable paginacion) {
        return pacienteRepository.findAll(paginacion).map(DatosListaPaciente::new);
    }*/

    @GetMapping
    public PagedModel<EntityModel<DatosListaPaciente>> listar(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        Page<DatosListaPaciente> pagina = pacienteRepository.findAllByActivoTrue(paginacion).map(DatosListaPaciente::new);
        // Usamos el pagedResourcesAssembler y el datosListaMedicoModelAssembler para convertir la Page en un PagedModel.
        // Esto garantiza que cada objeto DatosListaMedico sea envuelto en un EntityModel, proporcionando una estructura JSON estable y permitiendo añadir links adicionales.
        return pagedResourcesAssembler.toModel(pagina, datosListaPacienteModelAssembler);
    }

    @Transactional
    @PutMapping
    public void actualizar(@RequestBody @Valid DatosActualizacionPaciente datos) {
        var paciente = pacienteRepository.getReferenceById(datos.id());
        paciente.actualizarInformacion(datos);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivar();
    }

}