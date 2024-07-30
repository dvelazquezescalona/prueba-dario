package cu.fcc.pigeon.service.dto;

import cu.fcc.pigeon.domain.ColombofiloVuelo;
import cu.fcc.pigeon.domain.Premio;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * A VueloDTO.
 */
@NoArgsConstructor
@AllArgsConstructor
public class VueloDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Instant fecha;

    @NotNull
    private String descripcion;

    @NotNull
    private String competencia;

    @NotNull
    private String campeonato;

    @NotNull
    private Double puntuacionMax;

    @NotNull
    private Double puntuacionMin;

    @NotNull
    private Integer puntuacionSystem;

    private Long paraderoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(String campeonato) {
        this.campeonato = campeonato;
    }

    public Long getParaderoId() {
        return paraderoId;
    }

    public void setParaderoId(Long paraderoId) {
        this.paraderoId = paraderoId;
    }

    public Double getPuntuacionMax() {
        return puntuacionMax;
    }

    public void setPuntuacionMax(Double puntuacionMax) {
        this.puntuacionMax = puntuacionMax;
    }

    public Double getPuntuacionMin() {
        return puntuacionMin;
    }

    public void setPuntuacionMin(Double puntuacionMin) {
        this.puntuacionMin = puntuacionMin;
    }

    public Integer getPuntuacionSystem() {
        return puntuacionSystem;
    }

    public void setPuntuacionSystem(Integer puntuacionSystem) {
        this.puntuacionSystem = puntuacionSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VueloDTO vueloDTO = (VueloDTO) o;
        return (
            Objects.equals(id, vueloDTO.id) &&
            Objects.equals(fecha, vueloDTO.fecha) &&
            Objects.equals(descripcion, vueloDTO.descripcion) &&
            Objects.equals(competencia, vueloDTO.competencia) &&
            Objects.equals(campeonato, vueloDTO.campeonato) &&
            Objects.equals(puntuacionMax, vueloDTO.puntuacionMax) &&
            Objects.equals(puntuacionMin, vueloDTO.puntuacionMin) &&
            Objects.equals(puntuacionSystem, vueloDTO.puntuacionSystem) &&
            Objects.equals(paraderoId, vueloDTO.paraderoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, descripcion, competencia, campeonato, puntuacionMax, puntuacionMin, puntuacionSystem, paraderoId);
    }

    @Override
    public String toString() {
        return (
            "VueloDTO{" +
            "id=" +
            id +
            ", fecha=" +
            fecha +
            ", descripcion='" +
            descripcion +
            '\'' +
            ", competencia='" +
            competencia +
            '\'' +
            ", campeonato='" +
            campeonato +
            '\'' +
            ", puntuacionMax=" +
            puntuacionMax +
            ", puntuacionMin=" +
            puntuacionMin +
            ", puntuacionSystem=" +
            puntuacionSystem +
            ", paraderoId=" +
            paraderoId +
            '}'
        );
    }
}
