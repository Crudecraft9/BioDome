# 04 · Alfabeto y expresiones regulares

> ⏳ **Por especificar por el equipo.** Esta página es la plantilla donde se escribirán.

## Alfabeto Σ

Lista de todos los caracteres válidos en un programa BDL. Cualquier carácter fuera de Σ es un error léxico.

```
Σ = { ... }
```

## Definiciones regulares auxiliares

Nombres cortos para reutilizar en las ER. Ejemplo de formato:

```
letra    → [A-Za-z_]
digito   → [0-9]
...
```

## Expresión regular por token

| Token | Expresión regular | Lexemas válidos | Lexemas inválidos |
|---|---|---|---|
| `ID` | | | |
| `PR_*` | *Mismo patrón que ID + búsqueda en tabla* | `dome`, `if` | — |
| | | | |

## Lenguaje léxico completo

El lenguaje léxico de BDL es la unión de los lenguajes de cada clase:

```
L(BDL) = L(ID) ∪ L(...) ∪ ...
```

aplicando la regla de la **coincidencia más larga** y, en caso de empate, la prioridad de la tabla (palabra reservada sobre ID).

## Cómo probar una ER

- https://regex101.com (seleccionar *Java 8* como flavor).
- En Java: `"30C".matches("[0-9]+C")`.
- Agregar los lexemas válidos e inválidos de la tabla como casos en `LexerTest.java`.
