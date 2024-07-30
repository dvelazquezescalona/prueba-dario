package cu.fcc.pigeon.service.dto;

import cu.fcc.pigeon.domain.Premio;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * A PalomaDTO.
 */
@NoArgsConstructor
@AllArgsConstructor
public class PalomaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String anilla;

    @NotNull
    private String anno;

    @NotNull
    private Long pais;

    @NotNull
    private Boolean sexo;

    private Boolean activo;

    private Long colorId;

    private Long colombofiloId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnilla() {
        return anilla;
    }

    public void setAnilla(String anilla) {
        this.anilla = anilla;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public Long getPais() {
        return pais;
    }

    public void setPais(Long pais) {
        this.pais = pais;
    }

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public Long getColombofiloId() {
        return colombofiloId;
    }

    public void setColombofiloId(Long colombofiloId) {
        this.colombofiloId = colombofiloId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PalomaDTO palomaDTO = (PalomaDTO) o;
        return (
            Objects.equals(id, palomaDTO.id) &&
            Objects.equals(anilla, palomaDTO.anilla) &&
            Objects.equals(anno, palomaDTO.anno) &&
            Objects.equals(pais, palomaDTO.pais) &&
            Objects.equals(sexo, palomaDTO.sexo) &&
            Objects.equals(activo, palomaDTO.activo) &&
            Objects.equals(colorId, palomaDTO.colorId) &&
            Objects.equals(colombofiloId, palomaDTO.colombofiloId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, anilla, anno, pais, sexo, activo, colorId, colombofiloId);
    }

    @Override
    public String toString() {
        return (
            "PalomaDTO{" +
            "id=" +
            id +
            ", anilla='" +
            anilla +
            '\'' +
            ", anno='" +
            anno +
            '\'' +
            ", pais='" +
            pais +
            '\'' +
            ", sexo=" +
            sexo +
            ", activo=" +
            activo +
            ", color=" +
            colorId +
            ", colombofilo=" +
            colombofiloId +
            '}'
        );
    }
}
