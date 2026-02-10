class UsuarioInvalidoException extends Exception {
    public UsuarioInvalidoException(String mensaje){
        super(mensaje);
    }
}
class UsuarioSancionadoException extends Exception{
    public UsuarioSancionadoException(String mensaje){
        super(mensaje);
    }
}
class UsuarioRepetidoException extends Exception{
    public UsuarioRepetidoException(String mensaje){
        super(mensaje);
    }
}
class PrestamoInvalidoException extends Exception{
    public PrestamoInvalidoException(String mensaje){
        super(mensaje);
    }
}
class LibroNoDisponibleException extends Exception{
    public LibroNoDisponibleException(String mensaje){
        super(mensaje);
    }
}

