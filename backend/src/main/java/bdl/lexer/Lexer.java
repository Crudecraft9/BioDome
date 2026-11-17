package bdl.lexer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Analizador léxico de BioDome (BDL) — VERSIÓN BASE.
 *
 * Qué hace ya:
 *   - Recorre el fuente carácter por carácter llevando línea y columna.
 *   - Ignora espacios, tabuladores y saltos de línea.
 *   - Reconoce palabras (letra o '_' seguida de letras/dígitos/'_') y las
 *     clasifica como PALABRA RESERVADA o ID consultando la tabla.
 *   - Llena la tabla de símbolos con los ID.
 *
 * Qué falta (cuando el equipo lo especifique):
 *   - Literales (números, unidades, ubicaciones, texto), operadores,
 *     delimitadores, comentarios y el catálogo de errores léxicos.
 *   Mientras tanto, cada carácter no reconocido se emite como NO_CLASIFICADO.
 *   Para agregar una clase nueva: añadir un "else if" en {@link #siguienteToken()}
 *   y un método leerXxx() que implemente su autómata.
 */
public final class Lexer {

    private final String fuente;
    private int pos = 0;
    private int linea = 1;
    private int columna = 1;

    private final List<Token> tokens = new ArrayList<>();
    private final List<ErrorLexico> errores = new ArrayList<>();
    private final Map<String, int[]> simbolos = new LinkedHashMap<>(); // nombre -> {primeraLinea, apariciones}

    public Lexer(String fuente) {
        this.fuente = fuente == null ? "" : fuente;
    }

    /** Punto de entrada: analiza todo el fuente. */
    public ResultadoLexico analizar() {
        while (true) {
            saltarEspacios();
            if (fin()) break;
            tokens.add(siguienteToken());
        }
        tokens.add(new Token(TipoToken.EOF, "", linea, columna));

        List<Simbolo> tabla = new ArrayList<>();
        simbolos.forEach((nombre, d) -> tabla.add(new Simbolo(nombre, d[0], d[1])));
        return new ResultadoLexico(List.copyOf(tokens), List.copyOf(errores), tabla);
    }

    // ── Despacho según el primer carácter ───────────────────────────────

    private Token siguienteToken() {
        char c = actual();

        if (esInicioPalabra(c)) {
            return leerPalabra();
        }
        // TODO: digitos  -> literales numéricos / con unidad
        // TODO: '@'      -> ubicación
        // TODO: '"'      -> texto
        // TODO: operadores, delimitadores y comentarios

        int lin = linea, col = columna;
        return new Token(TipoToken.NO_CLASIFICADO, String.valueOf(avanzar()), lin, col);
    }

    // ── Palabras: reservadas o identificadores ──────────────────────────

    private Token leerPalabra() {
        int lin = linea, col = columna;
        StringBuilder sb = new StringBuilder();
        sb.append(avanzar());
        while (!fin() && esParteDePalabra(actual())) {
            sb.append(avanzar());
        }
        String lexema = sb.toString();

        TipoToken reservada = PalabrasReservadas.buscar(lexema);
        if (reservada != null) {
            return new Token(reservada, lexema, lin, col);
        }
        registrarSimbolo(lexema, lin);
        return new Token(TipoToken.ID, lexema, lin, col);
    }

    private void registrarSimbolo(String nombre, int lin) {
        simbolos.computeIfAbsent(nombre, k -> new int[]{lin, 0})[1]++;
    }

    // ── Utilidades de recorrido ─────────────────────────────────────────

    private void saltarEspacios() {
        while (!fin() && (actual() == ' ' || actual() == '\t' || actual() == '\r' || actual() == '\n')) {
            avanzar();
        }
    }

    private static boolean esInicioPalabra(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    private static boolean esParteDePalabra(char c) {
        return esInicioPalabra(c) || (c >= '0' && c <= '9');
    }

    private boolean fin() { return pos >= fuente.length(); }

    private char actual() { return fuente.charAt(pos); }

    private char avanzar() {
        char c = fuente.charAt(pos++);
        if (c == '\n') {
            linea++;
            columna = 1;
        } else {
            columna++;
        }
        return c;
    }
}
