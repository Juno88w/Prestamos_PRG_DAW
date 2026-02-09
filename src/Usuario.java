import java.time.*;
import java.time.temporal.*;

public class Usuario {
    private String nombre;
    private String email;
    private String numeroSocio;
    private LocalDate fechaRegistro;
    private boolean sancionado;
    private LocalDate fechaFinSancion;

    Usuario(String nombre, String email, String numeroSocio, LocalDate fechaRegistro){
        this.nombre=nombre;
        this.email=email;
        this.numeroSocio=numeroSocio;
        this.fechaRegistro=fechaRegistro;
    }
    public void sancionar(int sancion){
        this.sancionado=true;

    }
    public void levantarSancion(){
        this.sancionado=false;
        this.fechaFinSancion=null;
    }
    public boolean estaSancionado(){
        return this.sancionado;
    }
}
