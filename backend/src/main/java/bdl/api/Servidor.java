package bdl.api;

import bdl.lexer.Lexer;
import bdl.lexer.PalabrasReservadas;
import io.javalin.Javalin;

import java.util.List;
import java.util.Map;

/**
 * API HTTP del compilador BioDome.
 *
 *   POST /api/lex          body: { "codigo": "..." }  -> { tokens, errores, simbolos }
 *   GET  /api/reservadas   -> lista de palabras reservadas con su descripción
 *
 * Ejecutar:  mvn compile exec:java   (escucha en http://localhost:7070)
 */
public final class Servidor {

    public static final int PUERTO = 7070;

    record PeticionCodigo(String codigo) {}

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        app.post("/api/lex", ctx -> {
            PeticionCodigo req = ctx.bodyAsClass(PeticionCodigo.class);
            ctx.json(new Lexer(req.codigo()).analizar());
        });

        app.get("/api/reservadas", ctx -> {
            List<Map<String, String>> lista = PalabrasReservadas.todas().values().stream()
                    .map(t -> Map.of(
                            "palabra", t.palabra(),
                            "token", t.name(),
                            "descripcion", t.descripcion()))
                    .toList();
            ctx.json(lista);
        });

        app.start(PUERTO);
    }
}
