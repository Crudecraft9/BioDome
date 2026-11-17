# Documentación de BioDome (BDL)

Aquí está **toda la especificación del lenguaje**. Regla del equipo: *primero se documenta aquí, después se programa*.

## Índice

| # | Documento | Contenido | Estado |
|---|---|---|---|
| 01 | [Descripción del lenguaje](01-descripcion-del-lenguaje.md) | Qué es BDL, problema que resuelve, público, ejemplo | ✅ |
| 02 | [Palabras reservadas](02-palabras-reservadas.md) | Las 23 palabras reservadas y su significado | ✅ |
| 03 | [Tabla de tokens](03-tabla-de-tokens.md) | Todas las clases de token (lexema, patrón, ejemplo, acción) | 🟡 parcial |
| 04 | [Alfabeto y expresiones regulares](04-expresiones-regulares.md) | Σ, definiciones regulares y ER de cada token | ⏳ |
| 05 | [Autómatas finitos](05-automatas.md) | AFD por clase de token + AFD combinado + tablas de transición | 🟡 parcial |
| 06 | [Errores léxicos](06-errores-lexicos.md) | Catálogo de errores con código y mensaje | ⏳ |
| 07 | [Arquitectura del compilador](07-arquitectura.md) | Backend, frontend, API, flujo de datos | ✅ |
| 08 | [Decisiones y pendientes](08-decisiones.md) | Lo que el equipo ha decidido y lo que falta decidir | 🟡 |

Leyenda: ✅ completo · 🟡 parcial · ⏳ por hacer

## Carpetas

- `img/` — imágenes de los autómatas (exportadas de JFLAP, draw.io, etc.). Nómbralas `afd-<clase>.png`, por ejemplo `afd-identificadores.png`.

## Convenciones

- **Nombres de token:** `PR_*` palabras reservadas, `LIT_*` literales, `OP_*` operadores, y nombres descriptivos en mayúsculas para delimitadores (`LLAVE_A`, `PUNTO_COMA`...). El nombre debe coincidir **exactamente** con la constante en `backend/src/main/java/bdl/lexer/TipoToken.java`.
- **Estados de autómatas:** `q0` es siempre el inicial; numerar de forma global (no reiniciar en cada autómata) para poder combinarlos en el AFD principal. Estados de aceptación entre paréntesis en diagramas de texto: `(q1)`.
- **Diagramas:** se pueden escribir en [Mermaid](https://mermaid.js.org/) dentro del `.md` (GitHub y VS Code los dibujan) o como imagen en `img/`.
- **Cada cambio en la especificación** se anota en [08-decisiones.md](08-decisiones.md) con fecha.

## Documentos de la materia

- *Tema 4 – Actividad: Analizador Léxico* (requisitos del entregable): tokens a reconocer, formato de comentarios y errores morfológicos a detectar.
- *Resumen Analizador Léxico* (cuestionario: token vs lexema, AFD, manejo de errores).
