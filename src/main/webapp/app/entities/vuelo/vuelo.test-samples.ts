import dayjs from 'dayjs/esm';

import { IVuelo, NewVuelo } from './vuelo.model';

export const sampleWithRequiredData: IVuelo = {
  id: 22812,
  fecha: dayjs('2024-03-11T09:51'),
  descripcion: 'psst surprisingly afterwards',
  competencia: 'upon nearly',
  campeonato: 'utterly tug',
};

export const sampleWithPartialData: IVuelo = {
  id: 14874,
  fecha: dayjs('2024-03-11T10:18'),
  descripcion: 'parched',
  competencia: 'formal gee apud',
  campeonato: 'elegantly',
};

export const sampleWithFullData: IVuelo = {
  id: 4238,
  fecha: dayjs('2024-03-11T05:26'),
  descripcion: 'sunbeam solitaire among',
  competencia: 'verbalise out which',
  campeonato: 'ouch mid gazunder',
};

export const sampleWithNewData: NewVuelo = {
  fecha: dayjs('2024-03-11T17:03'),
  descripcion: 'wonderfully yahoo',
  competencia: 'piercing qua',
  campeonato: 'shaw without',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
