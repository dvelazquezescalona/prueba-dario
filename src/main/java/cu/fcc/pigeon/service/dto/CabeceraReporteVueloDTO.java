package cu.fcc.pigeon.service.dto;

public class CabeceraReporteVueloDTO {

    private String fecha;
    private String competencia;
    private String campeonato;
    private String sociedad;
    private String paradero;
    private Integer distancia;
    private Integer enviadas;
    private Integer premios;

    public CabeceraReporteVueloDTO(
        String fecha,
        String competencia,
        String campeonato,
        String sociedad,
        String paradero,
        Integer distancia,
        Integer enviadas,
        Integer premios
    ) {
        this.fecha = fecha;
        this.competencia = competencia;
        this.campeonato = campeonato;
        this.sociedad = sociedad;
        this.paradero = paradero;
        this.distancia = distancia;
        this.enviadas = enviadas;
        this.premios = premios;
    }

    public CabeceraReporteVueloDTO() {}

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(String campeonato) {
        this.campeonato = campeonato;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getParadero() {
        return paradero;
    }

    public void setParadero(String paradero) {
        this.paradero = paradero;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
    }

    public Integer getEnviadas() {
        return enviadas;
    }

    public void setEnviadas(Integer enviadas) {
        this.enviadas = enviadas;
    }

    public Integer getPremios() {
        return premios;
    }

    public void setPremios(Integer premios) {
        this.premios = premios;
    }

    @Override
    public String toString() {
        return (
            "CabeceraReporteVueloDTO{" +
            "fecha='" +
            fecha +
            '\'' +
            ", competencia='" +
            competencia +
            '\'' +
            ", campeonato='" +
            campeonato +
            '\'' +
            ", sociedad='" +
            sociedad +
            '\'' +
            ", paradero='" +
            paradero +
            '\'' +
            ", distancia=" +
            distancia +
            ", enviadas=" +
            enviadas +
            ", premios=" +
            premios +
            '}'
        );
    }
}
