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
 * A Municipio.
 */
@Entity
@Table(name = "municipio")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_municipio", nullable = false)
    private String nombreMunicipio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "municipio")
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "colombofilos", "paraderos", "municipio" }, allowSetters = true)
    private Set<Sociedad> sociedades = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "municipios" }, allowSetters = true)
    private Provincia provincia;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Municipio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreMunicipio() {
        return this.nombreMunicipio;
    }

    public Municipio nombreMunicipio(String nombreMunicipio) {
        this.setNombreMunicipio(nombreMunicipio);
        return this;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public Set<Sociedad> getSociedades() {
        return this.sociedades;
    }

    public void setSociedades(Set<Sociedad> sociedades) {
        if (this.sociedades != null) {
            this.sociedades.forEach(i -> i.setMunicipio(null));
        }
        if (sociedades != null) {
            sociedades.forEach(i -> i.setMunicipio(this));
        }
        this.sociedades = sociedades;
    }

    public Municipio sociedades(Set<Sociedad> sociedades) {
        this.setSociedades(sociedades);
        return this;
    }

    public Municipio addSociedad(Sociedad sociedad) {
        this.sociedades.add(sociedad);
        sociedad.setMunicipio(this);
        return this;
    }

    public Municipio removeSociedad(Sociedad sociedad) {
        this.sociedades.remove(sociedad);
        sociedad.setMunicipio(null);
        return this;
    }

    public Provincia getProvincia() {
        return this.provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Municipio provincia(Provincia provincia) {
        this.setProvincia(provincia);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Municipio)) {
            return false;
        }
        return getId() != null && getId().equals(((Municipio) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Municipio{" +
            "id=" + getId() +
            ", nombreMunicipio='" + getNombreMunicipio() + "'" +
            "}";
    }
}
