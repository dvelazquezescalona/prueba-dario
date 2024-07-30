import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../paradero.test-samples';

import { ParaderoFormService } from './paradero-form.service';

describe('Paradero Form Service', () => {
  let service: ParaderoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParaderoFormService);
  });

  describe('Service methods', () => {
    describe('createParaderoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createParaderoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreParadero: expect.any(Object),
            distanciaMedia: expect.any(Object),
            latitud: expect.any(Object),
            longitud: expect.any(Object),
            sociedad: expect.any(Object),
          }),
        );
      });

      it('passing IParadero should create a new form with FormGroup', () => {
        const formGroup = service.createParaderoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreParadero: expect.any(Object),
            distanciaMedia: expect.any(Object),
            latitud: expect.any(Object),
            longitud: expect.any(Object),
            sociedad: expect.any(Object),
          }),
        );
      });
    });

    describe('getParadero', () => {
      it('should return NewParadero for default Paradero initial value', () => {
        const formGroup = service.createParaderoFormGroup(sampleWithNewData);

        const paradero = service.getParadero(formGroup) as any;

        expect(paradero).toMatchObject(sampleWithNewData);
      });

      it('should return NewParadero for empty Paradero initial value', () => {
        const formGroup = service.createParaderoFormGroup();

        const paradero = service.getParadero(formGroup) as any;

        expect(paradero).toMatchObject({});
      });

      it('should return IParadero', () => {
        const formGroup = service.createParaderoFormGroup(sampleWithRequiredData);

        const paradero = service.getParadero(formGroup) as any;

        expect(paradero).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IParadero should not enable id FormControl', () => {
        const formGroup = service.createParaderoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewParadero should disable id FormControl', () => {
        const formGroup = service.createParaderoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
