package cu.fcc.pigeon.service.dto;

import cu.fcc.pigeon.service.dto.enumeration.Status;
import lombok.*;

/*@Data
@NoArgsConstructor
@AllArgsConstructor
*/
//@Builder
//@ToString
public class ResponseDTO<T> {

    private String mensaje;
    private Status status;
    private T objEntity;

    public ResponseDTO() {}

    public ResponseDTO(String mensaje, Status status, T objEntity) {
        this.mensaje = mensaje;
        this.status = status;
        this.objEntity = objEntity;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getObjEntity() {
        return objEntity;
    }

    public void setObjEntity(T objEntity) {
        this.objEntity = objEntity;
    }
}
