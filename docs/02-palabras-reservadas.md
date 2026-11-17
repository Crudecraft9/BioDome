# 02 · Palabras reservadas

BDL tiene **23 palabras reservadas**.

## Reglas

- Se reconocen primero como palabra (letra o `_` seguida de letras, dígitos o `_`) y luego se buscan en la tabla de reservadas.
- **Coincidencia exacta y sensible a mayúsculas:** `sensor` es reservada, `Sensor` y `SENSOR` son identificadores.
  *(Configurable en `PalabrasReservadas.SENSIBLE_A_MAYUSCULAS`.)*
- **Coincidencia más larga:** `sensorAgua` es un identificador, no `sensor` + `Agua`.
- Una palabra reservada **no** puede usarse como nombre de sensor, actuador o domo.

## Tabla

### Estructura

| Token | Lexema | Descripción |
|---|---|---|
| `PR_DOME` | `dome` | Declara un ecosistema artificial. |
| `PR_SENSOR` | `sensor` | Declara un sensor. |
| `PR_ACTUATOR` | `actuator` | Declara un actuador. |

### Tipos de sensor

| Token | Lexema | Descripción |
|---|---|---|
| `PR_TEMPERATURE` | `temperature` | Tipo de sensor de temperatura. |
| `PR_HUMIDITY` | `humidity` | Tipo de sensor de humedad ambiental. |
| `PR_SOIL` | `soil` | Tipo de sensor de humedad del suelo. |
| `PR_LIGHT` | `light` | Tipo de sensor de iluminación. |
| `PR_CO2` | `co2` | Tipo de sensor de CO₂. |

### Tipos de actuador

| Token | Lexema | Descripción |
|---|---|---|
| `PR_IRRIGATION` | `irrigation` | Tipo de actuador de riego. |
| `PR_VENTILATION` | `ventilation` | Tipo de actuador de ventilación. |
| `PR_LIGHTING` | `lighting` | Tipo de actuador de iluminación. |

### Acciones

| Token | Lexema | Descripción |
|---|---|---|
| `PR_ACTIVATE` | `activate` | Enciende un actuador. |
| `PR_DEACTIVATE` | `deactivate` | Apaga un actuador. |
| `PR_ALERT` | `alert` | Genera una alerta. |
| `PR_LOG` | `log` | Registra un evento. |

### Control

| Token | Lexema | Descripción |
|---|---|---|
| `PR_EVERY` | `every` | Define un intervalo de monitoreo. |
| `PR_IF` | `if` | Inicia una condición. |
| `PR_ELSE` | `else` | Rama alternativa. |

### Lógicos y booleanos

| Token | Lexema | Descripción |
|---|---|---|
| `PR_AND` | `and` | Conjunción lógica. |
| `PR_OR` | `or` | Disyunción lógica. |
| `PR_NOT` | `not` | Negación lógica. |
| `PR_TRUE` | `true` | Literal booleano verdadero. |
| `PR_FALSE` | `false` | Literal booleano falso. |

## Ejemplos

| Entrada | Token(s) |
|---|---|
| `dome` | `PR_DOME` |
| `Dome` | `ID` |
| `sensorAgua` | `ID` |
| `co2` | `PR_CO2` |
| `co2Interior` | `ID` |
| `sensor temperature temp` | `PR_SENSOR` `PR_TEMPERATURE` `ID` |

## Dónde está en el código

- `backend/src/main/java/bdl/lexer/TipoToken.java` — una constante `PR_*` por palabra, con su lexema y descripción.
- `backend/src/main/java/bdl/lexer/PalabrasReservadas.java` — tabla de búsqueda generada a partir del enum.

Para **agregar** una palabra reservada basta con añadir una constante en `TipoToken` (ej. `PR_WHILE("while", "...")`), actualizar esta tabla y cambiar el número esperado en la prueba `hayVeintitresPalabrasReservadas`.
