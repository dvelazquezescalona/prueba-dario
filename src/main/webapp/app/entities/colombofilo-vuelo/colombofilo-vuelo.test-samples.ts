import { IColombofiloVuelo, NewColombofiloVuelo } from './colombofilo-vuelo.model';

export const sampleWithRequiredData: IColombofiloVuelo = {
  id: 22448,
  envidas: 13320,
  distancia: 31386.52,
};

export const sampleWithPartialData: IColombofiloVuelo = {
  id: 21256,
  envidas: 25576,
  distancia: 9501.57,
};

export const sampleWithFullData: IColombofiloVuelo = {
  id: 6500,
  envidas: 6399,
  distancia: 12049.02,
};

export const sampleWithNewData: NewColombofiloVuelo = {
  envidas: 19367,
  distancia: 11031.59,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
