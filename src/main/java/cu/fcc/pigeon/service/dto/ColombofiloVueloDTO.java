package cu.fcc.pigeon.service.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * A ColombofiloVueloDTO.
 */

@NoArgsConstructor
@AllArgsConstructor
public class ColombofiloVueloDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer enviadas;

    @NotNull
    private Double distancia;

    private Instant aperturaOficial;

    private Instant cierreOficial;

    private Instant cierre;

    private Long colombofiloId;

    private Long vueloId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEnviadas() {
        return enviadas;
    }

    public void setEnviadas(Integer enviadas) {
        this.enviadas = enviadas;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public Long getColombofiloId() {
        return colombofiloId;
    }

    public void setColombofiloId(Long colombofiloId) {
        this.colombofiloId = colombofiloId;
    }

    public Long getVueloId() {
        return vueloId;
    }

    public void setVueloId(Long vueloId) {
        this.vueloId = vueloId;
    }

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

    public Instant getAperturaOficial() {
        return aperturaOficial;
    }

    public void setAperturaOficial(Instant aperturaOficial) {
        this.aperturaOficial = aperturaOficial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColombofiloVueloDTO that = (ColombofiloVueloDTO) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(enviadas, that.enviadas) &&
            Objects.equals(distancia, that.distancia) &&
            Objects.equals(aperturaOficial, that.aperturaOficial) &&
            Objects.equals(cierreOficial, that.cierreOficial) &&
            Objects.equals(cierre, that.cierre) &&
            Objects.equals(colombofiloId, that.colombofiloId) &&
            Objects.equals(vueloId, that.vueloId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enviadas, distancia, aperturaOficial, cierreOficial, cierre, colombofiloId, vueloId);
    }

    @Override
    public String toString() {
        return (
            "ColombofiloVueloDTO{" +
            "id=" +
            id +
            ", enviadas=" +
            enviadas +
            ", distancia=" +
            distancia +
            ", aperturaOficial=" +
            aperturaOficial +
            ", cierreOficial=" +
            cierreOficial +
            ", cierre=" +
            cierre +
            ", colombofiloId=" +
            colombofiloId +
            ", vueloId=" +
            vueloId +
            '}'
        );
    }
}
