package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired // necesitamos que Spring pueda crear una instancia de un objeto que tenga heredado de esa interfaz
    private MedicoRepository medicoRepository; // esto es solo la interfaz

    //Dentro de MedicoController
    @Autowired // PagedResourcesAssembler se usa para convertir una Page en un PagedModel.
    private PagedResourcesAssembler<DatosListaMedico> pagedResourcesAssembler;
    @Autowired // Inyectamos nuestro DatosListaMedicoModelAssembler para convertir DatosListaMedico en EntityModel.
    private DatosListaMedicoModelAssembler datosListaMedicoModelAssembler;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroMedico datos, UriComponentsBuilder uriComponentsBuilder) {
        var medico = new Medico(datos); // una vez guardado ya obtiene un id
        medicoRepository.save(medico);

        // Codigo 201
        // uri: http://localhost:8080 en caso que el servidor este en nuestra PC,
        // pero cuando se hace un deploy va a tener otro ip u otro puerto,
        // el lugar donde esta esa uri no es algo estatico, se utiliza otro mecanismo
        // para poder tener esa flexibilidad y saber donde es que esta el servidor
        // Spring nos ofrece una clase que puede encapsular donde es que esta ese servidor
        // y adonde es que tiene que estar llamando a cada una de las urls
        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleMedico(medico));
    }

    /*@GetMapping
    public Page<DatosListaMedico> listar(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        return medicoRepository.findAll(paginacion).map(DatosListaMedico::new);
    }*/

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DatosListaMedico>>> listar(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        Page<DatosListaMedico> pagina = medicoRepository.findAllByActivoTrue(paginacion).map(DatosListaMedico::new);
        // Usamos el pagedResourcesAssembler y el datosListaMedicoModelAssembler para convertir la Page en un PagedModel.
        // Esto garantiza que cada objeto DatosListaMedico sea envuelto en un EntityModel, proporcionando una estructura JSON estable y permitiendo añadir links adicionales.
        var page = pagedResourcesAssembler.toModel(pagina, datosListaMedicoModelAssembler);

        // Codigo 200
        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionMedico datos) {
        // medico es la entidad JPA y no devolvemos esta entidad sino DTOs
        var medico = medicoRepository.getReferenceById(datos.id());
        medico.actualizarInformacion(datos);

        return ResponseEntity.ok(new DatosDetalleMedico(medico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);
        medico.eliminar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);

        return ResponseEntity.ok(new  DatosDetalleMedico(medico));
    }
}
