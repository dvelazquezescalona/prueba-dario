import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IParadero } from 'app/entities/paradero/paradero.model';
import { ParaderoService } from 'app/entities/paradero/service/paradero.service';
import { VueloService } from '../service/vuelo.service';
import { IVuelo } from '../vuelo.model';
import { VueloFormService } from './vuelo-form.service';

import { VueloUpdateComponent } from './vuelo-update.component';

describe('Vuelo Management Update Component', () => {
  let comp: VueloUpdateComponent;
  let fixture: ComponentFixture<VueloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let vueloFormService: VueloFormService;
  let vueloService: VueloService;
  let paraderoService: ParaderoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), VueloUpdateComponent],
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
      .overrideTemplate(VueloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VueloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    vueloFormService = TestBed.inject(VueloFormService);
    vueloService = TestBed.inject(VueloService);
    paraderoService = TestBed.inject(ParaderoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Paradero query and add missing value', () => {
      const vuelo: IVuelo = { id: 456 };
      const paradero: IParadero = { id: 26097 };
      vuelo.paradero = paradero;

      const paraderoCollection: IParadero[] = [{ id: 29775 }];
      jest.spyOn(paraderoService, 'query').mockReturnValue(of(new HttpResponse({ body: paraderoCollection })));
      const additionalParaderos = [paradero];
      const expectedCollection: IParadero[] = [...additionalParaderos, ...paraderoCollection];
      jest.spyOn(paraderoService, 'addParaderoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ vuelo });
      comp.ngOnInit();

      expect(paraderoService.query).toHaveBeenCalled();
      expect(paraderoService.addParaderoToCollectionIfMissing).toHaveBeenCalledWith(
        paraderoCollection,
        ...additionalParaderos.map(expect.objectContaining),
      );
      expect(comp.paraderosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const vuelo: IVuelo = { id: 456 };
      const paradero: IParadero = { id: 3434 };
      vuelo.paradero = paradero;

      activatedRoute.data = of({ vuelo });
      comp.ngOnInit();

      expect(comp.paraderosSharedCollection).toContain(paradero);
      expect(comp.vuelo).toEqual(vuelo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVuelo>>();
      const vuelo = { id: 123 };
      jest.spyOn(vueloFormService, 'getVuelo').mockReturnValue(vuelo);
      jest.spyOn(vueloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vuelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vuelo }));
      saveSubject.complete();

      // THEN
      expect(vueloFormService.getVuelo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(vueloService.update).toHaveBeenCalledWith(expect.objectContaining(vuelo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVuelo>>();
      const vuelo = { id: 123 };
      jest.spyOn(vueloFormService, 'getVuelo').mockReturnValue({ id: null });
      jest.spyOn(vueloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vuelo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vuelo }));
      saveSubject.complete();

      // THEN
      expect(vueloFormService.getVuelo).toHaveBeenCalled();
      expect(vueloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVuelo>>();
      const vuelo = { id: 123 };
      jest.spyOn(vueloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vuelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(vueloService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareParadero', () => {
      it('Should forward to paraderoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(paraderoService, 'compareParadero');
        comp.compareParadero(entity, entity2);
        expect(paraderoService.compareParadero).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
