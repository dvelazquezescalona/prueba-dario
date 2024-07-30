package cu.fcc.pigeon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Paradero.
 */
@Entity
@Table(name = "paradero")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Paradero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_paradero", nullable = false)
    private String nombreParadero;

    @NotNull
    @Column(name = "distancia_media", nullable = false)
    private Integer distanciaMedia;

    @NotNull
    @Column(name = "latitud", nullable = false)
    private Double latitud;

    @NotNull
    @Column(name = "longitud", nullable = false)
    private Double longitud;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paradero")
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "colombofiloVuelos", "premios", "paradero" }, allowSetters = true)
    private Set<Vuelo> vuelos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "colombofilos", "paraderos", "municipio" }, allowSetters = true)
    private Sociedad sociedad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paradero id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreParadero() {
        return this.nombreParadero;
    }

    public Paradero nombreParadero(String nombreParadero) {
        this.setNombreParadero(nombreParadero);
        return this;
    }

    public void setNombreParadero(String nombreParadero) {
        this.nombreParadero = nombreParadero;
    }

    public Integer getDistanciaMedia() {
        return this.distanciaMedia;
    }

    public Paradero distanciaMedia(Integer distanciaMedia) {
        this.setDistanciaMedia(distanciaMedia);
        return this;
    }

    public void setDistanciaMedia(Integer distanciaMedia) {
        this.distanciaMedia = distanciaMedia;
    }

    public Double getLatitud() {
        return this.latitud;
    }

    public Paradero latitud(Double latitud) {
        this.setLatitud(latitud);
        return this;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return this.longitud;
    }

    public Paradero longitud(Double longitud) {
        this.setLongitud(longitud);
        return this;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Set<Vuelo> getVuelos() {
        return this.vuelos;
    }

    public void setVuelos(Set<Vuelo> vuelos) {
        if (this.vuelos != null) {
            this.vuelos.forEach(i -> i.setParadero(null));
        }
        if (vuelos != null) {
            vuelos.forEach(i -> i.setParadero(this));
        }
        this.vuelos = vuelos;
    }

    public Paradero vuelos(Set<Vuelo> vuelos) {
        this.setVuelos(vuelos);
        return this;
    }

    public Paradero addVuelo(Vuelo vuelo) {
        this.vuelos.add(vuelo);
        vuelo.setParadero(this);
        return this;
    }

    public Paradero removeVuelo(Vuelo vuelo) {
        this.vuelos.remove(vuelo);
        vuelo.setParadero(null);
        return this;
    }

    public Sociedad getSociedad() {
        return this.sociedad;
    }

    public void setSociedad(Sociedad sociedad) {
        this.sociedad = sociedad;
    }

    public Paradero sociedad(Sociedad sociedad) {
        this.setSociedad(sociedad);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paradero)) {
            return false;
        }
        return getId() != null && getId().equals(((Paradero) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paradero{" +
            "id=" + getId() +
            ", nombreParadero='" + getNombreParadero() + "'" +
            ", distanciaMedia=" + getDistanciaMedia() +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            "}";
    }
}
