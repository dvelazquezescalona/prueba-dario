import dayjs from 'dayjs/esm';
import { IColombofiloVuelo } from 'app/entities/colombofilo-vuelo/colombofilo-vuelo.model';
import { IPremio } from 'app/entities/premio/premio.model';
import { IParadero } from 'app/entities/paradero/paradero.model';

export interface IVuelo {
  id: number;
  fecha?: dayjs.Dayjs | null;
  descripcion?: string | null;
  competencia?: string | null;
  campeonato?: string | null;
  colombofiloVuelos?: IColombofiloVuelo[] | null;
  premios?: IPremio[] | null;
  paradero?: IParadero | null;
}

export type NewVuelo = Omit<IVuelo, 'id'> & { id: null };
