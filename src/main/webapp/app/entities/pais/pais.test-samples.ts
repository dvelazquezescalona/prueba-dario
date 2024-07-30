import { IPais, NewPais } from './pais.model';

export const sampleWithRequiredData: IPais = {
  id: 5566,
};

export const sampleWithPartialData: IPais = {
  id: 22255,
  codigo: 'grease',
  nombre: 'geez fibrosis',
};

export const sampleWithFullData: IPais = {
  id: 28291,
  codigo: 'partially before creaking',
  nombre: 'wherever aha',
};

export const sampleWithNewData: NewPais = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
