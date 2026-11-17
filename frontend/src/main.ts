import './style.css';
import { analizarLexico, obtenerReservadas, type ResultadoLexico } from './api';

const EJEMPLO = `dome Tropical01 {

    sensor temperature temp @A1;
    sensor humidity humedad @A2;
    actuator ventilation ventilador @B2;

    every 10min {
        if (temp > 30C and humedad >= 80.5%) {
            activate ventilador -> 5min;
            alert "Temperatura elevada";
        } else {
            deactivate ventilador;
        }
    }
}
`;

const $ = <T extends HTMLElement>(sel: string) => document.querySelector(sel) as T;

const codigo = $<HTMLTextAreaElement>('#codigo');
const lineas = $<HTMLPreElement>('#lineas');
const estado = $<HTMLSpanElement>('#estado');

// ── Editor: números de línea ──────────────────────────────────────────
function actualizarLineas() {
  const n = codigo.value.split('\n').length;
  lineas.textContent = Array.from({ length: n }, (_, i) => i + 1).join('\n');
}
codigo.addEventListener('input', actualizarLineas);
codigo.addEventListener('scroll', () => (lineas.scrollTop = codigo.scrollTop));
codigo.addEventListener('keydown', (e) => {
  if (e.key === 'Tab') {
    e.preventDefault();
    codigo.setRangeText('    ', codigo.selectionStart, codigo.selectionEnd, 'end');
    actualizarLineas();
  }
  if (e.key === 'Enter' && e.ctrlKey) analizar();
});

function irALinea(linea: number, columna: number) {
  const filas = codigo.value.split('\n');
  let offset = 0;
  for (let i = 0; i < linea - 1 && i < filas.length; i++) offset += filas[i].length + 1;
  offset += columna - 1;
  codigo.focus();
  codigo.setSelectionRange(offset, offset);
}

// ── Pestañas ──────────────────────────────────────────────────────────
document.querySelectorAll<HTMLButtonElement>('.pestanas button').forEach((btn) => {
  btn.addEventListener('click', () => {
    document.querySelectorAll('.pestanas button, .panel').forEach((el) => el.classList.remove('activa'));
    btn.classList.add('activa');
    $(`#tab-${btn.dataset.tab}`).classList.add('activa');
  });
});

// ── Tablas ────────────────────────────────────────────────────────────
function celda(texto: string | number, clase = ''): HTMLTableCellElement {
  const td = document.createElement('td');
  td.textContent = String(texto);
  if (clase) td.className = clase;
  return td;
}

function llenar(tabId: string, filas: HTMLTableRowElement[]) {
  $(`#tab-${tabId} tbody`).replaceChildren(...filas);
}

function fila(celdas: HTMLTableCellElement[], alClic?: () => void): HTMLTableRowElement {
  const tr = document.createElement('tr');
  tr.append(...celdas);
  if (alClic) {
    tr.classList.add('clic');
    tr.addEventListener('click', alClic);
  }
  return tr;
}

function mostrar(r: ResultadoLexico) {
  const tokens = r.tokens.filter((t) => t.tipo !== 'EOF');
  llenar(
    'tokens',
    tokens.map((t, i) =>
      fila(
        [
          celda(i + 1),
          celda(t.lexema, 'mono'),
          celda(t.tipo, 'mono'),
          celda(t.categoria, `cat ${t.categoria}`),
          celda(t.linea),
          celda(t.columna),
        ],
        () => irALinea(t.linea, t.columna),
      ),
    ),
  );
  llenar(
    'simbolos',
    r.simbolos.map((s) => fila([celda(s.nombre, 'mono'), celda(s.primeraLinea), celda(s.apariciones)])),
  );
  llenar(
    'errores',
    r.errores.map((e) =>
      fila([celda(e.codigo), celda(e.mensaje), celda(e.linea), celda(e.columna)], () => irALinea(e.linea, e.columna)),
    ),
  );
  $('#n-tokens').textContent = String(tokens.length);
  $('#n-simbolos').textContent = String(r.simbolos.length);
  $('#n-errores').textContent = String(r.errores.length);

  const pendientes = tokens.filter((t) => t.categoria === 'NO_CLASIFICADO').length;
  estado.textContent =
    `${tokens.length} tokens · ${r.errores.length} errores` +
    (pendientes ? ` · ${pendientes} sin clasificar (pendiente de especificar)` : '');
  estado.className = r.errores.length ? 'error' : 'ok';
}

async function analizar() {
  estado.textContent = 'Analizando…';
  estado.className = '';
  try {
    mostrar(await analizarLexico(codigo.value));
  } catch (err) {
    estado.textContent = `No se pudo conectar con el backend (${(err as Error).message}). ¿Está corriendo en :7070?`;
    estado.className = 'error';
  }
}

$('#btn-analizar').addEventListener('click', analizar);

// ── Inicio ────────────────────────────────────────────────────────────
codigo.value = EJEMPLO;
actualizarLineas();
obtenerReservadas()
  .then((lista) =>
    llenar(
      'reservadas',
      lista.map((p) => fila([celda(p.palabra, 'mono'), celda(p.token, 'mono'), celda(p.descripcion)])),
    ),
  )
  .catch(() => {});
analizar();
