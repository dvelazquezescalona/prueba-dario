import { IPaloma } from 'app/entities/paloma/paloma.model';
import { IColombofiloVuelo } from 'app/entities/colombofilo-vuelo/colombofilo-vuelo.model';
import { ISociedad } from 'app/entities/sociedad/sociedad.model';

export interface IColombofilo {
  id: number;
  nombre?: string | null;
  primerApellido?: string | null;
  segindoApellido?: string | null;
  ci?: string | null;
  latitud?: number | null;
  longitud?: number | null;
  direccion?: string | null;
  categoria?: string | null;
  telefono?: string | null;
  zona?: string | null;
  palomas?: IPaloma[] | null;
  colombofiloVuelos?: IColombofiloVuelo[] | null;
  sociedad?: ISociedad | null;
}

export type NewColombofilo = Omit<IColombofilo, 'id'> & { id: null };
