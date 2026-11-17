package bdl.lexer;

/** Token entregado por el analizador léxico: (tipo, lexema) + posición en el fuente. */
public record Token(TipoToken tipo, String categoria, String lexema, int linea, int columna) {

    public Token(TipoToken tipo, String lexema, int linea, int columna) {
        this(tipo, tipo.categoria(), lexema, linea, columna);
    }
}
