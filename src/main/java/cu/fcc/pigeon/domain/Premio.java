package cu.fcc.pigeon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Premio.
 */
@Entity
@Table(name = "premio")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Premio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "designada", nullable = false)
    private String designada;

    @NotNull
    @Column(name = "fecha_arribo", nullable = false)
    private Instant fechaArribo;

    @Column(name = "tiempo_vuelo")
    private Double tiempoVuelo;

    @Column(name = "plus")
    private Double plus;

    @Column(name = "velocidad")
    private Double velocidad;

    @Column(name = "lugar")
    private Integer lugar;

    @Column(name = "puntos")
    private Double puntos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "premios", "color", "colombofilo" }, allowSetters = true)
    private Paloma paloma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "colombofiloVuelos", "premios", "paradero" }, allowSetters = true)
    private Vuelo vuelo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Double getPlus() {
        return plus;
    }

    public void setPlus(Double plus) {
        this.plus = plus;
    }

    public Long getId() {
        return this.id;
    }

    public Premio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignada() {
        return this.designada;
    }

    public Premio designada(String designada) {
        this.setDesignada(designada);
        return this;
    }

    public void setDesignada(String designada) {
        this.designada = designada;
    }

    public Instant getFechaArribo() {
        return this.fechaArribo;
    }

    public Premio fechaArribo(Instant fechaArribo) {
        this.setFechaArribo(fechaArribo);
        return this;
    }

    public void setFechaArribo(Instant fechaArribo) {
        this.fechaArribo = fechaArribo;
    }

    public Double getTiempoVuelo() {
        return this.tiempoVuelo;
    }

    public Premio tiempoVuelo(Double tiempoVuelo) {
        this.setTiempoVuelo(tiempoVuelo);
        return this;
    }

    public void setTiempoVuelo(Double tiempoVuelo) {
        this.tiempoVuelo = tiempoVuelo;
    }

    public Double getVelocidad() {
        return this.velocidad;
    }

    public Premio velocidad(Double velocidad) {
        this.setVelocidad(velocidad);
        return this;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    public Integer getLugar() {
        return this.lugar;
    }

    public Premio lugar(Integer lugar) {
        this.setLugar(lugar);
        return this;
    }

    public void setLugar(Integer lugar) {
        this.lugar = lugar;
    }

    public Double getPuntos() {
        return this.puntos;
    }

    public Premio puntos(Double puntos) {
        this.setPuntos(puntos);
        return this;
    }

    public void setPuntos(Double puntos) {
        this.puntos = puntos;
    }

    public Paloma getPaloma() {
        return this.paloma;
    }

    public void setPaloma(Paloma paloma) {
        this.paloma = paloma;
    }

    public Premio paloma(Paloma paloma) {
        this.setPaloma(paloma);
        return this;
    }

    public Vuelo getVuelo() {
        return this.vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Premio vuelo(Vuelo vuelo) {
        this.setVuelo(vuelo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Premio)) {
            return false;
        }
        return getId() != null && getId().equals(((Premio) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Premio{" +
            "id=" + getId() +
            ", designada='" + getDesignada() + "'" +
            ", fechaArribo='" + getFechaArribo() + "'" +
            ", tiempoVuelo=" + getTiempoVuelo() +
            ", velocidad=" + getVelocidad() +
            ", lugar=" + getLugar() +
            ", puntos=" + getPuntos() +
            "}";
    }
}
