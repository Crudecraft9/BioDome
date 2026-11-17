# 08 · Decisiones y pendientes

Registro de lo que el equipo decide sobre el lenguaje. Agregar una línea **con fecha** cada vez que algo cambie.

## Decidido

| Fecha | Decisión |
|---|---|
| 2026-11-16 | Tema del lenguaje: DSL para ecosistemas artificiales (BioDome, `.bdl`). |
| 2026-11-16 | 23 palabras reservadas (ver [02](02-palabras-reservadas.md)). |
| 2026-11-16 | Stack: backend Java 21 + Maven + Javalin; frontend Vite + TypeScript. |
| 2026-11-16 | Palabras reservadas sensibles a mayúsculas *(provisional, configurable)*. |

## Pendiente de decidir

- [ ] Alfabeto Σ.
- [ ] Patrón definitivo de identificadores (¿se permite `_` al inicio? ¿longitud máxima?).
- [ ] Literales numéricos: ¿enteros y reales? ¿negativos?
- [ ] Literales con unidad: temperatura (`30C`?), humedad (`40%`?), tiempo (`10min`, `5s`?).
- [ ] Ubicación de dispositivos (`@A1`?).
- [ ] Cadenas de texto: delimitador y secuencias de escape.
- [ ] Operadores relacionales, aritméticos, de asignación y el operador de duración (`->`?).
- [ ] Delimitadores (`{ } ( ) ; ,`?).
- [ ] Formato de comentarios (`//`? `/* */`?).
- [ ] Catálogo de errores léxicos y sus códigos.
- [ ] ¿Las palabras reservadas distinguen mayúsculas? (hoy: sí).
