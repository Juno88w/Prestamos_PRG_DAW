import java.time.*;
import java.time.temporal.*;

public class Usuario {
    private String nombre;
    private String email;
    private String numeroSocio;
    private LocalDate fechaRegistro;
    private boolean sancionado;
    private LocalDate fechaFinSancion;

    Usuario(String nombre, String email, String numeroSocio, LocalDate fechaRegistro) throws UsuarioInvalidoException{
        if(nombre==null){
            throw new UsuarioInvalidoException("No has indicado el nombre");
        }
        if(email==null || !email.contains("@") || !email.contains(".")){
            throw new UsuarioInvalidoException("El email no puede ser nulo y tiene que contener un @ y un punto(.)");
        }
        if(numeroSocio==null || !numeroSocio.matches("SOC\\d{5}")){
            throw new UsuarioInvalidoException("El número de socio no puede ser nulo y tiene que tener este formato: SOC12345");
        }
        if(fechaRegistro==null){
            throw new UsuarioInvalidoException("La fecha de registro no puede ser nula");
        }
        this.nombre=nombre;
        this.email = email;
        this.numeroSocio=numeroSocio;
        this.fechaRegistro=fechaRegistro;
    }
    public void sancionar(int sancion, LocalDate fechaInicio){
        this.sancionado=true;
        this.fechaFinSancion=fechaInicio.plusDays(sancion);
    }
    public void levantarSancion(){
        this.sancionado=false;
        this.fechaFinSancion=null;
    }
    public boolean estaSancionado() {
        return this.sancionado;
    }
    @Override
    public String toString(){
        if(sancionado) {
            return "Usuario: " + this.nombre + " email: " + this.email + " Numero Socio: " + this.numeroSocio
                    + " Fecha Registro: " + this.fechaRegistro + " Sancionado: " + (sancionado? "SI" : "NO");
        }
        else{
            return "Usuario: " + this.nombre + " email: " + this.email + " Numero Socio: " + this.numeroSocio
                    + " Fecha Registro: " + this.fechaRegistro + " Sancionado: " + (sancionado? "SI" : "NO") + " Fecha Fin Sanción: " + fechaFinSancion;
        }
    }
    public String getNombre(){
        return this.nombre;
    }
}
