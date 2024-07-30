import dayjs from 'dayjs/esm';

import { IPremio, NewPremio } from './premio.model';

export const sampleWithRequiredData: IPremio = {
  id: 32293,
  designada: false,
  fechaArribo: dayjs('2024-03-11T14:03'),
};

export const sampleWithPartialData: IPremio = {
  id: 12894,
  designada: false,
  fechaArribo: dayjs('2024-03-11T08:19'),
  tiempoVuelo: 29384,
  velocidad: 27821.1,
  puntos: 22247.38,
};

export const sampleWithFullData: IPremio = {
  id: 29530,
  designada: false,
  fechaArribo: dayjs('2024-03-10T22:28'),
  tiempoVuelo: 23827,
  velocidad: 22228.94,
  lugar: 2145,
  puntos: 6568.65,
};

export const sampleWithNewData: NewPremio = {
  designada: true,
  fechaArribo: dayjs('2024-03-10T21:53'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
