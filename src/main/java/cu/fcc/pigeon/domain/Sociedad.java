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
 * A Sociedad.
 */
@Entity
@Table(name = "sociedad")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sociedad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_sociedad", nullable = false)
    private String nombreSociedad;

    @NotNull
    @Column(name = "latitud", nullable = false)
    private Double latitud;

    @NotNull
    @Column(name = "longitud", nullable = false)
    private Double longitud;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sociedad")
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "palomas", "colombofiloVuelos", "sociedad" }, allowSetters = true)
    private Set<Colombofilo> colombofilos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sociedad")
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vuelos", "sociedad" }, allowSetters = true)
    private Set<Paradero> paraderos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sociedads", "provincia" }, allowSetters = true)
    private Municipio municipio;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sociedad id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreSociedad() {
        return this.nombreSociedad;
    }

    public Sociedad nombreSociedad(String nombreSociedad) {
        this.setNombreSociedad(nombreSociedad);
        return this;
    }

    public void setNombreSociedad(String nombreSociedad) {
        this.nombreSociedad = nombreSociedad;
    }

    public Double getLatitud() {
        return this.latitud;
    }

    public Sociedad latitud(Double latitud) {
        this.setLatitud(latitud);
        return this;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return this.longitud;
    }

    public Sociedad longitud(Double longitud) {
        this.setLongitud(longitud);
        return this;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Set<Colombofilo> getColombofilos() {
        return this.colombofilos;
    }

    public void setColombofilos(Set<Colombofilo> colombofilos) {
        if (this.colombofilos != null) {
            this.colombofilos.forEach(i -> i.setSociedad(null));
        }
        if (colombofilos != null) {
            colombofilos.forEach(i -> i.setSociedad(this));
        }
        this.colombofilos = colombofilos;
    }

    public Sociedad colombofilos(Set<Colombofilo> colombofilos) {
        this.setColombofilos(colombofilos);
        return this;
    }

    public Sociedad addColombofilo(Colombofilo colombofilo) {
        this.colombofilos.add(colombofilo);
        colombofilo.setSociedad(this);
        return this;
    }

    public Sociedad removeColombofilo(Colombofilo colombofilo) {
        this.colombofilos.remove(colombofilo);
        colombofilo.setSociedad(null);
        return this;
    }

    public Set<Paradero> getParaderos() {
        return this.paraderos;
    }

    public void setParaderos(Set<Paradero> paraderos) {
        if (this.paraderos != null) {
            this.paraderos.forEach(i -> i.setSociedad(null));
        }
        if (paraderos != null) {
            paraderos.forEach(i -> i.setSociedad(this));
        }
        this.paraderos = paraderos;
    }

    public Sociedad paraderos(Set<Paradero> paraderos) {
        this.setParaderos(paraderos);
        return this;
    }

    public Sociedad addParadero(Paradero paradero) {
        this.paraderos.add(paradero);
        paradero.setSociedad(this);
        return this;
    }

    public Sociedad removeParadero(Paradero paradero) {
        this.paraderos.remove(paradero);
        paradero.setSociedad(null);
        return this;
    }

    public Municipio getMunicipio() {
        return this.municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Sociedad municipio(Municipio municipio) {
        this.setMunicipio(municipio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sociedad)) {
            return false;
        }
        return getId() != null && getId().equals(((Sociedad) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sociedad{" +
            "id=" + getId() +
            ", nombreSociedad='" + getNombreSociedad() + "'" +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            "}";
    }
}
