package easybalads.easybalads;

/**
 * Created by L.A.S.S on 12/11/2017.
 */

public class Eventos {
    private String titulo;
    private String dt;
    private String hora;
    private String participantes;


    public Eventos() {
    }

    public Eventos(String titulo, String dt, String hora,String participantes) {
        super();
        this.titulo = titulo;
        this.dt = dt;
        this.hora = hora;
        this.participantes = participantes;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.titulo = hora;
    }

    public String getParticipantes() {
        return participantes;
    }

    public void setParticipantes(String participantes) {
        this.titulo = participantes;
    }
}
