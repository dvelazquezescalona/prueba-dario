import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../municipio.test-samples';

import { MunicipioFormService } from './municipio-form.service';

describe('Municipio Form Service', () => {
  let service: MunicipioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MunicipioFormService);
  });

  describe('Service methods', () => {
    describe('createMunicipioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMunicipioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreMunicipio: expect.any(Object),
            provincia: expect.any(Object),
          }),
        );
      });

      it('passing IMunicipio should create a new form with FormGroup', () => {
        const formGroup = service.createMunicipioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreMunicipio: expect.any(Object),
            provincia: expect.any(Object),
          }),
        );
      });
    });

    describe('getMunicipio', () => {
      it('should return NewMunicipio for default Municipio initial value', () => {
        const formGroup = service.createMunicipioFormGroup(sampleWithNewData);

        const municipio = service.getMunicipio(formGroup) as any;

        expect(municipio).toMatchObject(sampleWithNewData);
      });

      it('should return NewMunicipio for empty Municipio initial value', () => {
        const formGroup = service.createMunicipioFormGroup();

        const municipio = service.getMunicipio(formGroup) as any;

        expect(municipio).toMatchObject({});
      });

      it('should return IMunicipio', () => {
        const formGroup = service.createMunicipioFormGroup(sampleWithRequiredData);

        const municipio = service.getMunicipio(formGroup) as any;

        expect(municipio).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMunicipio should not enable id FormControl', () => {
        const formGroup = service.createMunicipioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMunicipio should disable id FormControl', () => {
        const formGroup = service.createMunicipioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
