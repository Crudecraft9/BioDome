package bdl.lexer;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Tabla de palabras reservadas, construida a partir de {@link TipoToken}. */
public final class PalabrasReservadas {

    /**
     * Si es true, "Dome" NO es reservada (solo "dome").
     * Decisión pendiente del equipo; se deja configurable.
     */
    public static final boolean SENSIBLE_A_MAYUSCULAS = true;

    private static final Map<String, TipoToken> TABLA;

    static {
        Map<String, TipoToken> m = new LinkedHashMap<>();
        for (TipoToken t : TipoToken.values()) {
            if (t.esReservada()) m.put(t.palabra(), t);
        }
        TABLA = Collections.unmodifiableMap(m);
    }

    private PalabrasReservadas() {}

    /** Devuelve el tipo de la palabra reservada, o null si el lexema no lo es. */
    public static TipoToken buscar(String lexema) {
        return TABLA.get(SENSIBLE_A_MAYUSCULAS ? lexema : lexema.toLowerCase());
    }

    public static Map<String, TipoToken> todas() {
        return TABLA;
    }
}
