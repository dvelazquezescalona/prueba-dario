import { IParadero, NewParadero } from './paradero.model';

export const sampleWithRequiredData: IParadero = {
  id: 13622,
  nombreParadero: 'chivvy',
  distanciaMedia: 26503,
  latitud: 17453.32,
  longitud: 13796.58,
};

export const sampleWithPartialData: IParadero = {
  id: 9740,
  nombreParadero: 'mouse journey',
  distanciaMedia: 22748,
  latitud: 13556.71,
  longitud: 701.98,
};

export const sampleWithFullData: IParadero = {
  id: 31560,
  nombreParadero: 'curtain',
  distanciaMedia: 18038,
  latitud: 14350.75,
  longitud: 15367.56,
};

export const sampleWithNewData: NewParadero = {
  nombreParadero: 'oof unethically aside',
  distanciaMedia: 1855,
  latitud: 10965.9,
  longitud: 32747.79,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
