import java.time.*;

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
        return prestamo;
    }
    public boolean devolverLibro(String codigoLibro, LocalDate fechaDevolucion) throws PrestamoInvalidoException{

    }
}
