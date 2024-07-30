import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IMunicipio } from 'app/entities/municipio/municipio.model';
import { MunicipioService } from 'app/entities/municipio/service/municipio.service';
import { SociedadService } from '../service/sociedad.service';
import { ISociedad } from '../sociedad.model';
import { SociedadFormService } from './sociedad-form.service';

import { SociedadUpdateComponent } from './sociedad-update.component';

describe('Sociedad Management Update Component', () => {
  let comp: SociedadUpdateComponent;
  let fixture: ComponentFixture<SociedadUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sociedadFormService: SociedadFormService;
  let sociedadService: SociedadService;
  let municipioService: MunicipioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), SociedadUpdateComponent],
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
      .overrideTemplate(SociedadUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SociedadUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sociedadFormService = TestBed.inject(SociedadFormService);
    sociedadService = TestBed.inject(SociedadService);
    municipioService = TestBed.inject(MunicipioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Municipio query and add missing value', () => {
      const sociedad: ISociedad = { id: 456 };
      const municipio: IMunicipio = { id: 20850 };
      sociedad.municipio = municipio;

      const municipioCollection: IMunicipio[] = [{ id: 31937 }];
      jest.spyOn(municipioService, 'query').mockReturnValue(of(new HttpResponse({ body: municipioCollection })));
      const additionalMunicipios = [municipio];
      const expectedCollection: IMunicipio[] = [...additionalMunicipios, ...municipioCollection];
      jest.spyOn(municipioService, 'addMunicipioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sociedad });
      comp.ngOnInit();

      expect(municipioService.query).toHaveBeenCalled();
      expect(municipioService.addMunicipioToCollectionIfMissing).toHaveBeenCalledWith(
        municipioCollection,
        ...additionalMunicipios.map(expect.objectContaining),
      );
      expect(comp.municipiosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sociedad: ISociedad = { id: 456 };
      const municipio: IMunicipio = { id: 31017 };
      sociedad.municipio = municipio;

      activatedRoute.data = of({ sociedad });
      comp.ngOnInit();

      expect(comp.municipiosSharedCollection).toContain(municipio);
      expect(comp.sociedad).toEqual(sociedad);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISociedad>>();
      const sociedad = { id: 123 };
      jest.spyOn(sociedadFormService, 'getSociedad').mockReturnValue(sociedad);
      jest.spyOn(sociedadService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sociedad });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sociedad }));
      saveSubject.complete();

      // THEN
      expect(sociedadFormService.getSociedad).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sociedadService.update).toHaveBeenCalledWith(expect.objectContaining(sociedad));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISociedad>>();
      const sociedad = { id: 123 };
      jest.spyOn(sociedadFormService, 'getSociedad').mockReturnValue({ id: null });
      jest.spyOn(sociedadService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sociedad: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sociedad }));
      saveSubject.complete();

      // THEN
      expect(sociedadFormService.getSociedad).toHaveBeenCalled();
      expect(sociedadService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISociedad>>();
      const sociedad = { id: 123 };
      jest.spyOn(sociedadService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sociedad });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sociedadService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMunicipio', () => {
      it('Should forward to municipioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(municipioService, 'compareMunicipio');
        comp.compareMunicipio(entity, entity2);
        expect(municipioService.compareMunicipio).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
