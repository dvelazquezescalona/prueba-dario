package cu.fcc.pigeon.service.dto;

import cu.fcc.pigeon.domain.Colombofilo;
import cu.fcc.pigeon.domain.Paradero;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * A SociedadDTO.
 */
@NoArgsConstructor
@AllArgsConstructor
public class SociedadDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombreSociedad;

    @NotNull
    private Double latitud;

    @NotNull
    private Double longitud;

    private Long municipioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreSociedad() {
        return nombreSociedad;
    }

    public void setNombreSociedad(String nombreSociedad) {
        this.nombreSociedad = nombreSociedad;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Long getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Long municipioId) {
        this.municipioId = municipioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SociedadDTO that = (SociedadDTO) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nombreSociedad, that.nombreSociedad) &&
            Objects.equals(latitud, that.latitud) &&
            Objects.equals(longitud, that.longitud) &&
            Objects.equals(municipioId, that.municipioId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreSociedad, latitud, longitud, municipioId);
    }

    @Override
    public String toString() {
        return (
            "SociedadDTO{" +
            "id=" +
            id +
            ", nombreSociedad='" +
            nombreSociedad +
            '\'' +
            ", latitud=" +
            latitud +
            ", longitud=" +
            longitud +
            ", municipioId=" +
            municipioId +
            '}'
        );
    }
}
