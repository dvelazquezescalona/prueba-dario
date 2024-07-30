import { IColombofilo } from 'app/entities/colombofilo/colombofilo.model';
import { IParadero } from 'app/entities/paradero/paradero.model';
import { IMunicipio } from 'app/entities/municipio/municipio.model';

export interface ISociedad {
  id: number;
  nombreSociedad?: string | null;
  latitud?: number | null;
  longitud?: number | null;
  colombofilos?: IColombofilo[] | null;
  paraderos?: IParadero[] | null;
  municipio?: IMunicipio | null;
}

export type NewSociedad = Omit<ISociedad, 'id'> & { id: null };
