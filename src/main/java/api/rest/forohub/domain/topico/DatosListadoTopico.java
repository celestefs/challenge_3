package api.rest.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String mensaje,
        String nombreCurso,
        String titulo,
        LocalDateTime fecha) {

    public DatosListadoTopico(Topico topico){
        this(topico.getId(), topico.getMensaje(),
                topico.getNombreCurso(), topico.getTitulo(), topico.getFechaCreacion());
    }
}
