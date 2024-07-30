import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../paloma.test-samples';

import { PalomaFormService } from './paloma-form.service';

describe('Paloma Form Service', () => {
  let service: PalomaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PalomaFormService);
  });

  describe('Service methods', () => {
    describe('createPalomaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPalomaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            anilla: expect.any(Object),
            anno: expect.any(Object),
            pais: expect.any(Object),
            sexo: expect.any(Object),
            activo: expect.any(Object),
            color: expect.any(Object),
            colombofilo: expect.any(Object),
          }),
        );
      });

      it('passing IPaloma should create a new form with FormGroup', () => {
        const formGroup = service.createPalomaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            anilla: expect.any(Object),
            anno: expect.any(Object),
            pais: expect.any(Object),
            sexo: expect.any(Object),
            activo: expect.any(Object),
            color: expect.any(Object),
            colombofilo: expect.any(Object),
          }),
        );
      });
    });

    describe('getPaloma', () => {
      it('should return NewPaloma for default Paloma initial value', () => {
        const formGroup = service.createPalomaFormGroup(sampleWithNewData);

        const paloma = service.getPaloma(formGroup) as any;

        expect(paloma).toMatchObject(sampleWithNewData);
      });

      it('should return NewPaloma for empty Paloma initial value', () => {
        const formGroup = service.createPalomaFormGroup();

        const paloma = service.getPaloma(formGroup) as any;

        expect(paloma).toMatchObject({});
      });

      it('should return IPaloma', () => {
        const formGroup = service.createPalomaFormGroup(sampleWithRequiredData);

        const paloma = service.getPaloma(formGroup) as any;

        expect(paloma).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPaloma should not enable id FormControl', () => {
        const formGroup = service.createPalomaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPaloma should disable id FormControl', () => {
        const formGroup = service.createPalomaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
