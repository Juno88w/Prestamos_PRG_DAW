import java.time.*;
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
                    System.out.println();
                    System.out.println("Usuario correctamente registrado");
                    break;
                case 2:
                    this.realizarPrestamo();
                    LocalDate fechaDevolucionPrevista = gestor.getFechaDevolucionPrevista(gestor.getPrestamos());
                    System.out.println("Préstamo realizado");
                    System.out.println("Devolución prevista: "  );
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
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
    }
    public void realizarPrestamo() throws FechaInvalidaException, PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException {
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
        gestor.realizarPrestamo(codigoLibro, titulo, fecha, usuario);
    }
}
