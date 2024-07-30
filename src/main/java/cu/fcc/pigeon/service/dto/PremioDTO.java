package cu.fcc.pigeon.service.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * A Premio.
 */

@NoArgsConstructor
@AllArgsConstructor
public class PremioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String designada;

    @NotNull
    private Instant fechaArribo;

    private Double tiempoVuelo;

    private Double plus;

    private Double velocidad;

    private Integer lugar;

    private Double puntos;

    private Long palomaId;

    private Long vueloId;

    public Double getPlus() {
        return plus;
    }

    public void setPlus(Double plus) {
        this.plus = plus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignada() {
        return designada;
    }

    public void setDesignada(String designada) {
        this.designada = designada;
    }

    public Instant getFechaArribo() {
        return fechaArribo;
    }

    public void setFechaArribo(Instant fechaArribo) {
        this.fechaArribo = fechaArribo;
    }

    public Double getTiempoVuelo() {
        return tiempoVuelo;
    }

    public void setTiempoVuelo(Double tiempoVuelo) {
        this.tiempoVuelo = tiempoVuelo;
    }

    public Double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    public Integer getLugar() {
        return lugar;
    }

    public void setLugar(Integer lugar) {
        this.lugar = lugar;
    }

    public Double getPuntos() {
        return puntos;
    }

    public void setPuntos(Double puntos) {
        this.puntos = puntos;
    }

    public Long getPalomaId() {
        return palomaId;
    }

    public void setPalomaId(Long palomaId) {
        this.palomaId = palomaId;
    }

    public Long getVueloId() {
        return vueloId;
    }

    public void setVueloId(Long vueloId) {
        this.vueloId = vueloId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PremioDTO premioDTO = (PremioDTO) o;
        return (
            Objects.equals(id, premioDTO.id) &&
            Objects.equals(designada, premioDTO.designada) &&
            Objects.equals(fechaArribo, premioDTO.fechaArribo) &&
            Objects.equals(tiempoVuelo, premioDTO.tiempoVuelo) &&
            Objects.equals(velocidad, premioDTO.velocidad) &&
            Objects.equals(lugar, premioDTO.lugar) &&
            Objects.equals(puntos, premioDTO.puntos) &&
            Objects.equals(palomaId, premioDTO.palomaId) &&
            Objects.equals(vueloId, premioDTO.vueloId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designada, fechaArribo, tiempoVuelo, velocidad, lugar, puntos, palomaId, vueloId);
    }

    @Override
    public String toString() {
        return (
            "PremioDTO{" +
            "id=" +
            id +
            ", designada=" +
            designada +
            ", fechaArribo=" +
            fechaArribo +
            ", tiempoVuelo=" +
            tiempoVuelo +
            ", velocidad=" +
            velocidad +
            ", lugar=" +
            lugar +
            ", puntos=" +
            puntos +
            ", palomaId=" +
            palomaId +
            ", vueloId=" +
            vueloId +
            '}'
        );
    }
}
