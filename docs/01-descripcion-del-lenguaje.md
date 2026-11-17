# 01 · Descripción del lenguaje

## Nombre

**BioDome Language (BDL)** — extensión de archivo `.bdl`.

## Área de aplicación

Lenguaje de dominio específico (DSL) para **describir, monitorear y automatizar ecosistemas artificiales**: domos, invernaderos cerrados y biósferas experimentales donde plantas, microorganismos u otros seres vivos dependen de condiciones ambientales controladas.

## Problema que resuelve

En estos ecosistemas hay que vigilar continuamente variables como:

- temperatura
- humedad del aire
- humedad del suelo
- concentración de CO₂
- intensidad luminosa

y actuar sobre dispositivos como **riego**, **ventilación** e **iluminación**.

Hoy esa lógica se programa en lenguajes de propósito general (C/C++ en microcontroladores, Python, scripts propietarios de cada fabricante), lo que mezcla las reglas biológicas con detalles de bajo nivel (pines, puertos, conversión de unidades, temporizadores).

## Por qué un lenguaje nuevo

- **No existe un lenguaje formal dedicado** a este nicho: cada fabricante usa configuraciones propias o código genérico.
- **Legible para no programadores:** un biólogo o agrónomo puede leer una regla como `if (temp > 30C) { activate ventilador; }` sin saber C.
- **Unidades como parte del lenguaje** *(propuesta, pendiente de especificar)*: `30C`, `40%`, `10min` como literales de primera clase, para detectar errores como comparar una temperatura con un porcentaje.
- **Superficie pequeña y verificable:** una gramática reducida facilita validar reglas antes de aplicarlas a organismos vivos.
- **Simulable:** el mismo programa puede ejecutarse en un simulador antes de conectarse a hardware real.

## Público objetivo

Investigadores de ecosistemas artificiales, estudiantes de ingeniería, especialistas en agricultura inteligente, desarrolladores de sistemas de automatización y administradores de invernaderos.

## Conceptos del lenguaje

| Concepto | Palabra(s) | Descripción |
|---|---|---|
| Domo | `dome` | Unidad principal: un ecosistema con sus dispositivos y reglas |
| Sensor | `sensor` + tipo | Dispositivo que mide: `temperature`, `humidity`, `soil`, `light`, `co2` |
| Actuador | `actuator` + tipo | Dispositivo que actúa: `irrigation`, `ventilation`, `lighting` |
| Acciones | `activate`, `deactivate`, `alert`, `log` | Encender/apagar actuadores, emitir alertas, registrar eventos |
| Monitoreo periódico | `every` | Bloque de reglas que se evalúa cada cierto intervalo |
| Condiciones | `if`, `else`, `and`, `or`, `not`, `true`, `false` | Lógica de decisión |

## Programa de ejemplo

Archivo [`ejemplos/Invernadero.bdl`](../ejemplos/Invernadero.bdl):

```
// Domo tropical con monitoreo cada 10 minutos
dome Tropical01 {

    sensor temperature temp @A1;
    sensor humidity humedad @A2;
    sensor soil humedadSuelo @A3;
    sensor light luminosidad @A4;
    sensor co2 dioxido @A5;

    actuator irrigation riego @B1;
    actuator ventilation ventilador @B2;
    actuator lighting luz @B3;

    every 10min {
        if (temp > 30C and humedad >= 80.5%) {
            activate ventilador -> 5min;
            alert "Temperatura elevada";
        } else {
            deactivate ventilador;
        }

        if (humedadSuelo < 40%) {
            activate riego -> 2min;
            log "Riego automatico iniciado";
        }
    }
}
```

> ⚠️ La sintaxis de literales (`30C`, `40%`, `10min`, `@A1`, `"texto"`), operadores (`->`, `>=`...) y comentarios (`//`) de este ejemplo es **ilustrativa**: aún no está especificada formalmente. Ver [08-decisiones.md](08-decisiones.md).

**Lectura del ejemplo:** el programa declara un domo con cinco sensores y tres actuadores; cada 10 minutos evalúa reglas que activan o desactivan dispositivos, emiten alertas y registran eventos.
