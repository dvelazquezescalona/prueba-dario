import { IVuelo } from 'app/entities/vuelo/vuelo.model';
import { ISociedad } from 'app/entities/sociedad/sociedad.model';

export interface IParadero {
  id: number;
  nombreParadero?: string | null;
  distanciaMedia?: number | null;
  latitud?: number | null;
  longitud?: number | null;
  vuelos?: IVuelo[] | null;
  sociedad?: ISociedad | null;
}

export type NewParadero = Omit<IParadero, 'id'> & { id: null };
