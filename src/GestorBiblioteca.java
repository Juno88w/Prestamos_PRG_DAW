public class GestorBiblioteca {
    private static final int MAX_USUARIOS = 50;
    private static final int MAX_PRESTAMOS = 200;
    private Usuario[] usuarios; //new Usuario[MAX_USUARIOS];
    private Prestamo[] prestamos; //new Prestamo[MAX_PRESTAMOS];
    private int numeroUsuarios;
    private int numeroPrestamos;

    public GestorBiblioteca(){
        Usuario[] usuarios = new Usuario[MAX_USUARIOS];
        Prestamo[] prestamos = new Prestamo[MAX_PRESTAMOS];
        numeroUsuarios=0;
        numeroPrestamos=0;
    }
}
