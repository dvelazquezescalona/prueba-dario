package cu.fcc.pigeon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.experimental.SuperBuilder;

/**
 * A Color.
 */
@Entity
@Table(name = "color")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Color implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_color", nullable = false)
    private String nombreColor;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "color")
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "premios", "color", "colombofilo" }, allowSetters = true)
    private Set<Paloma> palomas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Color id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreColor() {
        return this.nombreColor;
    }

    public Color nombreColor(String nombreColor) {
        this.setNombreColor(nombreColor);
        return this;
    }

    public void setNombreColor(String nombreColor) {
        this.nombreColor = nombreColor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Set<Paloma> getPalomas() {
        return this.palomas;
    }

    public void setPalomas(Set<Paloma> palomas) {
        if (this.palomas != null) {
            this.palomas.forEach(i -> i.setColor(null));
        }
        if (palomas != null) {
            palomas.forEach(i -> i.setColor(this));
        }
        this.palomas = palomas;
    }

    public Color palomas(Set<Paloma> palomas) {
        this.setPalomas(palomas);
        return this;
    }

    public Color addPaloma(Paloma paloma) {
        this.palomas.add(paloma);
        paloma.setColor(this);
        return this;
    }

    public Color removePaloma(Paloma paloma) {
        this.palomas.remove(paloma);
        paloma.setColor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Color)) {
            return false;
        }
        return getId() != null && getId().equals(((Color) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Color{" + "id=" + id + ", nombreColor='" + nombreColor + '\'' + ", codigo='" + codigo + '\'' + '}';
    }
}
