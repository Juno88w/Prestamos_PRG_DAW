import java.time.*;
import java.time.temporal.*;

public class GestorBiblioteca {
    private static final int MAX_USUARIOS = 50;
    private static final int MAX_PRESTAMOS = 200;
    private Usuario[] usuarios;
    private Prestamo[] prestamos;
    private int numeroUsuarios;
    private int numeroPrestamos;

    public GestorBiblioteca(){
        Usuario[] usuarios = new Usuario[MAX_USUARIOS];
        Prestamo[] prestamos = new Prestamo[MAX_PRESTAMOS];
        numeroUsuarios=0;
        numeroPrestamos=0;
    }
    public void registrarUsuario(Usuario usuario)throws UsuarioRepetidoException{
        for(int i=0;i<usuarios.length;i++){
            if(usuarios[i]==usuario){
                throw new UsuarioRepetidoException("Este usuario ya está registrado");
            }
        }
        usuarios[numeroUsuarios]=usuario;
        numeroUsuarios++;
    }
    public Prestamo realizarPrestamo(String codigoLibro, String tituloLibro, LocalDate fechaPrestamo, Usuario usuario)
            throws PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException{
        if(codigoLibro == null || !codigoLibro.matches("\\p{Lu}{3}\\d{4}")){
            throw new PrestamoInvalidoException("El código del Libro no puede ser nulo y tiene que tener este formato LIB1234");
        }
        if(tituloLibro == null){
            throw new PrestamoInvalidoException("El título del libro no puede ser nulo.");
        }
        if(fechaPrestamo == null || fechaPrestamo.isBefore(LocalDate.now())){
            throw new PrestamoInvalidoException("La fecha de prestamo no puede ser nula o posterior a la de hoy");
        }
        if(usuario.estaSancionado()){
            throw new UsuarioSancionadoException("El usuario está sancionado.");
        }
        for(int i=0;i<prestamos.length;i++){
            if(prestamos[i].getCodigoLibro().equals(codigoLibro) && prestamos[i].getFechaDevolucionReal()==null){
                throw new LibroNoDisponibleException("El libro está actualmente prestado");
            }
        }
        Prestamo prestamo = new Prestamo(codigoLibro, usuario, tituloLibro, fechaPrestamo);
        numeroPrestamos++;
        return prestamo;
    }
    public boolean devolverLibro(String codigoLibro, LocalDate fechaDevolucion)
            throws PrestamoInvalidoException{
        for(int i=0;i<=numeroPrestamos;i++){
            LocalDate fechaDevolucionPrevista = prestamos[i].getFechaDevolucionPrevista();
            String libro=prestamos[i].getCodigoLibro();
            //Lanzar excepcion si la fecha de devolucion es anterior a la de prestamo
            if(libro.equals(codigoLibro) && fechaDevolucion.isBefore(prestamos[i].getFechaPrestamo())){
                throw new PrestamoInvalidoException("La fecha de devolución es anterior a la de préstamo");
            }
            /*Si es el libro y la fecha de devolucion prevista es posterior o igual a la fecha de devolucion
             registramos la devolucion como la fecha de devolucion*/
            else if(libro.equals(codigoLibro) &&
                    fechaDevolucionPrevista.isAfter(fechaDevolucion) ||
                    fechaDevolucionPrevista.isEqual(fechaDevolucion)){
                prestamos[i].registrarDevolucion(fechaDevolucion);
                return true;
            }
            /* Si es el libro y la fecha de devolucion prevista es anterior a la fecha de devolucion
            sancionamos al usuario con un día por cada día de retraso*/
            else if(libro.equals(codigoLibro) &&
                    fechaDevolucionPrevista.isBefore(fechaDevolucion)){
                long retraso=ChronoUnit.DAYS.between(fechaDevolucion, fechaDevolucionPrevista);
                prestamos[i].socio.sancionar((int)retraso, fechaDevolucion);
                return true;
            }
            //Si es el libro y la fecha de devolucion no es nula es que ya se ha devuelto
            else if(libro.equals(codigoLibro) && prestamos[i].getFechaDevolucionReal()!=null){
                return false;
            }
        }
        //En cualquier otro caso en el que no encuentra el libro devuelve false
        return false;
    }
    public Usuario buscarUsuario(String codigo){
        for(int i=0; i<=numeroUsuarios;i++){
            if(codigo.equals(usuarios[i].getNumeroSocio())){
                return usuarios[i];
            }
        }
        return null; //En cualquier otro caso devuelve null
    }
    public Prestamo getPrestamos(){
        return prestamos[numeroPrestamos];
    }
    public Usuario getUsuarios(){
        return usuarios[numeroUsuarios];
    }
    public String toString(){
        return "Prestamos: \n" + this.getPrestamos() + "\nUsuarios: \n" + this.getUsuarios();
    }
}
