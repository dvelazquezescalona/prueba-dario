package cu.fcc.pigeon.service.dto;

import cu.fcc.pigeon.service.dto.enumeration.Status;
import java.io.Serializable;
import lombok.Data;

public class RequestReporteVueloDTO implements Serializable {

    private Long colombofiloId = null;
    private Long vueloId = null;
    private Long sociedadId = null;

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

    public Long getSociedadId() {
        return sociedadId;
    }

    public void setSociedadId(Long sociedadId) {
        this.sociedadId = sociedadId;
    }

    @Override
    public String toString() {
        return "RequestReporteVueloDTO{" + "colombofiloId=" + colombofiloId + ", vueloId=" + vueloId + ", sociedadId=" + sociedadId + '}';
    }
}
