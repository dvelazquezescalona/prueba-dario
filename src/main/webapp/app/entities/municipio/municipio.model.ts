import { ISociedad } from 'app/entities/sociedad/sociedad.model';
import { IProvincia } from 'app/entities/provincia/provincia.model';

export interface IMunicipio {
  id: number;
  nombreMunicipio?: string | null;
  sociedads?: ISociedad[] | null;
  provincia?: IProvincia | null;
}

export type NewMunicipio = Omit<IMunicipio, 'id'> & { id: null };
