import java.time.*;
import java.time.temporal.*;

public class Prestamo {
    private String codigoLibro;
    private String tituloLibro;
    Usuario socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;

    Prestamo(String codigoLibro, Usuario socio, String tituloLibro, LocalDate fechaPrestamo){
        this.codigoLibro=codigoLibro;
        this.socio=socio;
        this.tituloLibro=tituloLibro;
        this.fechaPrestamo=fechaPrestamo;
        this.fechaDevolucionPrevista=fechaPrestamo.plusDays(14);
    }
    public LocalDate getFechaDevolucionPrevista(){
        return fechaDevolucionPrevista;
    }
    public LocalDate getFechaDevolucionReal(){
        return this.fechaDevolucionReal;
    }
    public void registrarDevolucion(){

    }
}
