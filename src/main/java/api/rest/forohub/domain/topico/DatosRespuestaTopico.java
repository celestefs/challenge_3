package api.rest.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        String mensaje,
        String nombreCurso,
        String titulo,
        LocalDateTime fechaCreacion) {
}
