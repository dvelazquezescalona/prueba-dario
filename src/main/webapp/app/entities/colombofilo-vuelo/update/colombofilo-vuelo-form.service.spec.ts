import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../colombofilo-vuelo.test-samples';

import { ColombofiloVueloFormService } from './colombofilo-vuelo-form.service';

describe('ColombofiloVuelo Form Service', () => {
  let service: ColombofiloVueloFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ColombofiloVueloFormService);
  });

  describe('Service methods', () => {
    describe('createColombofiloVueloFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createColombofiloVueloFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            envidas: expect.any(Object),
            distancia: expect.any(Object),
            colombofilo: expect.any(Object),
            vuelo: expect.any(Object),
          }),
        );
      });

      it('passing IColombofiloVuelo should create a new form with FormGroup', () => {
        const formGroup = service.createColombofiloVueloFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            envidas: expect.any(Object),
            distancia: expect.any(Object),
            colombofilo: expect.any(Object),
            vuelo: expect.any(Object),
          }),
        );
      });
    });

    describe('getColombofiloVuelo', () => {
      it('should return NewColombofiloVuelo for default ColombofiloVuelo initial value', () => {
        const formGroup = service.createColombofiloVueloFormGroup(sampleWithNewData);

        const colombofiloVuelo = service.getColombofiloVuelo(formGroup) as any;

        expect(colombofiloVuelo).toMatchObject(sampleWithNewData);
      });

      it('should return NewColombofiloVuelo for empty ColombofiloVuelo initial value', () => {
        const formGroup = service.createColombofiloVueloFormGroup();

        const colombofiloVuelo = service.getColombofiloVuelo(formGroup) as any;

        expect(colombofiloVuelo).toMatchObject({});
      });

      it('should return IColombofiloVuelo', () => {
        const formGroup = service.createColombofiloVueloFormGroup(sampleWithRequiredData);

        const colombofiloVuelo = service.getColombofiloVuelo(formGroup) as any;

        expect(colombofiloVuelo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IColombofiloVuelo should not enable id FormControl', () => {
        const formGroup = service.createColombofiloVueloFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewColombofiloVuelo should disable id FormControl', () => {
        const formGroup = service.createColombofiloVueloFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
