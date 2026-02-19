import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
public class Menu {
    Scanner in = new Scanner(System.in);
    GestorBiblioteca gestor = new GestorBiblioteca();
    public void mostrarMenu() throws UsuarioInvalidoException, FechaInvalidaException, UsuarioSancionadoException, UsuarioRepetidoException, PrestamoInvalidoException, LibroNoDisponibleException {
        int opc=0;
        do{
            System.out.println("===\tSISTEMA GESTIÓN BIBLIOTECA\t===");
            System.out.println("1. Registrar nuevo usuario");
            System.out.println("2. Realizar préstamo de libro");
            System.out.println("3. Devolver libro");
            System.out.println("4. Consultar estado de usuario");
            System.out.println("5. Mostrar préstamos activos");
            System.out.println("6. Mostrar usuarios sancionados");
            System.out.println("7. Actualizar sanciones");
            System.out.println("8. Salir");
            System.out.println();
            System.out.print("Escribe tu opción: ");
            try {
                opc = Integer.parseInt(in.nextLine().trim());
            }
            catch (NumberFormatException nfe){
                System.out.print(""); //Si no es un número hará el default del switch por lo que escribirá Escoge una opción válida
            }
            switch (opc){
                case 1:
                    this.registrarNuevoUsuario();
                    break;
                case 2:
                    this.realizarPrestamo();
                    break;
                case 3:
                    this.devolverLibro();
                    break;
                case 4:
                    this.consultarEstadoUsuario();
                    break;
                case 5:
                    this.mostrarPrestamosActivos();
                    break;
                case 6:
                    this.mostrarUsuariosSancionados();
                    break;
                case 7:
                    this.actualizarSanciones();
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Escoge una opción válida");
                    break;
            }
        }
        while(opc != 8);
        System.out.println("Saliendo...");
        in.close();
    }
    public static String formatearFecha(LocalDate fecha){
        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaformatear =fecha.format(f1);
        return fechaformatear;
    }
    public static LocalDate conversionFecha(String fecha) throws FechaInvalidaException{
        if (fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
            String dia = fecha.substring(0, 2);
            String mes = fecha.substring(3, 5);
            String anio = fecha.substring(6);
            LocalDate fechaConvertida = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia));
            return fechaConvertida;
        }
        else{
            throw new FechaInvalidaException("Debes de poner una fecha es este formato (dd/mm/aaaa).");
        }
    }
    public void registrarNuevoUsuario() throws UsuarioInvalidoException, FechaInvalidaException, UsuarioRepetidoException {
        System.out.print("Nombre: ");
        String nombre=in.nextLine().trim();
        System.out.print("Email: ");
        String email=in.nextLine().trim();
        System.out.print("Número de socio: ");
        String numSocio=in.nextLine().trim();
        System.out.print("Fecha registro (dd/mm/aaaa): ");
        String fechaRegistro=in.nextLine().trim();
        LocalDate fecha = Menu.conversionFecha(fechaRegistro);
        Usuario usuario=new Usuario(nombre, email, numSocio, fecha);
        gestor.registrarUsuario(usuario);
        System.out.println();
        System.out.println("Usuario correctamente registrado");
    }
    public void realizarPrestamo() throws FechaInvalidaException, PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException, UsuarioInvalidoException {
        System.out.print("Código libro: ");
        String codigoLibro=in.nextLine().trim();
        System.out.print("Título: ");
        String titulo = in.nextLine().trim();
        System.out.print("Número de socio: ");
        String numSocio = in.nextLine().trim();
        System.out.println("Fecha de préstamo (dd/mm/aaaa): ");
        String fechaPrestamo=in.nextLine().trim();
        LocalDate fecha = Menu.conversionFecha(fechaPrestamo);
        Usuario usuario = gestor.buscarUsuario(numSocio);
        Prestamo prestamo = gestor.realizarPrestamo(codigoLibro, titulo, fecha, usuario);
        System.out.println("Préstamo realizado");
        LocalDate fechaDevolucionPrevista = prestamo.getFechaDevolucionPrevista();
        System.out.println("Devolución prevista: " + Menu.formatearFecha(fechaDevolucionPrevista));
    }
    public void devolverLibro() throws FechaInvalidaException, PrestamoInvalidoException {
        System.out.print("Código libro: ");
        String codigoLibro=in.nextLine().trim();
        System.out.println("Fecha de devolución (dd/mm/aaaa): ");
        String fechaDevolucion=in.nextLine().trim();
        LocalDate fecha = Menu.conversionFecha(fechaDevolucion);
        boolean devolver = gestor.devolverLibro(codigoLibro, fecha);
        if(devolver == true) {
            long retraso = ChronoUnit.DAYS.between(LocalDate.now(), fecha);
            Usuario usuario = gestor.buscarUsuario(codigoLibro);
            System.out.println("Devolución retrasada con " + retraso + " días");
            System.out.println("Usuario sancionado por " + retraso + " días (Hasta el " + Menu.formatearFecha(usuario.getFechaFinSancion()) + ")");
        }
        else{
            System.out.println("El libro no se encuentra o ya se ha devuelto, así que el libro no se puede devolver");
        }
    }
    public void consultarEstadoUsuario(){
        System.out.println("Introduce el número de socio de un usuario: ");
        String numerosocio=in.nextLine().trim();
        Usuario usuario = gestor.buscarUsuario(numerosocio);
        System.out.println(usuario.toString());
    }
    public void mostrarPrestamosActivos(){
        Prestamo[] prestamos = gestor.getPrestamos();
        boolean activo = false;
        for(int i = 0; i<gestor.getNumeroPrestamos(); i++){
            if(prestamos[i].getFechaDevolucionReal()==null){
                System.out.println(prestamos[i].toString());
                activo = true;
            }
        }
        if (activo == false){
            System.out.println("No hay ningún préstamo activo");
        }
    }
    public void mostrarUsuariosSancionados(){
        Usuario[] usuarios = gestor.getUsuarios();
        boolean sancionado = false;
        for(int i=0;i<gestor.getNumeroUsuarios(); i++){
            if(usuarios[i].estaSancionado()){
                System.out.println(usuarios[i].toString());
                sancionado = true;
            }
        }
        if(sancionado == false){
            System.out.println("No hay ningún usuario sancionado");
        }
    }
    public void actualizarSanciones(){
        Usuario[] usuarios = gestor.getUsuarios();
        boolean actualizar = false;
        for(int i=0; i<gestor.getNumeroUsuarios(); i++){
            if(usuarios[i].getFechaFinSancion().isBefore(LocalDate.now())){
                usuarios[i].levantarSancion();
                actualizar = true;
            }
        }
        if(actualizar){
            System.out.println("Sanciones actualizadas correctamente");
        }
        else{
            System.out.println("No hay sanciones que actualizar");
        }
    }

}
