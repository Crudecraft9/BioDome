package bdl.lexer;

/**
 * Tipos de token de BioDome (BDL).
 *
 * Por ahora solo están definidas las PALABRAS RESERVADAS. El resto de
 * clases (literales con unidades, operadores, delimitadores, comentarios...)
 * se agregarán cuando el equipo termine de especificarlas.
 */
public enum TipoToken {

    // ── Estructura ──────────────────────────────────────────────
    PR_DOME("dome", "Declara un ecosistema artificial."),
    PR_SENSOR("sensor", "Declara un sensor."),
    PR_ACTUATOR("actuator", "Declara un actuador."),

    // ── Tipos de sensor ─────────────────────────────────────────
    PR_TEMPERATURE("temperature", "Tipo de sensor de temperatura."),
    PR_HUMIDITY("humidity", "Tipo de sensor de humedad ambiental."),
    PR_SOIL("soil", "Tipo de sensor de humedad del suelo."),
    PR_LIGHT("light", "Tipo de sensor de iluminación."),
    PR_CO2("co2", "Tipo de sensor de CO2."),

    // ── Tipos de actuador ───────────────────────────────────────
    PR_IRRIGATION("irrigation", "Tipo de actuador de riego."),
    PR_VENTILATION("ventilation", "Tipo de actuador de ventilación."),
    PR_LIGHTING("lighting", "Tipo de actuador de iluminación."),

    // ── Acciones ────────────────────────────────────────────────
    PR_ACTIVATE("activate", "Enciende un actuador."),
    PR_DEACTIVATE("deactivate", "Apaga un actuador."),
    PR_ALERT("alert", "Genera una alerta."),
    PR_LOG("log", "Registra un evento."),

    // ── Control ─────────────────────────────────────────────────
    PR_EVERY("every", "Define un intervalo de monitoreo."),
    PR_IF("if", "Inicia una condición."),
    PR_ELSE("else", "Rama alternativa."),

    // ── Lógicos y booleanos ─────────────────────────────────────
    PR_AND("and", "Conjunción lógica."),
    PR_OR("or", "Disyunción lógica."),
    PR_NOT("not", "Negación lógica."),
    PR_TRUE("true", "Literal booleano verdadero."),
    PR_FALSE("false", "Literal booleano falso."),

    // ── Otros ───────────────────────────────────────────────────
    ID(null, "Identificador definido por el usuario."),
    /** Lexema que todavía no pertenece a ninguna clase especificada. */
    NO_CLASIFICADO(null, "Pendiente de especificar (literal, operador, delimitador...)."),
    EOF(null, "Fin de archivo.");

    private final String palabra;
    private final String descripcion;

    TipoToken(String palabra, String descripcion) {
        this.palabra = palabra;
        this.descripcion = descripcion;
    }

    /** Lexema exacto de la palabra reservada, o null si no es reservada. */
    public String palabra() { return palabra; }

    public String descripcion() { return descripcion; }

    public boolean esReservada() { return palabra != null; }

    public String categoria() {
        if (esReservada()) return "PALABRA_RESERVADA";
        return switch (this) {
            case ID -> "IDENTIFICADOR";
            case EOF -> "FIN";
            default -> "NO_CLASIFICADO";
        };
    }
}
