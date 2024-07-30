import { IProvincia, NewProvincia } from './provincia.model';

export const sampleWithRequiredData: IProvincia = {
  id: 24316,
  nombreProvincia: 'hm in',
};

export const sampleWithPartialData: IProvincia = {
  id: 8472,
  nombreProvincia: 'swiftly daring conserve',
};

export const sampleWithFullData: IProvincia = {
  id: 23447,
  nombreProvincia: 'pfft whose',
};

export const sampleWithNewData: NewProvincia = {
  nombreProvincia: 'butter which excepting',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
