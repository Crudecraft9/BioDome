package bdl.lexer;

import java.util.List;

/** Todo lo que produce una pasada del analizador léxico (es lo que la API devuelve como JSON). */
public record ResultadoLexico(List<Token> tokens, List<ErrorLexico> errores, List<Simbolo> simbolos) {}
