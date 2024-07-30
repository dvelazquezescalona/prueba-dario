package cu.fcc.pigeon.service.dto;

public class ReporteVueloDTO {

    private Integer lugar;
    private String nombre;
    private String anilla;
    private String anno;
    private String pais;
    private String sexo;
    private String color;
    private String tiempo;
    private String designada;
    private Double distancia;
    private Double puntos;
    private Double velocidad;
    private String fecha;
    private String sociedad;

    public Integer getLugar() {
        return lugar;
    }

    public void setLugar(Integer lugar) {
        this.lugar = lugar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnilla() {
        return anilla;
    }

    public void setAnilla(String anilla) {
        this.anilla = anilla;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getDesignada() {
        return designada;
    }

    public void setDesignada(String designada) {
        this.designada = designada;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public Double getPuntos() {
        return puntos;
    }

    public void setPuntos(Double puntos) {
        this.puntos = puntos;
    }

    public Double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    @Override
    public String toString() {
        return (
            "ReporteVueloDTO{" +
            "lugar=" +
            lugar +
            ", nombre='" +
            nombre +
            '\'' +
            ", anilla='" +
            anilla +
            '\'' +
            ", anno='" +
            anno +
            '\'' +
            ", pais='" +
            pais +
            '\'' +
            ", sexo='" +
            sexo +
            '\'' +
            ", color='" +
            color +
            '\'' +
            ", tiempo='" +
            tiempo +
            '\'' +
            ", designada='" +
            designada +
            '\'' +
            ", distancia=" +
            distancia +
            ", puntos=" +
            puntos +
            ", velocidad=" +
            velocidad +
            ", fecha='" +
            fecha +
            '\'' +
            ", sociedad='" +
            sociedad +
            '\'' +
            '}'
        );
    }
}
