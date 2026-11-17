# 03 · Tabla de tokens

Conceptos (según la materia):

- **Lexema:** secuencia concreta de caracteres del programa fuente (ej. `humedadSuelo`).
- **Patrón:** regla (expresión regular) que describe todos los lexemas posibles de una clase.
- **Token:** par ⟨nombre, atributo⟩ que el analizador léxico entrega al sintáctico.

Cada fila de esta tabla debe tener su ER en [04](04-expresiones-regulares.md) y su autómata en [05](05-automatas.md).

## Implementados

| Token | Categoría | Patrón | Ejemplo | Acción |
|---|---|---|---|---|
| `PR_*` (23) | Palabra reservada | Ver [02](02-palabras-reservadas.md) | `dome`, `if` | Se reconoce como palabra y se busca en la tabla de reservadas |
| `ID` | Identificador | *(provisional)* letra o `_`, seguida de letras, dígitos o `_` | `Tropical01`, `humedadSuelo` | Si no es reservada, se registra en la tabla de símbolos |
| `EOF` | Fin | — | — | Marca el final del archivo |
| `NO_CLASIFICADO` | Temporal | cualquier carácter no cubierto | `{`, `@`, `3` | Marcador mientras se especifica el resto. **Se eliminará** cuando todo esté definido |

## Elementos que se descartan

| Elemento | Patrón | Acción |
|---|---|---|
| Espacio en blanco | espacio, tabulador, `\r`, `\n` | Se ignora; actualiza línea y columna |
| Comentario | ⏳ *por especificar* | Se ignora |

## Por especificar

Rellenar cuando el equipo lo decida (y moverlos a "Implementados" cuando estén en el código):

### Literales

| Token | Patrón | Ejemplo | Acción |
|---|---|---|---|
| `LIT_ENTERO` | | | |
| `LIT_REAL` | | | |
| `LIT_TEMP` | | | |
| `LIT_HUMEDAD` | | | |
| `LIT_TIEMPO` | | | |
| `LIT_UBICACION` | | | |
| `LIT_TEXTO` | | | |

### Operadores

| Token | Patrón | Ejemplo | Acción |
|---|---|---|---|
| | | | |

### Delimitadores

| Token | Patrón | Ejemplo | Acción |
|---|---|---|---|
| | | | |

> Los nombres de arriba son una propuesta basada en el ejemplo del lenguaje; el equipo puede cambiarlos.

## Conflictos léxicos y su resolución

Cuando una entrada puede leerse de varias formas. Criterio general: **coincidencia más larga**; en empate, **la palabra reservada gana sobre ID**.

| Entrada | Posibles lecturas | Resolución |
|---|---|---|
| `sensor` | `ID` / `PR_SENSOR` | Misma longitud → gana la reservada: `PR_SENSOR` |
| `sensorAgua` | `PR_SENSOR` + `ID` / `ID` | Coincidencia más larga: `ID` |
| | | |

## Ejemplos de tokenización

Entrada: `sensor temperature temp`

| Lexema | Token | Línea | Columna |
|---|---|---|---|
| `sensor` | `PR_SENSOR` | 1 | 1 |
| `temperature` | `PR_TEMPERATURE` | 1 | 8 |
| `temp` | `ID` | 1 | 20 |
