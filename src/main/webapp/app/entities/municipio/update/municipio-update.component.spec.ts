import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IProvincia } from 'app/entities/provincia/provincia.model';
import { ProvinciaService } from 'app/entities/provincia/service/provincia.service';
import { MunicipioService } from '../service/municipio.service';
import { IMunicipio } from '../municipio.model';
import { MunicipioFormService } from './municipio-form.service';

import { MunicipioUpdateComponent } from './municipio-update.component';

describe('Municipio Management Update Component', () => {
  let comp: MunicipioUpdateComponent;
  let fixture: ComponentFixture<MunicipioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let municipioFormService: MunicipioFormService;
  let municipioService: MunicipioService;
  let provinciaService: ProvinciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MunicipioUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MunicipioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MunicipioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    municipioFormService = TestBed.inject(MunicipioFormService);
    municipioService = TestBed.inject(MunicipioService);
    provinciaService = TestBed.inject(ProvinciaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Provincia query and add missing value', () => {
      const municipio: IMunicipio = { id: 456 };
      const provincia: IProvincia = { id: 8595 };
      municipio.provincia = provincia;

      const provinciaCollection: IProvincia[] = [{ id: 11844 }];
      jest.spyOn(provinciaService, 'query').mockReturnValue(of(new HttpResponse({ body: provinciaCollection })));
      const additionalProvincias = [provincia];
      const expectedCollection: IProvincia[] = [...additionalProvincias, ...provinciaCollection];
      jest.spyOn(provinciaService, 'addProvinciaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ municipio });
      comp.ngOnInit();

      expect(provinciaService.query).toHaveBeenCalled();
      expect(provinciaService.addProvinciaToCollectionIfMissing).toHaveBeenCalledWith(
        provinciaCollection,
        ...additionalProvincias.map(expect.objectContaining),
      );
      expect(comp.provinciasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const municipio: IMunicipio = { id: 456 };
      const provincia: IProvincia = { id: 26450 };
      municipio.provincia = provincia;

      activatedRoute.data = of({ municipio });
      comp.ngOnInit();

      expect(comp.provinciasSharedCollection).toContain(provincia);
      expect(comp.municipio).toEqual(municipio);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMunicipio>>();
      const municipio = { id: 123 };
      jest.spyOn(municipioFormService, 'getMunicipio').mockReturnValue(municipio);
      jest.spyOn(municipioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ municipio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: municipio }));
      saveSubject.complete();

      // THEN
      expect(municipioFormService.getMunicipio).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(municipioService.update).toHaveBeenCalledWith(expect.objectContaining(municipio));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMunicipio>>();
      const municipio = { id: 123 };
      jest.spyOn(municipioFormService, 'getMunicipio').mockReturnValue({ id: null });
      jest.spyOn(municipioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ municipio: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: municipio }));
      saveSubject.complete();

      // THEN
      expect(municipioFormService.getMunicipio).toHaveBeenCalled();
      expect(municipioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMunicipio>>();
      const municipio = { id: 123 };
      jest.spyOn(municipioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ municipio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(municipioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProvincia', () => {
      it('Should forward to provinciaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(provinciaService, 'compareProvincia');
        comp.compareProvincia(entity, entity2);
        expect(provinciaService.compareProvincia).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
