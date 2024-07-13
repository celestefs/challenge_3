package api.rest.forohub.domain.topico;

import api.rest.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Optional;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private String nombreCurso;
    private String titulo;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Topico(DatosRegistroTopico datosRegistroTopico, Usuario usuario){
        this.activo = true;
        this.mensaje = datosRegistroTopico.mensaje();
        this.nombreCurso = datosRegistroTopico.nombreCurso();
        this.titulo = datosRegistroTopico.titulo();
        this.usuario = usuario;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Topico(DatosRegistroTopico datosRegistroTopico) {
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico){
        if(datosActualizarTopico.mensaje() != null){
            this.mensaje = datosActualizarTopico.mensaje();
        }
        if(datosActualizarTopico.nombreCurso() != null){
            this.nombreCurso = datosActualizarTopico.nombreCurso();
        }
        if(datosActualizarTopico.titulo() != null){
            this.titulo = datosActualizarTopico.titulo();
        }
    }

    public void desactivarTopico() {
        this.activo = false;
    }
}
