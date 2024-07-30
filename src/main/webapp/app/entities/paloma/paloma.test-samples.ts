import { IPaloma, NewPaloma } from './paloma.model';

export const sampleWithRequiredData: IPaloma = {
  id: 26978,
  anilla: 'fawn altruistic valet',
  anno: 'anti parch',
  pais: 'hype',
  sexo: false,
};

export const sampleWithPartialData: IPaloma = {
  id: 30476,
  anilla: 'honeydew drama',
  anno: 'duh but considering',
  pais: 'provided fatally stint',
  sexo: true,
};

export const sampleWithFullData: IPaloma = {
  id: 28116,
  anilla: 'conceptualise',
  anno: 'brr',
  pais: 'millimeter zowie',
  sexo: false,
  activo: true,
};

export const sampleWithNewData: NewPaloma = {
  anilla: 'afore',
  anno: 'yahoo',
  pais: 'translate recall exemplary',
  sexo: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
