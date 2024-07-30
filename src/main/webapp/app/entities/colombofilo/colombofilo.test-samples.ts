import { IColombofilo, NewColombofilo } from './colombofilo.model';

export const sampleWithRequiredData: IColombofilo = {
  id: 1611,
  nombre: 'geez',
  primerApellido: 'how',
  segindoApellido: 'now lest',
  ci: 'although till snaggle',
  latitud: 23878.61,
  longitud: 20923.08,
  direccion: 'serene heavily following',
  categoria: 'given',
};

export const sampleWithPartialData: IColombofilo = {
  id: 31804,
  nombre: 'shop sans actualise',
  primerApellido: 'athwart chairperson',
  segindoApellido: 'usually nest knowledgeably',
  ci: 'of',
  latitud: 6030.65,
  longitud: 26003.11,
  direccion: 'dimly tomorrow brr',
  categoria: 'opposite',
  telefono: 'instructive',
};

export const sampleWithFullData: IColombofilo = {
  id: 23799,
  nombre: 'or offensively',
  primerApellido: 'meaningfully gosh whoever',
  segindoApellido: 'upgrade so',
  ci: 'and brand',
  latitud: 11684.74,
  longitud: 9270.04,
  direccion: 'brr why',
  categoria: 'attractive dory',
  telefono: 'lime',
  zona: 'vaguely whenever',
};

export const sampleWithNewData: NewColombofilo = {
  nombre: 'alongside doctor',
  primerApellido: 'ah',
  segindoApellido: 'bah agonizing inside',
  ci: 'happily memorialize',
  latitud: 6970.32,
  longitud: 16789.7,
  direccion: 'now usefully misguided',
  categoria: 'tavern',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
