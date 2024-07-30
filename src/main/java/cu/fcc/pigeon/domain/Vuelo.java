package cu.fcc.pigeon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.experimental.SuperBuilder;

/**
 * A Vuelo.
 */
@Entity
@Table(name = "vuelo")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vuelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "competencia", nullable = false)
    private String competencia;

    @NotNull
    @Column(name = "campeonato", nullable = false)
    private String campeonato;

    @NotNull
    @Column(name = "p_max", nullable = false)
    private Double puntuacionMax;

    @NotNull
    @Column(name = "p_min", nullable = false)
    private Double puntuacionMin;

    @NotNull
    @Column(name = "p_system", nullable = false)
    private Integer puntuacionSystem;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vuelo")
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "colombofilo", "vuelo" }, allowSetters = true)
    private Set<ColombofiloVuelo> colombofiloVuelos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vuelo")
    @JsonIgnoreProperties(value = { "paloma", "vuelo" }, allowSetters = true)
    private Set<Premio> premios = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "vuelos", "sociedad" }, allowSetters = true)
    private Paradero paradero;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vuelo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return this.fecha;
    }

    public Vuelo fecha(Instant fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Vuelo descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCompetencia() {
        return this.competencia;
    }

    public Vuelo competencia(String competencia) {
        this.setCompetencia(competencia);
        return this;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getCampeonato() {
        return this.campeonato;
    }

    public Vuelo campeonato(String campeonato) {
        this.setCampeonato(campeonato);
        return this;
    }

    public void setCampeonato(String campeonato) {
        this.campeonato = campeonato;
    }

    public Set<ColombofiloVuelo> getColombofiloVuelos() {
        return this.colombofiloVuelos;
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

    public void setColombofiloVuelos(Set<ColombofiloVuelo> colombofiloVuelos) {
        if (this.colombofiloVuelos != null) {
            this.colombofiloVuelos.forEach(i -> i.setVuelo(null));
        }
        if (colombofiloVuelos != null) {
            colombofiloVuelos.forEach(i -> i.setVuelo(this));
        }
        this.colombofiloVuelos = colombofiloVuelos;
    }

    public Vuelo colombofiloVuelos(Set<ColombofiloVuelo> colombofiloVuelos) {
        this.setColombofiloVuelos(colombofiloVuelos);
        return this;
    }

    public Vuelo addColombofiloVuelo(ColombofiloVuelo colombofiloVuelo) {
        this.colombofiloVuelos.add(colombofiloVuelo);
        colombofiloVuelo.setVuelo(this);
        return this;
    }

    public Vuelo removeColombofiloVuelo(ColombofiloVuelo colombofiloVuelo) {
        this.colombofiloVuelos.remove(colombofiloVuelo);
        colombofiloVuelo.setVuelo(null);
        return this;
    }

    public Set<Premio> getPremios() {
        return this.premios;
    }

    public void setPremios(Set<Premio> premios) {
        if (this.premios != null) {
            this.premios.forEach(i -> i.setVuelo(null));
        }
        if (premios != null) {
            premios.forEach(i -> i.setVuelo(this));
        }
        this.premios = premios;
    }

    public Vuelo premios(Set<Premio> premios) {
        this.setPremios(premios);
        return this;
    }

    public Vuelo addPremio(Premio premio) {
        this.premios.add(premio);
        premio.setVuelo(this);
        return this;
    }

    public Vuelo removePremio(Premio premio) {
        this.premios.remove(premio);
        premio.setVuelo(null);
        return this;
    }

    public Paradero getParadero() {
        return this.paradero;
    }

    public void setParadero(Paradero paradero) {
        this.paradero = paradero;
    }

    public Vuelo paradero(Paradero paradero) {
        this.setParadero(paradero);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vuelo)) {
            return false;
        }
        return getId() != null && getId().equals(((Vuelo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vuelo{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", competencia='" + getCompetencia() + "'" +
            ", campeonato='" + getCampeonato() + "'" +
            "}";
    }
}
