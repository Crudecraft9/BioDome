package bdl.lexer;

/** Entrada de la tabla de símbolos: un identificador y dónde aparece. */
public record Simbolo(String nombre, int primeraLinea, int apariciones) {}
