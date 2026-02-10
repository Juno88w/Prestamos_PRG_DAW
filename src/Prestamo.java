import java.time.*;
import java.time.temporal.*;

public class Prestamo {
    private String codigoLibro;
    private String tituloLibro;
    Usuario socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;

    Prestamo(String codigoLibro, Usuario socio, String tituloLibro, LocalDate fechaPrestamo) throws PrestamoInvalidoException{
        if(codigoLibro == null || !codigoLibro.matches("\\p{Lu}{3}\\d{4}")){
            throw new PrestamoInvalidoException("El código del Libro no puede ser nulo y tiene que tener este formato LIB1234");
        }
        if(tituloLibro == null){
            throw new PrestamoInvalidoException("El título del libro no puede ser nulo.");
        }
        if(fechaPrestamo == null || fechaPrestamo.isBefore(LocalDate.now())){
            throw new PrestamoInvalidoException("La fecha de prestamo no puede ser nula o posterior a la de hoy");
        }
        this.codigoLibro=codigoLibro;
        this.socio=socio;
        this.tituloLibro=tituloLibro;
        this.fechaPrestamo=fechaPrestamo;
        this.fechaDevolucionPrevista=fechaPrestamo.plusDays(14);
    }
    public void registrarDevolucion(LocalDate fechaDevolucion) throws PrestamoInvalidoException{
        if(fechaDevolucion==null || fechaDevolucion.isBefore(this.fechaPrestamo)){
            throw new PrestamoInvalidoException("La fecha de devolución no puede ser nula y no puede ser anterior a la fecha de préstamo");
        }
        this.fechaDevolucionReal=fechaDevolucion;
    }
    public int calcularDiasRetraso(){
        if(this.fechaDevolucionReal.isAfter(fechaDevolucionPrevista)){
            long diferencia = ChronoUnit.DAYS.between(fechaDevolucionReal, fechaDevolucionPrevista);
            return (int)diferencia;
        }
        else{
            return 0;
        }
    }
    public boolean estaRetrasado(){
        if(fechaDevolucionPrevista.isBefore(LocalDate.now())){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public String toString(){
        return "Código Libro: " + this.codigoLibro + ", Socio: " + this.socio.getNombre() + ", Título Libro: " + this.tituloLibro + ", Fecha Préstamo: "
                + this.fechaPrestamo + ", Fecha Devolución Prevista: " + this.fechaDevolucionPrevista;
    }
}
