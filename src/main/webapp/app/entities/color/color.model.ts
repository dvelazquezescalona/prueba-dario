import { IPaloma } from 'app/entities/paloma/paloma.model';

export interface IColor {
  id: number;
  nombreColor?: string | null;
  palomas?: IPaloma[] | null;
}

export type NewColor = Omit<IColor, 'id'> & { id: null };
