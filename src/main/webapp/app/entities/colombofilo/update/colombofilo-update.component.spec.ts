import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ISociedad } from 'app/entities/sociedad/sociedad.model';
import { SociedadService } from 'app/entities/sociedad/service/sociedad.service';
import { ColombofiloService } from '../service/colombofilo.service';
import { IColombofilo } from '../colombofilo.model';
import { ColombofiloFormService } from './colombofilo-form.service';

import { ColombofiloUpdateComponent } from './colombofilo-update.component';

describe('Colombofilo Management Update Component', () => {
  let comp: ColombofiloUpdateComponent;
  let fixture: ComponentFixture<ColombofiloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let colombofiloFormService: ColombofiloFormService;
  let colombofiloService: ColombofiloService;
  let sociedadService: SociedadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ColombofiloUpdateComponent],
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
      .overrideTemplate(ColombofiloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ColombofiloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    colombofiloFormService = TestBed.inject(ColombofiloFormService);
    colombofiloService = TestBed.inject(ColombofiloService);
    sociedadService = TestBed.inject(SociedadService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Sociedad query and add missing value', () => {
      const colombofilo: IColombofilo = { id: 456 };
      const sociedad: ISociedad = { id: 527 };
      colombofilo.sociedad = sociedad;

      const sociedadCollection: ISociedad[] = [{ id: 12198 }];
      jest.spyOn(sociedadService, 'query').mockReturnValue(of(new HttpResponse({ body: sociedadCollection })));
      const additionalSociedads = [sociedad];
      const expectedCollection: ISociedad[] = [...additionalSociedads, ...sociedadCollection];
      jest.spyOn(sociedadService, 'addSociedadToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ colombofilo });
      comp.ngOnInit();

      expect(sociedadService.query).toHaveBeenCalled();
      expect(sociedadService.addSociedadToCollectionIfMissing).toHaveBeenCalledWith(
        sociedadCollection,
        ...additionalSociedads.map(expect.objectContaining),
      );
      expect(comp.sociedadsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const colombofilo: IColombofilo = { id: 456 };
      const sociedad: ISociedad = { id: 25385 };
      colombofilo.sociedad = sociedad;

      activatedRoute.data = of({ colombofilo });
      comp.ngOnInit();

      expect(comp.sociedadsSharedCollection).toContain(sociedad);
      expect(comp.colombofilo).toEqual(colombofilo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IColombofilo>>();
      const colombofilo = { id: 123 };
      jest.spyOn(colombofiloFormService, 'getColombofilo').mockReturnValue(colombofilo);
      jest.spyOn(colombofiloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ colombofilo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: colombofilo }));
      saveSubject.complete();

      // THEN
      expect(colombofiloFormService.getColombofilo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(colombofiloService.update).toHaveBeenCalledWith(expect.objectContaining(colombofilo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IColombofilo>>();
      const colombofilo = { id: 123 };
      jest.spyOn(colombofiloFormService, 'getColombofilo').mockReturnValue({ id: null });
      jest.spyOn(colombofiloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ colombofilo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: colombofilo }));
      saveSubject.complete();

      // THEN
      expect(colombofiloFormService.getColombofilo).toHaveBeenCalled();
      expect(colombofiloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IColombofilo>>();
      const colombofilo = { id: 123 };
      jest.spyOn(colombofiloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ colombofilo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(colombofiloService.update).toHaveBeenCalled();
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
