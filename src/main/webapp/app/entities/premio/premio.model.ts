import dayjs from 'dayjs/esm';
import { IPaloma } from 'app/entities/paloma/paloma.model';
import { IVuelo } from 'app/entities/vuelo/vuelo.model';

export interface IPremio {
  id: number;
  designada?: boolean | null;
  fechaArribo?: dayjs.Dayjs | null;
  tiempoVuelo?: number | null;
  velocidad?: number | null;
  lugar?: number | null;
  puntos?: number | null;
  paloma?: IPaloma | null;
  vuelo?: IVuelo | null;
}

export type NewPremio = Omit<IPremio, 'id'> & { id: null };
