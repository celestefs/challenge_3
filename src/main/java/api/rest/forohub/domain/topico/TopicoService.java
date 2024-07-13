package api.rest.forohub.domain.topico;

import api.rest.forohub.domain.usuario.UsuarioRepository;
import api.rest.forohub.infra.errores.ValidacionDeIntegridad;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public DatosRegistroTopico crearTopico(DatosRegistroTopico datos){
        if(datos.usuarioId()!=null&&usuarioRepository.existsById(datos.usuarioId())){
            throw new ValidacionDeIntegridad("El id de usuario no existe.");
        }
        var usuario = usuarioRepository.findById(datos.usuarioId()).get();
        var topico = new Topico(new DatosRegistroTopico(datos.usuarioId(), datos.mensaje(), datos.nombreCurso(), datos.titulo(), datos.fecha()));
        topicoRepository.save(topico);
        return new DatosRegistroTopico(topico);
    }
}




















