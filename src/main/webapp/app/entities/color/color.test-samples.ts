import { IColor, NewColor } from './color.model';

export const sampleWithRequiredData: IColor = {
  id: 21900,
  nombreColor: 'register so atop',
};

export const sampleWithPartialData: IColor = {
  id: 9793,
  nombreColor: 'notwithstanding whenever gosh',
};

export const sampleWithFullData: IColor = {
  id: 27139,
  nombreColor: 'insomnia flagellate',
};

export const sampleWithNewData: NewColor = {
  nombreColor: 'brutalize present heavily',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
