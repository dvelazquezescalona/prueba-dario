import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../colombofilo.test-samples';

import { ColombofiloFormService } from './colombofilo-form.service';

describe('Colombofilo Form Service', () => {
  let service: ColombofiloFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ColombofiloFormService);
  });

  describe('Service methods', () => {
    describe('createColombofiloFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createColombofiloFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            primerApellido: expect.any(Object),
            segindoApellido: expect.any(Object),
            ci: expect.any(Object),
            latitud: expect.any(Object),
            longitud: expect.any(Object),
            direccion: expect.any(Object),
            categoria: expect.any(Object),
            telefono: expect.any(Object),
            zona: expect.any(Object),
            sociedad: expect.any(Object),
          }),
        );
      });

      it('passing IColombofilo should create a new form with FormGroup', () => {
        const formGroup = service.createColombofiloFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            primerApellido: expect.any(Object),
            segindoApellido: expect.any(Object),
            ci: expect.any(Object),
            latitud: expect.any(Object),
            longitud: expect.any(Object),
            direccion: expect.any(Object),
            categoria: expect.any(Object),
            telefono: expect.any(Object),
            zona: expect.any(Object),
            sociedad: expect.any(Object),
          }),
        );
      });
    });

    describe('getColombofilo', () => {
      it('should return NewColombofilo for default Colombofilo initial value', () => {
        const formGroup = service.createColombofiloFormGroup(sampleWithNewData);

        const colombofilo = service.getColombofilo(formGroup) as any;

        expect(colombofilo).toMatchObject(sampleWithNewData);
      });

      it('should return NewColombofilo for empty Colombofilo initial value', () => {
        const formGroup = service.createColombofiloFormGroup();

        const colombofilo = service.getColombofilo(formGroup) as any;

        expect(colombofilo).toMatchObject({});
      });

      it('should return IColombofilo', () => {
        const formGroup = service.createColombofiloFormGroup(sampleWithRequiredData);

        const colombofilo = service.getColombofilo(formGroup) as any;

        expect(colombofilo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IColombofilo should not enable id FormControl', () => {
        const formGroup = service.createColombofiloFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewColombofilo should disable id FormControl', () => {
        const formGroup = service.createColombofiloFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
