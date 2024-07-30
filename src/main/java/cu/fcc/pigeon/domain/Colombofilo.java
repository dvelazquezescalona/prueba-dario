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
 * A Colombofilo.
 */
@Entity
@Table(name = "colombofilo")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Colombofilo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @NotNull
    @Column(name = "segindo_apellido", nullable = false)
    private String segindoApellido;

    @NotNull
    @Column(name = "ci", nullable = false)
    private String ci;

    @NotNull
    @Column(name = "latitud", nullable = false)
    private Double latitud;

    @NotNull
    @Column(name = "longitud", nullable = false)
    private Double longitud;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "categoria", nullable = false)
    private String categoria;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "zona")
    private String zona;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "colombofilo")
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "premios", "color", "colombofilo" }, allowSetters = true)
    private Set<Paloma> palomas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "colombofilo")
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "colombofilo", "vuelo" }, allowSetters = true)
    private Set<ColombofiloVuelo> colombofiloVuelos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "colombofilos", "paraderos", "municipio" }, allowSetters = true)
    private Sociedad sociedad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Colombofilo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Colombofilo nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public Colombofilo primerApellido(String primerApellido) {
        this.setPrimerApellido(primerApellido);
        return this;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegindoApellido() {
        return this.segindoApellido;
    }

    public Colombofilo segindoApellido(String segindoApellido) {
        this.setSegindoApellido(segindoApellido);
        return this;
    }

    public void setSegindoApellido(String segindoApellido) {
        this.segindoApellido = segindoApellido;
    }

    public String getCi() {
        return this.ci;
    }

    public Colombofilo ci(String ci) {
        this.setCi(ci);
        return this;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public Double getLatitud() {
        return this.latitud;
    }

    public Colombofilo latitud(Double latitud) {
        this.setLatitud(latitud);
        return this;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return this.longitud;
    }

    public Colombofilo longitud(Double longitud) {
        this.setLongitud(longitud);
        return this;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Colombofilo direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public Colombofilo categoria(String categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Colombofilo telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getZona() {
        return this.zona;
    }

    public Colombofilo zona(String zona) {
        this.setZona(zona);
        return this;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Set<Paloma> getPalomas() {
        return this.palomas;
    }

    public void setPalomas(Set<Paloma> palomas) {
        if (this.palomas != null) {
            this.palomas.forEach(i -> i.setColombofilo(null));
        }
        if (palomas != null) {
            palomas.forEach(i -> i.setColombofilo(this));
        }
        this.palomas = palomas;
    }

    public Colombofilo palomas(Set<Paloma> palomas) {
        this.setPalomas(palomas);
        return this;
    }

    public Colombofilo addPaloma(Paloma paloma) {
        this.palomas.add(paloma);
        paloma.setColombofilo(this);
        return this;
    }

    public Colombofilo removePaloma(Paloma paloma) {
        this.palomas.remove(paloma);
        paloma.setColombofilo(null);
        return this;
    }

    public Set<ColombofiloVuelo> getColombofiloVuelos() {
        return this.colombofiloVuelos;
    }

    public void setColombofiloVuelos(Set<ColombofiloVuelo> colombofiloVuelos) {
        if (this.colombofiloVuelos != null) {
            this.colombofiloVuelos.forEach(i -> i.setColombofilo(null));
        }
        if (colombofiloVuelos != null) {
            colombofiloVuelos.forEach(i -> i.setColombofilo(this));
        }
        this.colombofiloVuelos = colombofiloVuelos;
    }

    public Colombofilo colombofiloVuelos(Set<ColombofiloVuelo> colombofiloVuelos) {
        this.setColombofiloVuelos(colombofiloVuelos);
        return this;
    }

    public Colombofilo addColombofiloVuelo(ColombofiloVuelo colombofiloVuelo) {
        this.colombofiloVuelos.add(colombofiloVuelo);
        colombofiloVuelo.setColombofilo(this);
        return this;
    }

    public Colombofilo removeColombofiloVuelo(ColombofiloVuelo colombofiloVuelo) {
        this.colombofiloVuelos.remove(colombofiloVuelo);
        colombofiloVuelo.setColombofilo(null);
        return this;
    }

    public Sociedad getSociedad() {
        return this.sociedad;
    }

    public void setSociedad(Sociedad sociedad) {
        this.sociedad = sociedad;
    }

    public Colombofilo sociedad(Sociedad sociedad) {
        this.setSociedad(sociedad);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Colombofilo)) {
            return false;
        }
        return getId() != null && getId().equals(((Colombofilo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Colombofilo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segindoApellido='" + getSegindoApellido() + "'" +
            ", ci='" + getCi() + "'" +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            ", direccion='" + getDireccion() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", zona='" + getZona() + "'" +
            "}";
    }
}
