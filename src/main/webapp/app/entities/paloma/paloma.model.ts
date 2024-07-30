import { IPremio } from 'app/entities/premio/premio.model';
import { IColor } from 'app/entities/color/color.model';
import { IColombofilo } from 'app/entities/colombofilo/colombofilo.model';

export interface IPaloma {
  id: number;
  anilla?: string | null;
  anno?: string | null;
  pais?: string | null;
  sexo?: boolean | null;
  activo?: boolean | null;
  premios?: IPremio[] | null;
  color?: IColor | null;
  colombofilo?: IColombofilo | null;
}

export type NewPaloma = Omit<IPaloma, 'id'> & { id: null };
