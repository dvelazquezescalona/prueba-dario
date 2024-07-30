import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IPaloma } from 'app/entities/paloma/paloma.model';
import { PalomaService } from 'app/entities/paloma/service/paloma.service';
import { IVuelo } from 'app/entities/vuelo/vuelo.model';
import { VueloService } from 'app/entities/vuelo/service/vuelo.service';
import { IPremio } from '../premio.model';
import { PremioService } from '../service/premio.service';
import { PremioFormService } from './premio-form.service';

import { PremioUpdateComponent } from './premio-update.component';

describe('Premio Management Update Component', () => {
  let comp: PremioUpdateComponent;
  let fixture: ComponentFixture<PremioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let premioFormService: PremioFormService;
  let premioService: PremioService;
  let palomaService: PalomaService;
  let vueloService: VueloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), PremioUpdateComponent],
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
      .overrideTemplate(PremioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PremioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    premioFormService = TestBed.inject(PremioFormService);
    premioService = TestBed.inject(PremioService);
    palomaService = TestBed.inject(PalomaService);
    vueloService = TestBed.inject(VueloService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Paloma query and add missing value', () => {
      const premio: IPremio = { id: 456 };
      const paloma: IPaloma = { id: 11885 };
      premio.paloma = paloma;

      const palomaCollection: IPaloma[] = [{ id: 13703 }];
      jest.spyOn(palomaService, 'query').mockReturnValue(of(new HttpResponse({ body: palomaCollection })));
      const additionalPalomas = [paloma];
      const expectedCollection: IPaloma[] = [...additionalPalomas, ...palomaCollection];
      jest.spyOn(palomaService, 'addPalomaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ premio });
      comp.ngOnInit();

      expect(palomaService.query).toHaveBeenCalled();
      expect(palomaService.addPalomaToCollectionIfMissing).toHaveBeenCalledWith(
        palomaCollection,
        ...additionalPalomas.map(expect.objectContaining),
      );
      expect(comp.palomasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Vuelo query and add missing value', () => {
      const premio: IPremio = { id: 456 };
      const vuelo: IVuelo = { id: 12896 };
      premio.vuelo = vuelo;

      const vueloCollection: IVuelo[] = [{ id: 9204 }];
      jest.spyOn(vueloService, 'query').mockReturnValue(of(new HttpResponse({ body: vueloCollection })));
      const additionalVuelos = [vuelo];
      const expectedCollection: IVuelo[] = [...additionalVuelos, ...vueloCollection];
      jest.spyOn(vueloService, 'addVueloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ premio });
      comp.ngOnInit();

      expect(vueloService.query).toHaveBeenCalled();
      expect(vueloService.addVueloToCollectionIfMissing).toHaveBeenCalledWith(
        vueloCollection,
        ...additionalVuelos.map(expect.objectContaining),
      );
      expect(comp.vuelosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const premio: IPremio = { id: 456 };
      const paloma: IPaloma = { id: 29759 };
      premio.paloma = paloma;
      const vuelo: IVuelo = { id: 28569 };
      premio.vuelo = vuelo;

      activatedRoute.data = of({ premio });
      comp.ngOnInit();

      expect(comp.palomasSharedCollection).toContain(paloma);
      expect(comp.vuelosSharedCollection).toContain(vuelo);
      expect(comp.premio).toEqual(premio);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPremio>>();
      const premio = { id: 123 };
      jest.spyOn(premioFormService, 'getPremio').mockReturnValue(premio);
      jest.spyOn(premioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ premio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: premio }));
      saveSubject.complete();

      // THEN
      expect(premioFormService.getPremio).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(premioService.update).toHaveBeenCalledWith(expect.objectContaining(premio));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPremio>>();
      const premio = { id: 123 };
      jest.spyOn(premioFormService, 'getPremio').mockReturnValue({ id: null });
      jest.spyOn(premioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ premio: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: premio }));
      saveSubject.complete();

      // THEN
      expect(premioFormService.getPremio).toHaveBeenCalled();
      expect(premioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPremio>>();
      const premio = { id: 123 };
      jest.spyOn(premioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ premio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(premioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePaloma', () => {
      it('Should forward to palomaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(palomaService, 'comparePaloma');
        comp.comparePaloma(entity, entity2);
        expect(palomaService.comparePaloma).toHaveBeenCalledWith(entity, entity2);
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
