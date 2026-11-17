package bdl.lexer;

/**
 * Error léxico con su posición.
 * El catálogo de errores (códigos y mensajes) aún no está definido.
 */
public record ErrorLexico(String codigo, String mensaje, int linea, int columna) {}
