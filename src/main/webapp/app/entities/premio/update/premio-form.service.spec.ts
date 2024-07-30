import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../premio.test-samples';

import { PremioFormService } from './premio-form.service';

describe('Premio Form Service', () => {
  let service: PremioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PremioFormService);
  });

  describe('Service methods', () => {
    describe('createPremioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPremioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            designada: expect.any(Object),
            fechaArribo: expect.any(Object),
            tiempoVuelo: expect.any(Object),
            velocidad: expect.any(Object),
            lugar: expect.any(Object),
            puntos: expect.any(Object),
            paloma: expect.any(Object),
            vuelo: expect.any(Object),
          }),
        );
      });

      it('passing IPremio should create a new form with FormGroup', () => {
        const formGroup = service.createPremioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            designada: expect.any(Object),
            fechaArribo: expect.any(Object),
            tiempoVuelo: expect.any(Object),
            velocidad: expect.any(Object),
            lugar: expect.any(Object),
            puntos: expect.any(Object),
            paloma: expect.any(Object),
            vuelo: expect.any(Object),
          }),
        );
      });
    });

    describe('getPremio', () => {
      it('should return NewPremio for default Premio initial value', () => {
        const formGroup = service.createPremioFormGroup(sampleWithNewData);

        const premio = service.getPremio(formGroup) as any;

        expect(premio).toMatchObject(sampleWithNewData);
      });

      it('should return NewPremio for empty Premio initial value', () => {
        const formGroup = service.createPremioFormGroup();

        const premio = service.getPremio(formGroup) as any;

        expect(premio).toMatchObject({});
      });

      it('should return IPremio', () => {
        const formGroup = service.createPremioFormGroup(sampleWithRequiredData);

        const premio = service.getPremio(formGroup) as any;

        expect(premio).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPremio should not enable id FormControl', () => {
        const formGroup = service.createPremioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPremio should disable id FormControl', () => {
        const formGroup = service.createPremioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
