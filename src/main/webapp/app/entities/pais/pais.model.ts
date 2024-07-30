export interface IPais {
  id: number;
  codigo?: string | null;
  nombre?: string | null;
}

export type NewPais = Omit<IPais, 'id'> & { id: null };
