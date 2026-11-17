# BioDome (BDL)

Lenguaje de dominio específico para describir, monitorear y automatizar **ecosistemas artificiales** (domos, invernaderos, biósferas): sensores (temperatura, humedad, suelo, luz, CO₂), actuadores (riego, ventilación, iluminación) y reglas que se evalúan cada cierto intervalo. Extensión de archivo: `.bdl`.

Proyecto de la materia **Lenguajes y Autómatas I**. Este repositorio contiene el compilador (backend en Java) y un IDE web (frontend en TypeScript).

> 📚 Toda la especificación del lenguaje (palabras reservadas, tokens, expresiones regulares, autómatas, errores) vive en la carpeta [`docs/`](docs/README.md).

---

## Índice

1. [Estado actual](#1-estado-actual)
2. [Estructura del proyecto](#2-estructura-del-proyecto)
3. [Requisitos](#3-requisitos)
4. [Descargar el proyecto](#4-descargar-el-proyecto)
5. [Ejecutar](#5-ejecutar)
6. [Verificar que todo funciona](#6-verificar-que-todo-funciona)
7. [Uso del IDE](#7-uso-del-ide)
8. [API del backend](#8-api-del-backend)
9. [Solución de problemas](#9-solución-de-problemas)
10. [Cómo agregar una clase de token nueva](#10-cómo-agregar-una-clase-de-token-nueva)

---

## 1. Estado actual

| Componente | Estado |
|---|---|
| Palabras reservadas (23) | ✅ definidas e implementadas |
| Identificadores + tabla de símbolos | ✅ |
| Línea / columna de cada token | ✅ |
| Literales (números, unidades, ubicaciones, texto) | ⏳ por especificar |
| Operadores y delimitadores | ⏳ por especificar |
| Comentarios | ⏳ por especificar |
| Expresiones regulares y autómatas formales | ⏳ por especificar → [`docs/`](docs/README.md) |
| Catálogo de errores léxicos | ⏳ por especificar |
| Parser, análisis semántico, simulador | ⏳ siguientes entregas |

Todo lo que aún no tiene clase de token se emite como `NO_CLASIFICADO` (se ve en **ámbar** en el IDE). No es un error, solo significa que falta especificarlo.

---

## 2. Estructura del proyecto

```
BioDome/
├── README.md                 ← este archivo
├── docs/                     ← especificación del lenguaje (ER, autómatas, tokens...)
├── ejemplos/
│   └── Invernadero.bdl       ← programa de prueba
├── backend/                  ← Java 21 + Maven + Javalin (puerto 7070)
│   ├── pom.xml
│   └── src/
│       ├── main/java/bdl/
│       │   ├── lexer/
│       │   │   ├── TipoToken.java           ← enum con todos los tokens (aquí están las palabras reservadas)
│       │   │   ├── PalabrasReservadas.java  ← tabla de búsqueda de reservadas
│       │   │   ├── Lexer.java               ← el analizador léxico
│       │   │   ├── Token.java               ← (tipo, categoría, lexema, línea, columna)
│       │   │   ├── Simbolo.java             ← entrada de la tabla de símbolos
│       │   │   ├── ErrorLexico.java         ← (código, mensaje, línea, columna)
│       │   │   └── ResultadoLexico.java     ← tokens + errores + símbolos
│       │   └── api/
│       │       └── Servidor.java            ← API HTTP
│       └── test/java/bdl/lexer/
│           └── LexerTest.java               ← pruebas JUnit
└── frontend/                 ← Vite + TypeScript (puerto 5173)
    ├── package.json
    ├── vite.config.ts        ← redirige /api → backend :7070
    ├── index.html
    └── src/
        ├── main.ts           ← lógica del IDE
        ├── api.ts            ← tipos + llamadas al backend
        └── style.css
```

---

## 3. Requisitos

| Herramienta | Versión mínima | Para qué | Descarga |
|---|---|---|---|
| **JDK** | 21 o superior | Compilar y correr el backend | https://adoptium.net (Temurin 21 LTS) |
| **Maven** | 3.9+ | Dependencias, compilar y pruebas del backend | https://maven.apache.org/download.cgi |
| **Node.js** | 18+ (recomendado 20 o 22 LTS) | Frontend (incluye `npm`) | https://nodejs.org |
| **Git** | cualquiera | Descargar el repositorio (opcional si usan ZIP) | https://git-scm.com |
| Navegador | Chrome, Edge o Firefox actual | Usar el IDE | — |

### Verificar que están instalados

Abre una terminal (PowerShell, CMD o Git Bash) y ejecuta:

```bash
java -version
```
```bash
mvn -v
```
```bash
node -v
```
```bash
npm -v
```

Debe salir algo como `java version "21..."` (o mayor), `Apache Maven 3.9...`, `v20...` / `v22...` y un número de versión de npm. Si alguno dice *"no se reconoce como comando"*, ve a [Solución de problemas](#9-solución-de-problemas).

### Instalar Maven en Windows (si no lo tienes)

1. Descarga el **Binary zip archive** de https://maven.apache.org/download.cgi.
2. Descomprímelo, por ejemplo en `C:\Program Files\Maven\apache-maven-3.9.9`.
3. Busca en Windows *"Editar las variables de entorno del sistema"* → **Variables de entorno**.
4. En **Path** agrega `C:\Program Files\Maven\apache-maven-3.9.9\bin`.
5. Verifica que exista `JAVA_HOME` apuntando a tu JDK (por ejemplo `C:\Program Files\Eclipse Adoptium\jdk-21...`). El instalador de Temurin puede crearla si marcas la opción *"Set JAVA_HOME"*.
6. **Cierra y vuelve a abrir** la terminal y prueba `mvn -v`.

---

## 4. Descargar el proyecto

### Opción A — con Git

```bash
git clone <URL-DEL-REPOSITORIO> BioDome
```
```bash
cd BioDome
```

> Sustituye `<URL-DEL-REPOSITORIO>` por la URL del repositorio del equipo (GitHub/GitLab).

### Opción B — como ZIP

1. Descarga el ZIP del repositorio (en GitHub: botón **Code → Download ZIP**) o cópialo de la carpeta compartida.
2. Descomprímelo y entra a la carpeta `BioDome`.

> ⚠️ No copien las carpetas `backend/target/`, `frontend/node_modules/` ni `frontend/dist/` entre computadoras: se generan solas y están en `.gitignore`.

---

## 5. Ejecutar

Se necesitan **dos terminales abiertas al mismo tiempo**: una para el backend y otra para el frontend.

### Terminal 1 — Backend (Java)

```bash
cd backend
```
```bash
mvn compile exec:java
```

- La **primera vez** Maven descarga las dependencias (Javalin, Jackson, JUnit); puede tardar 1–2 minutos.
- Está listo cuando aparece una línea como:
  `Javalin started in ...ms \o/` y `Listening on http://localhost:7070/`
- Deja esta terminal abierta. Para detenerlo: `Ctrl + C`.

### Terminal 2 — Frontend (TypeScript)

```bash
cd frontend
```
```bash
npm install
```
(solo la primera vez, o cuando cambie `package.json`)

```bash
npm run dev
```

- Está listo cuando aparece: `Local: http://localhost:5173/`
- Abre **http://localhost:5173** en el navegador.
- Para detenerlo: `Ctrl + C`.

> El frontend redirige todas las peticiones `/api/...` al backend (`vite.config.ts`), por eso **el backend debe estar corriendo** antes de analizar código.

### Atajo desde la app de Claude

El archivo `.claude/launch.json` define dos servidores (`backend` y `frontend`) que se pueden arrancar desde el panel de vista previa de Claude Code.

### Compilar el frontend para entregar (opcional)

```bash
cd frontend
```
```bash
npm run build
```

Revisa los tipos con TypeScript y genera la versión optimizada en `frontend/dist/`.

---

## 6. Verificar que todo funciona

Sigue estos pasos en orden. Si todos pasan, el proyecto está bien instalado.

### ✅ Paso 1 — Pruebas unitarias del lexer

```bash
cd backend
```
```bash
mvn test
```

**Resultado esperado** (al final de la salida):

```
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

Qué comprueban las 8 pruebas (`LexerTest.java`):

| Prueba | Qué verifica |
|---|---|
| `reconoceTodasLasPalabrasReservadas` | Cada una de las 23 reservadas produce su token `PR_*` |
| `hayVeintitresPalabrasReservadas` | La tabla tiene exactamente 23 entradas |
| `palabraMasLargaQueReservadaEsIdentificador` | `sensorAgua` es `ID`, no `PR_SENSOR` + `ID` |
| `sensibleAMayusculas` | `dome` → `PR_DOME`, pero `Dome` → `ID` |
| `declaracionDeSensor` | `sensor temperature temp` → `PR_SENSOR PR_TEMPERATURE ID` |
| `llevaLineaYColumna` | Línea y columna correctas después de un salto de línea |
| `caracteresSinEspecificarQuedanNoClasificados` | `{` → `NO_CLASIFICADO` |
| `tablaDeSimbolosCuentaApariciones` | La tabla de símbolos registra primera línea y número de apariciones |

### ✅ Paso 2 — API del backend

Con el backend corriendo (Terminal 1), en **otra** terminal:

**PowerShell:**
```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:7070/api/lex -ContentType 'application/json' -Body '{"codigo":"sensor temperature temp"}' | ConvertTo-Json -Depth 5
```

**Git Bash / Linux / macOS:**
```bash
curl -s -X POST http://localhost:7070/api/lex -H "Content-Type: application/json" -d '{"codigo":"sensor temperature temp"}'
```

**Resultado esperado:**

```json
{
  "tokens": [
    {"tipo":"PR_SENSOR","categoria":"PALABRA_RESERVADA","lexema":"sensor","linea":1,"columna":1},
    {"tipo":"PR_TEMPERATURE","categoria":"PALABRA_RESERVADA","lexema":"temperature","linea":1,"columna":8},
    {"tipo":"ID","categoria":"IDENTIFICADOR","lexema":"temp","linea":1,"columna":20},
    {"tipo":"EOF","categoria":"FIN","lexema":"","linea":1,"columna":24}
  ],
  "errores": [],
  "simbolos": [{"nombre":"temp","primeraLinea":1,"apariciones":1}]
}
```

Lista de palabras reservadas: abre http://localhost:7070/api/reservadas en el navegador. Debe mostrar un JSON con 23 elementos.

### ✅ Paso 3 — IDE en el navegador

Con backend y frontend corriendo, abre http://localhost:5173 y comprueba:

- [ ] Se carga el editor con un programa de ejemplo y aparece arriba un mensaje verde tipo `66 tokens · 0 errores · 36 sin clasificar`.
- [ ] En la pestaña **Tokens**: `dome` aparece como `PR_DOME` (azul), `Tropical01` como `ID` (verde) y `{` como `NO_CLASIFICADO` (ámbar).
- [ ] La pestaña **Símbolos** lista los identificadores (`Tropical01`, `temp`, `humedad`, …) con su número de apariciones.
- [ ] La pestaña **Palabras reservadas** muestra las 23 con su descripción.
- [ ] Al hacer clic en una fila de tokens, el cursor del editor salta a esa posición.
- [ ] Escribe `sensorAgua` en el editor y pulsa **Analizar** (o `Ctrl + Enter`): debe salir como `ID`.
- [ ] Copia el contenido de `ejemplos/Invernadero.bdl` al editor y analiza: no debe haber errores.
- [ ] Si detienes el backend y pulsas **Analizar**, aparece en rojo *"No se pudo conectar con el backend"* (comprueba que el manejo de errores funciona).

---

## 7. Uso del IDE

| Acción | Cómo |
|---|---|
| Analizar el código | Botón **Analizar ▶** o `Ctrl + Enter` |
| Indentar | `Tab` (inserta 4 espacios) |
| Ir a un token / error | Clic en la fila de la tabla |
| Ver tokens / símbolos / errores / reservadas | Pestañas del panel derecho |

Colores de categoría: **azul** = palabra reservada · **verde** = identificador · **ámbar** = no clasificado (pendiente de especificar).

---

## 8. API del backend

Base: `http://localhost:7070`

### `POST /api/lex`

Analiza léxicamente un programa BDL.

- **Body:** `{ "codigo": "<texto del programa>" }`
- **Respuesta:** `{ tokens: Token[], errores: ErrorLexico[], simbolos: Simbolo[] }`

| Objeto | Campos |
|---|---|
| `Token` | `tipo` (p. ej. `PR_IF`, `ID`), `categoria` (`PALABRA_RESERVADA`, `IDENTIFICADOR`, `NO_CLASIFICADO`, `FIN`), `lexema`, `linea`, `columna` |
| `ErrorLexico` | `codigo`, `mensaje`, `linea`, `columna` |
| `Simbolo` | `nombre`, `primeraLinea`, `apariciones` |

La lista de tokens siempre termina con un token `EOF`.

### `GET /api/reservadas`

Devuelve `[{ palabra, token, descripcion }]` con las 23 palabras reservadas.

---

## 9. Solución de problemas

| Problema | Causa probable | Solución |
|---|---|---|
| `'mvn' no se reconoce como un comando` | Maven no está en el `Path` | Ver [Instalar Maven en Windows](#instalar-maven-en-windows-si-no-lo-tienes) y reabrir la terminal |
| `JAVA_HOME is not defined correctly` | Falta la variable `JAVA_HOME` | Crear `JAVA_HOME` apuntando a la carpeta del JDK (sin `\bin`) |
| `release version 21 not supported` | JDK menor a 21 | Instalar JDK 21+ y actualizar `JAVA_HOME` |
| `Port 7070 is already in use` / `BindException` | Ya hay un backend corriendo | Cerrar la otra terminal o el proceso Java que usa el puerto |
| Vite usa el puerto 5174 en vez de 5173 | Ya hay otro frontend corriendo | Usar la URL que imprime Vite o cerrar el otro |
| `npm.ps1 no se puede cargar porque la ejecución de scripts está deshabilitada` | Política de PowerShell | Usar `npm.cmd install` / `npm.cmd run dev`, o ejecutar desde CMD o Git Bash |
| El IDE dice *"No se pudo conectar con el backend"* | El backend no está corriendo | Arrancar la Terminal 1 (`mvn compile exec:java`) |
| Maven falla descargando dependencias | Sin internet o proxy de la escuela | Probar con otra red; luego quedan en caché (`~/.m2`) |
| Cambié código Java y no se refleja | El servidor sigue con la versión anterior | `Ctrl + C` en la Terminal 1 y volver a `mvn compile exec:java` |

---

## 10. Cómo agregar una clase de token nueva

Cuando el equipo especifique una clase (ver [`docs/`](docs/README.md)):

1. **Documentarla primero** en `docs/` (tabla de tokens, ER y autómata).
2. **Backend** — agregar la constante en `TipoToken.java`, sin palabra reservada:
   ```java
   LIT_TEMP(null, "Temperatura con unidad °C."),
   ```
   y ajustar `categoria()` si es una categoría nueva.
3. **Backend** — en `Lexer.siguienteToken()` añadir un `else if` según el primer carácter y un método `leerXxx()` que implemente su autómata. Los `// TODO` marcan dónde va cada una.
4. **Pruebas** — agregar casos en `LexerTest.java` y correr `mvn test`.
5. **Frontend** — si es categoría nueva, añadirla al tipo `Categoria` de `src/api.ts` y darle color en `src/style.css` (`.NOMBRE_CATEGORIA { color: ... }`).

### Palabras reservadas

`dome` `sensor` `actuator` · `temperature` `humidity` `soil` `light` `co2` · `irrigation` `ventilation` `lighting` · `activate` `deactivate` `alert` `log` · `every` `if` `else` · `and` `or` `not` `true` `false`

Son sensibles a mayúsculas (`Dome` es un ID). Se puede cambiar en `PalabrasReservadas.SENSIBLE_A_MAYUSCULAS`. Detalle completo en [`docs/02-palabras-reservadas.md`](docs/02-palabras-reservadas.md).
