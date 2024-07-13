package api.rest.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotNull
        Long usuarioId,
        @NotBlank(message = "Escriba un mensaje a su tópico.")
        String mensaje,
        @NotBlank(message = "Escriba el nombre del curso a su tópico.")
        String nombreCurso,
        @NotBlank(message = "Escriba un título a su tópico.")
        String titulo,
        LocalDateTime fecha) {

        public DatosRegistroTopico(Topico topico){
                this(topico.getUsuario().getId(),
                        topico.getMensaje(), topico.getNombreCurso(), topico.getTitulo(), topico.getFechaCreacion());
        }
}
