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
 * A Provincia.
 */
@Entity
@Table(name = "provincia")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Provincia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_provincia", nullable = false)
    private String nombreProvincia;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "provincia")
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sociedads", "provincia" }, allowSetters = true)
    private Set<Municipio> municipios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getId() {
        return this.id;
    }

    public Provincia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProvincia() {
        return this.nombreProvincia;
    }

    public Provincia nombreProvincia(String nombreProvincia) {
        this.setNombreProvincia(nombreProvincia);
        return this;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    public Set<Municipio> getMunicipios() {
        return this.municipios;
    }

    public void setMunicipios(Set<Municipio> municipios) {
        if (this.municipios != null) {
            this.municipios.forEach(i -> i.setProvincia(null));
        }
        if (municipios != null) {
            municipios.forEach(i -> i.setProvincia(this));
        }
        this.municipios = municipios;
    }

    public Provincia municipios(Set<Municipio> municipios) {
        this.setMunicipios(municipios);
        return this;
    }

    public Provincia addMunicipio(Municipio municipio) {
        this.municipios.add(municipio);
        municipio.setProvincia(this);
        return this;
    }

    public Provincia removeMunicipio(Municipio municipio) {
        this.municipios.remove(municipio);
        municipio.setProvincia(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Provincia)) {
            return false;
        }
        return getId() != null && getId().equals(((Provincia) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Provincia{" +
            "id=" + getId() +
            ", nombreProvincia='" + getNombreProvincia() + "'" +
            "}";
    }
}
