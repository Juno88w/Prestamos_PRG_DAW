import java.time.*;
import java.util.Scanner;
public class Menu {
    Scanner in = new Scanner(System.in);
    public void mostrarMenu() throws UsuarioInvalidoException, UsuarioSancionadoException, UsuarioRepetidoException, PrestamoInvalidoException, LibroNoDisponibleException {
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
    public LocalDate conversionFecha(String fecha){
        if(fecha.matches("\\d{2}/\\d{2}/\\d{4}")){
            String dia = fecha.substring(0,2);
            String mes=fecha.substring(3,5);
            String anio=fecha.substring(6);
            LocalDate fechaConvertida=LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), Integer.parseInt(dia));
            return fechaConvertida;
        }
        else{
            return null;
        }
    }
    public void registrarNuevoUsuario() throws UsuarioInvalidoException {
        System.out.print("Nombre: ");
        String nombre=in.nextLine().trim();
        System.out.print("Email: ");
        String email=in.nextLine().trim();
        System.out.print("Número de socio: ");
        String numSocio=in.nextLine().trim();
        System.out.print("Fecha registro (dd/mm/aaaa): ");
        String fechaRegistro=in.nextLine().trim();
        LocalDate fecha = this.conversionFecha(fechaRegistro);
        Usuario usuario=new Usuario(nombre, email, numSocio, fecha);
    }
}
