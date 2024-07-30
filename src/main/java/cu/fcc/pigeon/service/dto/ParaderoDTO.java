package cu.fcc.pigeon.service.dto;

import cu.fcc.pigeon.domain.Vuelo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * A ParaderoDTO.
 */
@NoArgsConstructor
@AllArgsConstructor
public class ParaderoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombreParadero;

    @NotNull
    private Integer distanciaMedia;

    @NotNull
    private Double latitud;

    @NotNull
    private Double longitud;

    private Long sociedadId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreParadero() {
        return nombreParadero;
    }

    public void setNombreParadero(String nombreParadero) {
        this.nombreParadero = nombreParadero;
    }

    public Integer getDistanciaMedia() {
        return distanciaMedia;
    }

    public void setDistanciaMedia(Integer distanciaMedia) {
        this.distanciaMedia = distanciaMedia;
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

    public Long getSociedadId() {
        return sociedadId;
    }

    public void setSociedadId(Long sociedadId) {
        this.sociedadId = sociedadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParaderoDTO that = (ParaderoDTO) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nombreParadero, that.nombreParadero) &&
            Objects.equals(distanciaMedia, that.distanciaMedia) &&
            Objects.equals(latitud, that.latitud) &&
            Objects.equals(longitud, that.longitud) &&
            Objects.equals(sociedadId, that.sociedadId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreParadero, distanciaMedia, latitud, longitud, sociedadId);
    }

    @Override
    public String toString() {
        return (
            "ParaderoDTO{" +
            "id=" +
            id +
            ", nombreParadero='" +
            nombreParadero +
            '\'' +
            ", distanciaMedia=" +
            distanciaMedia +
            ", latitud=" +
            latitud +
            ", longitud=" +
            longitud +
            ", sociedadId=" +
            sociedadId +
            '}'
        );
    }
}
