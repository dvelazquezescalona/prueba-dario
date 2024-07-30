package cu.fcc.pigeon.service.dto;

import cu.fcc.pigeon.domain.ColombofiloVuelo;
import cu.fcc.pigeon.domain.Paloma;
import cu.fcc.pigeon.service.dto.enumeration.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * A ColombofiloDTO.
 */

@NoArgsConstructor
@AllArgsConstructor
public class ColombofiloDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String primerApellido;

    @NotNull
    private String segindoApellido;

    @NotNull
    private String ci;

    @NotNull
    private Double latitud;

    @NotNull
    private Double longitud;

    @NotNull
    private String direccion;

    @NotNull
    private String categoria;

    private String telefono;

    private String zona;

    private Long sociedadId;

    public Long getSociedadId() {
        return sociedadId;
    }

    public void setSociedadId(Long sociedadId) {
        this.sociedadId = sociedadId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegindoApellido() {
        return segindoApellido;
    }

    public void setSegindoApellido(String segindoApellido) {
        this.segindoApellido = segindoApellido;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    @Override
    public String toString() {
        return (
            "ColombofiloDTO{" +
            "id=" +
            id +
            ", nombre='" +
            nombre +
            '\'' +
            ", primerApellido='" +
            primerApellido +
            '\'' +
            ", segindoApellido='" +
            segindoApellido +
            '\'' +
            ", ci='" +
            ci +
            '\'' +
            ", latitud=" +
            latitud +
            ", longitud=" +
            longitud +
            ", direccion='" +
            direccion +
            '\'' +
            ", categoria='" +
            categoria +
            '\'' +
            ", telefono='" +
            telefono +
            '\'' +
            ", zona='" +
            zona +
            '\'' +
            ", sociedad=" +
            sociedadId +
            '}'
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColombofiloDTO that = (ColombofiloDTO) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(primerApellido, that.primerApellido) &&
            Objects.equals(segindoApellido, that.segindoApellido) &&
            Objects.equals(ci, that.ci) &&
            Objects.equals(latitud, that.latitud) &&
            Objects.equals(longitud, that.longitud) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(categoria, that.categoria) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(zona, that.zona) &&
            Objects.equals(sociedadId, that.sociedadId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nombre,
            primerApellido,
            segindoApellido,
            ci,
            latitud,
            longitud,
            direccion,
            categoria,
            telefono,
            zona,
            sociedadId
        );
    }
}
