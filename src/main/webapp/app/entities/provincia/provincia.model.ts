import { IMunicipio } from 'app/entities/municipio/municipio.model';

export interface IProvincia {
  id: number;
  nombreProvincia?: string | null;
  municipios?: IMunicipio[] | null;
}

export type NewProvincia = Omit<IProvincia, 'id'> & { id: null };
