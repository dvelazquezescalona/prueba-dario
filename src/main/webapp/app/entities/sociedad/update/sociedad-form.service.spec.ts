import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sociedad.test-samples';

import { SociedadFormService } from './sociedad-form.service';

describe('Sociedad Form Service', () => {
  let service: SociedadFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SociedadFormService);
  });

  describe('Service methods', () => {
    describe('createSociedadFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSociedadFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreSociedad: expect.any(Object),
            latitud: expect.any(Object),
            longitud: expect.any(Object),
            municipio: expect.any(Object),
          }),
        );
      });

      it('passing ISociedad should create a new form with FormGroup', () => {
        const formGroup = service.createSociedadFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreSociedad: expect.any(Object),
            latitud: expect.any(Object),
            longitud: expect.any(Object),
            municipio: expect.any(Object),
          }),
        );
      });
    });

    describe('getSociedad', () => {
      it('should return NewSociedad for default Sociedad initial value', () => {
        const formGroup = service.createSociedadFormGroup(sampleWithNewData);

        const sociedad = service.getSociedad(formGroup) as any;

        expect(sociedad).toMatchObject(sampleWithNewData);
      });

      it('should return NewSociedad for empty Sociedad initial value', () => {
        const formGroup = service.createSociedadFormGroup();

        const sociedad = service.getSociedad(formGroup) as any;

        expect(sociedad).toMatchObject({});
      });

      it('should return ISociedad', () => {
        const formGroup = service.createSociedadFormGroup(sampleWithRequiredData);

        const sociedad = service.getSociedad(formGroup) as any;

        expect(sociedad).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISociedad should not enable id FormControl', () => {
        const formGroup = service.createSociedadFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSociedad should disable id FormControl', () => {
        const formGroup = service.createSociedadFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
