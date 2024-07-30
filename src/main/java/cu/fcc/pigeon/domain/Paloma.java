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
 * A Paloma.
 */
@Entity
@Table(name = "paloma")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Paloma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "anilla", nullable = false)
    private String anilla;

    @NotNull
    @Column(name = "anno", nullable = false)
    private String anno;

    @NotNull
    @Column(name = "pais", nullable = false)
    private Long pais;

    @NotNull
    @Column(name = "sexo", nullable = false)
    private Boolean sexo;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paloma")
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paloma", "vuelo" }, allowSetters = true)
    private Set<Premio> premios = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "palomas" }, allowSetters = true)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "palomas", "colombofiloVuelos", "sociedad" }, allowSetters = true)
    private Colombofilo colombofilo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paloma id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnilla() {
        return this.anilla;
    }

    public Paloma anilla(String anilla) {
        this.setAnilla(anilla);
        return this;
    }

    public void setAnilla(String anilla) {
        this.anilla = anilla;
    }

    public String getAnno() {
        return this.anno;
    }

    public Paloma anno(String anno) {
        this.setAnno(anno);
        return this;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public Long getPais() {
        return this.pais;
    }

    public Paloma pais(Long pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(Long pais) {
        this.pais = pais;
    }

    public Boolean getSexo() {
        return this.sexo;
    }

    public Paloma sexo(Boolean sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Paloma activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Premio> getPremios() {
        return this.premios;
    }

    public void setPremios(Set<Premio> premios) {
        if (this.premios != null) {
            this.premios.forEach(i -> i.setPaloma(null));
        }
        if (premios != null) {
            premios.forEach(i -> i.setPaloma(this));
        }
        this.premios = premios;
    }

    public Paloma premios(Set<Premio> premios) {
        this.setPremios(premios);
        return this;
    }

    public Paloma addPremio(Premio premio) {
        this.premios.add(premio);
        premio.setPaloma(this);
        return this;
    }

    public Paloma removePremio(Premio premio) {
        this.premios.remove(premio);
        premio.setPaloma(null);
        return this;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Paloma color(Color color) {
        this.setColor(color);
        return this;
    }

    public Colombofilo getColombofilo() {
        return this.colombofilo;
    }

    public void setColombofilo(Colombofilo colombofilo) {
        this.colombofilo = colombofilo;
    }

    public Paloma colombofilo(Colombofilo colombofilo) {
        this.setColombofilo(colombofilo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paloma)) {
            return false;
        }
        return getId() != null && getId().equals(((Paloma) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paloma{" +
            "id=" + getId() +
            ", anilla='" + getAnilla() + "'" +
            ", anno='" + getAnno() + "'" +
            ", pais='" + getPais() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", activo='" + getActivo() + "'" +
            "}";
    }
}
