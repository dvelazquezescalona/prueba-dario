package cu.fcc.pigeon.service.dto;

import cu.fcc.pigeon.domain.Paloma;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * A Color.
 */

@NoArgsConstructor
@AllArgsConstructor
public class ColorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombreColor;

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

    public String getNombreColor() {
        return nombreColor;
    }

    public void setNombreColor(String nombreColor) {
        this.nombreColor = nombreColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorDTO colorDTO = (ColorDTO) o;
        return (
            Objects.equals(id, colorDTO.id) && Objects.equals(nombreColor, colorDTO.nombreColor) && Objects.equals(codigo, colorDTO.codigo)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreColor, codigo);
    }

    @Override
    public String toString() {
        return "ColorDTO{" + "id=" + id + ", nombreColor='" + nombreColor + '\'' + ", codigo='" + codigo + '\'' + '}';
    }
    // jhipster-needle-entity-add-field - JHipster will add fields here

}
