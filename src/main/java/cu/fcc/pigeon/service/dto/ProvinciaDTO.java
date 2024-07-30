package cu.fcc.pigeon.service.dto;

import cu.fcc.pigeon.domain.Municipio;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * A Provincia.
 */

@AllArgsConstructor
@NoArgsConstructor
public class ProvinciaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombreProvincia;

    @NotNull
    private String codigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProvinciaDTO that = (ProvinciaDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nombreProvincia, that.nombreProvincia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreProvincia);
    }

    @Override
    public String toString() {
        return "ProvinciaDTO{" + "id=" + id + ", nombreProvincia='" + nombreProvincia + '\'' + '}';
    }
}
