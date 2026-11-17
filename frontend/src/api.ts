// Tipos que reflejan los records de Java (bdl.lexer.*) y llamadas al backend.

export type Categoria = 'PALABRA_RESERVADA' | 'IDENTIFICADOR' | 'NO_CLASIFICADO' | 'FIN';

export interface Token {
  tipo: string;
  categoria: Categoria;
  lexema: string;
  linea: number;
  columna: number;
}

export interface ErrorLexico {
  codigo: string;
  mensaje: string;
  linea: number;
  columna: number;
}

export interface Simbolo {
  nombre: string;
  primeraLinea: number;
  apariciones: number;
}

export interface ResultadoLexico {
  tokens: Token[];
  errores: ErrorLexico[];
  simbolos: Simbolo[];
}

export interface PalabraReservada {
  palabra: string;
  token: string;
  descripcion: string;
}

async function pedir<T>(url: string, init?: RequestInit): Promise<T> {
  const res = await fetch(url, init);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json() as Promise<T>;
}

export function analizarLexico(codigo: string): Promise<ResultadoLexico> {
  return pedir('/api/lex', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ codigo }),
  });
}

export function obtenerReservadas(): Promise<PalabraReservada[]> {
  return pedir('/api/reservadas');
}
