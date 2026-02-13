import java.util.Scanner;
public class App {
    public static void main(String[] args) throws PrestamoInvalidoException, UsuarioInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException, UsuarioRepetidoException {
        Menu m = new Menu();
        m.mostrarMenu();
    }
}
