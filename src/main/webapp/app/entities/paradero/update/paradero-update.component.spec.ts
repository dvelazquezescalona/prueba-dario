import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ISociedad } from 'app/entities/sociedad/sociedad.model';
import { SociedadService } from 'app/entities/sociedad/service/sociedad.service';
import { ParaderoService } from '../service/paradero.service';
import { IParadero } from '../paradero.model';
import { ParaderoFormService } from './paradero-form.service';

import { ParaderoUpdateComponent } from './paradero-update.component';

describe('Paradero Management Update Component', () => {
  let comp: ParaderoUpdateComponent;
  let fixture: ComponentFixture<ParaderoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paraderoFormService: ParaderoFormService;
  let paraderoService: ParaderoService;
  let sociedadService: SociedadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ParaderoUpdateComponent],
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
      .overrideTemplate(ParaderoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParaderoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paraderoFormService = TestBed.inject(ParaderoFormService);
    paraderoService = TestBed.inject(ParaderoService);
    sociedadService = TestBed.inject(SociedadService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Sociedad query and add missing value', () => {
      const paradero: IParadero = { id: 456 };
      const sociedad: ISociedad = { id: 6063 };
      paradero.sociedad = sociedad;

      const sociedadCollection: ISociedad[] = [{ id: 21967 }];
      jest.spyOn(sociedadService, 'query').mockReturnValue(of(new HttpResponse({ body: sociedadCollection })));
      const additionalSociedads = [sociedad];
      const expectedCollection: ISociedad[] = [...additionalSociedads, ...sociedadCollection];
      jest.spyOn(sociedadService, 'addSociedadToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paradero });
      comp.ngOnInit();

      expect(sociedadService.query).toHaveBeenCalled();
      expect(sociedadService.addSociedadToCollectionIfMissing).toHaveBeenCalledWith(
        sociedadCollection,
        ...additionalSociedads.map(expect.objectContaining),
      );
      expect(comp.sociedadsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paradero: IParadero = { id: 456 };
      const sociedad: ISociedad = { id: 11124 };
      paradero.sociedad = sociedad;

      activatedRoute.data = of({ paradero });
      comp.ngOnInit();

      expect(comp.sociedadsSharedCollection).toContain(sociedad);
      expect(comp.paradero).toEqual(paradero);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParadero>>();
      const paradero = { id: 123 };
      jest.spyOn(paraderoFormService, 'getParadero').mockReturnValue(paradero);
      jest.spyOn(paraderoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paradero });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paradero }));
      saveSubject.complete();

      // THEN
      expect(paraderoFormService.getParadero).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(paraderoService.update).toHaveBeenCalledWith(expect.objectContaining(paradero));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParadero>>();
      const paradero = { id: 123 };
      jest.spyOn(paraderoFormService, 'getParadero').mockReturnValue({ id: null });
      jest.spyOn(paraderoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paradero: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paradero }));
      saveSubject.complete();

      // THEN
      expect(paraderoFormService.getParadero).toHaveBeenCalled();
      expect(paraderoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParadero>>();
      const paradero = { id: 123 };
      jest.spyOn(paraderoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paradero });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paraderoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSociedad', () => {
      it('Should forward to sociedadService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(sociedadService, 'compareSociedad');
        comp.compareSociedad(entity, entity2);
        expect(sociedadService.compareSociedad).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
