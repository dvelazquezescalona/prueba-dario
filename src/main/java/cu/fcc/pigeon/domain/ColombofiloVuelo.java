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
 * A ColombofiloVuelo.
 */
@Entity
@Table(name = "colombofilo_vuelo")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ColombofiloVuelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "enviadas", nullable = false)
    private Integer enviadas;

    @NotNull
    @Column(name = "distancia", nullable = false)
    private Double distancia;

    @NotNull
    @Column(name = "apertura_oficial", nullable = false)
    private Instant aperturaOficial;

    @NotNull
    @Column(name = "cierre_oficial", nullable = false)
    private Instant cierreOficial;

    @NotNull
    @Column(name = "cierre", nullable = false)
    private Instant cierre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "palomas", "colombofiloVuelos", "sociedad" }, allowSetters = true)
    private Colombofilo colombofilo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "colombofiloVuelos", "premios", "paradero" }, allowSetters = true)
    private Vuelo vuelo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Instant getCierreOficial() {
        return cierreOficial;
    }

    public void setCierreOficial(Instant cierreOficial) {
        this.cierreOficial = cierreOficial;
    }

    public Instant getCierre() {
        return cierre;
    }

    public void setCierre(Instant cierre) {
        this.cierre = cierre;
    }

    public Long getId() {
        return this.id;
    }

    public ColombofiloVuelo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEnviadas() {
        return this.enviadas;
    }

    public ColombofiloVuelo enviadas(Integer enviadas) {
        this.setEnviadas(enviadas);
        return this;
    }

    public void setEnviadas(Integer enviadas) {
        this.enviadas = enviadas;
    }

    public Double getDistancia() {
        return this.distancia;
    }

    public ColombofiloVuelo distancia(Double distancia) {
        this.setDistancia(distancia);
        return this;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public Colombofilo getColombofilo() {
        return this.colombofilo;
    }

    public void setColombofilo(Colombofilo colombofilo) {
        this.colombofilo = colombofilo;
    }

    public ColombofiloVuelo colombofilo(Colombofilo colombofilo) {
        this.setColombofilo(colombofilo);
        return this;
    }

    public Vuelo getVuelo() {
        return this.vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public ColombofiloVuelo vuelo(Vuelo vuelo) {
        this.setVuelo(vuelo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ColombofiloVuelo)) {
            return false;
        }
        return getId() != null && getId().equals(((ColombofiloVuelo) o).getId());
    }

    public Instant getAperturaOficial() {
        return aperturaOficial;
    }

    public void setAperturaOficial(Instant aperturaOficial) {
        this.aperturaOficial = aperturaOficial;
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ColombofiloVuelo{" +
            "id=" + getId() +
            ", envidas=" + getEnviadas() +
            ", distancia=" + getDistancia() +
            "}";
    }
}
