# 06 · Errores léxicos

> ⏳ **Por especificar por el equipo.** Hoy el lexer no reporta errores: lo que no reconoce lo emite como `NO_CLASIFICADO`.

## Estrategia de manejo de errores

Pautas de la materia a considerar:

- **Mensajes claros** con línea, columna y causa.
- **Recuperación:** no detenerse en el primer error; reportar todos los de una pasada.
- **Ignorar el carácter desconocido** y continuar.
- **Palabras reservadas mal escritas:** sugerir la correcta (ej. `sesnor` → *¿quisiste decir `sensor`?*).
- **Literales mal formados:** mensaje específico por tipo de literal.
- **Registro** de todos los errores en una tabla (pestaña *Errores* del IDE).

## Catálogo

| Código | Error | Ejemplo de entrada | Mensaje | ¿Cómo se recupera? |
|---|---|---|---|---|
| `LEX-001` | | | | |
| `LEX-002` | | | | |

## Dónde está en el código

- `backend/src/main/java/bdl/lexer/ErrorLexico.java` — record `(codigo, mensaje, linea, columna)`.
- `Lexer` tiene la lista `errores`; para reportar uno:
  ```java
  errores.add(new ErrorLexico("LEX-001", "Carácter no reconocido '#'", lin, col));
  ```
- El frontend ya los muestra en la pestaña **Errores** y permite hacer clic para ir a la línea.
