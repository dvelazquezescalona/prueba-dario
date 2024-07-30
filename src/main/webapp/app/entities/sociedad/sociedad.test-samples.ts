import { ISociedad, NewSociedad } from './sociedad.model';

export const sampleWithRequiredData: ISociedad = {
  id: 23714,
  nombreSociedad: 'pool win geez',
  latitud: 5476.96,
  longitud: 4556.74,
};

export const sampleWithPartialData: ISociedad = {
  id: 6269,
  nombreSociedad: 'obediently stymie barrage',
  latitud: 1854.59,
  longitud: 28383.02,
};

export const sampleWithFullData: ISociedad = {
  id: 7728,
  nombreSociedad: 'uh-huh provided hm',
  latitud: 12986.29,
  longitud: 9636.2,
};

export const sampleWithNewData: NewSociedad = {
  nombreSociedad: 'bestir negative dream',
  latitud: 23888.14,
  longitud: 7049.33,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
