import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IColombofilo } from 'app/entities/colombofilo/colombofilo.model';
import { ColombofiloService } from 'app/entities/colombofilo/service/colombofilo.service';
import { IVuelo } from 'app/entities/vuelo/vuelo.model';
import { VueloService } from 'app/entities/vuelo/service/vuelo.service';
import { IColombofiloVuelo } from '../colombofilo-vuelo.model';
import { ColombofiloVueloService } from '../service/colombofilo-vuelo.service';
import { ColombofiloVueloFormService } from './colombofilo-vuelo-form.service';

import { ColombofiloVueloUpdateComponent } from './colombofilo-vuelo-update.component';

describe('ColombofiloVuelo Management Update Component', () => {
  let comp: ColombofiloVueloUpdateComponent;
  let fixture: ComponentFixture<ColombofiloVueloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let colombofiloVueloFormService: ColombofiloVueloFormService;
  let colombofiloVueloService: ColombofiloVueloService;
  let colombofiloService: ColombofiloService;
  let vueloService: VueloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ColombofiloVueloUpdateComponent],
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
      .overrideTemplate(ColombofiloVueloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ColombofiloVueloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    colombofiloVueloFormService = TestBed.inject(ColombofiloVueloFormService);
    colombofiloVueloService = TestBed.inject(ColombofiloVueloService);
    colombofiloService = TestBed.inject(ColombofiloService);
    vueloService = TestBed.inject(VueloService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Colombofilo query and add missing value', () => {
      const colombofiloVuelo: IColombofiloVuelo = { id: 456 };
      const colombofilo: IColombofilo = { id: 5397 };
      colombofiloVuelo.colombofilo = colombofilo;

      const colombofiloCollection: IColombofilo[] = [{ id: 10391 }];
      jest.spyOn(colombofiloService, 'query').mockReturnValue(of(new HttpResponse({ body: colombofiloCollection })));
      const additionalColombofilos = [colombofilo];
      const expectedCollection: IColombofilo[] = [...additionalColombofilos, ...colombofiloCollection];
      jest.spyOn(colombofiloService, 'addColombofiloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ colombofiloVuelo });
      comp.ngOnInit();

      expect(colombofiloService.query).toHaveBeenCalled();
      expect(colombofiloService.addColombofiloToCollectionIfMissing).toHaveBeenCalledWith(
        colombofiloCollection,
        ...additionalColombofilos.map(expect.objectContaining),
      );
      expect(comp.colombofilosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Vuelo query and add missing value', () => {
      const colombofiloVuelo: IColombofiloVuelo = { id: 456 };
      const vuelo: IVuelo = { id: 24887 };
      colombofiloVuelo.vuelo = vuelo;

      const vueloCollection: IVuelo[] = [{ id: 20489 }];
      jest.spyOn(vueloService, 'query').mockReturnValue(of(new HttpResponse({ body: vueloCollection })));
      const additionalVuelos = [vuelo];
      const expectedCollection: IVuelo[] = [...additionalVuelos, ...vueloCollection];
      jest.spyOn(vueloService, 'addVueloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ colombofiloVuelo });
      comp.ngOnInit();

      expect(vueloService.query).toHaveBeenCalled();
      expect(vueloService.addVueloToCollectionIfMissing).toHaveBeenCalledWith(
        vueloCollection,
        ...additionalVuelos.map(expect.objectContaining),
      );
      expect(comp.vuelosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const colombofiloVuelo: IColombofiloVuelo = { id: 456 };
      const colombofilo: IColombofilo = { id: 12712 };
      colombofiloVuelo.colombofilo = colombofilo;
      const vuelo: IVuelo = { id: 30439 };
      colombofiloVuelo.vuelo = vuelo;

      activatedRoute.data = of({ colombofiloVuelo });
      comp.ngOnInit();

      expect(comp.colombofilosSharedCollection).toContain(colombofilo);
      expect(comp.vuelosSharedCollection).toContain(vuelo);
      expect(comp.colombofiloVuelo).toEqual(colombofiloVuelo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IColombofiloVuelo>>();
      const colombofiloVuelo = { id: 123 };
      jest.spyOn(colombofiloVueloFormService, 'getColombofiloVuelo').mockReturnValue(colombofiloVuelo);
      jest.spyOn(colombofiloVueloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ colombofiloVuelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: colombofiloVuelo }));
      saveSubject.complete();

      // THEN
      expect(colombofiloVueloFormService.getColombofiloVuelo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(colombofiloVueloService.update).toHaveBeenCalledWith(expect.objectContaining(colombofiloVuelo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IColombofiloVuelo>>();
      const colombofiloVuelo = { id: 123 };
      jest.spyOn(colombofiloVueloFormService, 'getColombofiloVuelo').mockReturnValue({ id: null });
      jest.spyOn(colombofiloVueloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ colombofiloVuelo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: colombofiloVuelo }));
      saveSubject.complete();

      // THEN
      expect(colombofiloVueloFormService.getColombofiloVuelo).toHaveBeenCalled();
      expect(colombofiloVueloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IColombofiloVuelo>>();
      const colombofiloVuelo = { id: 123 };
      jest.spyOn(colombofiloVueloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ colombofiloVuelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(colombofiloVueloService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareColombofilo', () => {
      it('Should forward to colombofiloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(colombofiloService, 'compareColombofilo');
        comp.compareColombofilo(entity, entity2);
        expect(colombofiloService.compareColombofilo).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareVuelo', () => {
      it('Should forward to vueloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(vueloService, 'compareVuelo');
        comp.compareVuelo(entity, entity2);
        expect(vueloService.compareVuelo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
