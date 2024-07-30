import { IMunicipio, NewMunicipio } from './municipio.model';

export const sampleWithRequiredData: IMunicipio = {
  id: 1712,
  nombreMunicipio: 'by anenst spiffy',
};

export const sampleWithPartialData: IMunicipio = {
  id: 149,
  nombreMunicipio: 'circa',
};

export const sampleWithFullData: IMunicipio = {
  id: 13664,
  nombreMunicipio: 'rally',
};

export const sampleWithNewData: NewMunicipio = {
  nombreMunicipio: 'aboard',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
