package api.rest.forohub.controller;

import api.rest.forohub.domain.topico.*;
import api.rest.forohub.domain.usuario.Usuario;
import api.rest.forohub.domain.usuario.UsuarioRepository;
import api.rest.forohub.infra.errores.ValidacionDeIntegridad;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                                UriComponentsBuilder uriComponentsBuilder){
        Optional<Usuario> usuario = usuarioRepository.findById(datosRegistroTopico.usuarioId());
        if(usuario.isEmpty()){
            throw new ValidacionDeIntegridad("El usuario ingresado no existe.");
        } else {
            Usuario usuarioEncontrado = usuario.get();
            Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, usuarioEncontrado));
            DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(),
                    topico.getMensaje(), topico.getNombreCurso(), topico.getTitulo(), topico.getFechaCreacion());
            URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(url).body(datosRespuestaTopico);
        }
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 5) Pageable paginacion){
        return ResponseEntity.ok(topicoRepository.findByActivoTrue(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> obtenerTopicoPorId(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosRespuestaTopico(topico.getId(),
                topico.getMensaje(), topico.getNombreCurso(), topico.getTitulo(), topico.getFechaCreacion());
        return ResponseEntity.ok(datosTopico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico.getId(),
                topico.getMensaje(), topico.getNombreCurso(), topico.getTitulo(), topico.getFechaCreacion()));
    }

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }

}
