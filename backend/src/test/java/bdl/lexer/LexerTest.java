package bdl.lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    private static List<Token> tokens(String fuente) {
        return new Lexer(fuente).analizar().tokens();
    }

    @Test
    void reconoceTodasLasPalabrasReservadas() {
        for (var e : PalabrasReservadas.todas().entrySet()) {
            List<Token> t = tokens(e.getKey());
            assertEquals(e.getValue(), t.get(0).tipo(), "Falló: " + e.getKey());
            assertEquals(TipoToken.EOF, t.get(1).tipo());
        }
    }

    @Test
    void hayVeintitresPalabrasReservadas() {
        assertEquals(23, PalabrasReservadas.todas().size());
    }

    @Test
    void palabraMasLargaQueReservadaEsIdentificador() {
        // "sensorAgua" no debe partirse en PR_SENSOR + ID
        List<Token> t = tokens("sensorAgua");
        assertEquals(TipoToken.ID, t.get(0).tipo());
        assertEquals("sensorAgua", t.get(0).lexema());
    }

    @Test
    void sensibleAMayusculas() {
        assertEquals(TipoToken.ID, tokens("Dome").get(0).tipo());
        assertEquals(TipoToken.PR_DOME, tokens("dome").get(0).tipo());
    }

    @Test
    void declaracionDeSensor() {
        List<Token> t = tokens("sensor temperature temp");
        assertEquals(TipoToken.PR_SENSOR, t.get(0).tipo());
        assertEquals(TipoToken.PR_TEMPERATURE, t.get(1).tipo());
        assertEquals(TipoToken.ID, t.get(2).tipo());
    }

    @Test
    void llevaLineaYColumna() {
        List<Token> t = tokens("dome X {\n    sensor");
        Token sensor = t.get(3);
        assertEquals(TipoToken.PR_SENSOR, sensor.tipo());
        assertEquals(2, sensor.linea());
        assertEquals(5, sensor.columna());
    }

    @Test
    void caracteresSinEspecificarQuedanNoClasificados() {
        List<Token> t = tokens("{");
        assertEquals(TipoToken.NO_CLASIFICADO, t.get(0).tipo());
    }

    @Test
    void tablaDeSimbolosCuentaApariciones() {
        ResultadoLexico r = new Lexer("riego riego\nluz").analizar();
        assertEquals(2, r.simbolos().size());
        assertEquals(new Simbolo("riego", 1, 2), r.simbolos().get(0));
        assertEquals(new Simbolo("luz", 2, 1), r.simbolos().get(1));
    }
}
