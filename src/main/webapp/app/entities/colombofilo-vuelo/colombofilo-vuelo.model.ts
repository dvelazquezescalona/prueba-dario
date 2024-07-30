import { IColombofilo } from 'app/entities/colombofilo/colombofilo.model';
import { IVuelo } from 'app/entities/vuelo/vuelo.model';

export interface IColombofiloVuelo {
  id: number;
  envidas?: number | null;
  distancia?: number | null;
  colombofilo?: IColombofilo | null;
  vuelo?: IVuelo | null;
}

export type NewColombofiloVuelo = Omit<IColombofiloVuelo, 'id'> & { id: null };
