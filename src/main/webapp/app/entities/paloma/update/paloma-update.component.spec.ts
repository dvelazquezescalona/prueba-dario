import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IColor } from 'app/entities/color/color.model';
import { ColorService } from 'app/entities/color/service/color.service';
import { IColombofilo } from 'app/entities/colombofilo/colombofilo.model';
import { ColombofiloService } from 'app/entities/colombofilo/service/colombofilo.service';
import { IPaloma } from '../paloma.model';
import { PalomaService } from '../service/paloma.service';
import { PalomaFormService } from './paloma-form.service';

import { PalomaUpdateComponent } from './paloma-update.component';

describe('Paloma Management Update Component', () => {
  let comp: PalomaUpdateComponent;
  let fixture: ComponentFixture<PalomaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let palomaFormService: PalomaFormService;
  let palomaService: PalomaService;
  let colorService: ColorService;
  let colombofiloService: ColombofiloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), PalomaUpdateComponent],
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
      .overrideTemplate(PalomaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PalomaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    palomaFormService = TestBed.inject(PalomaFormService);
    palomaService = TestBed.inject(PalomaService);
    colorService = TestBed.inject(ColorService);
    colombofiloService = TestBed.inject(ColombofiloService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Color query and add missing value', () => {
      const paloma: IPaloma = { id: 456 };
      const color: IColor = { id: 18327 };
      paloma.color = color;

      const colorCollection: IColor[] = [{ id: 19522 }];
      jest.spyOn(colorService, 'query').mockReturnValue(of(new HttpResponse({ body: colorCollection })));
      const additionalColors = [color];
      const expectedCollection: IColor[] = [...additionalColors, ...colorCollection];
      jest.spyOn(colorService, 'addColorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paloma });
      comp.ngOnInit();

      expect(colorService.query).toHaveBeenCalled();
      expect(colorService.addColorToCollectionIfMissing).toHaveBeenCalledWith(
        colorCollection,
        ...additionalColors.map(expect.objectContaining),
      );
      expect(comp.colorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Colombofilo query and add missing value', () => {
      const paloma: IPaloma = { id: 456 };
      const colombofilo: IColombofilo = { id: 4771 };
      paloma.colombofilo = colombofilo;

      const colombofiloCollection: IColombofilo[] = [{ id: 13909 }];
      jest.spyOn(colombofiloService, 'query').mockReturnValue(of(new HttpResponse({ body: colombofiloCollection })));
      const additionalColombofilos = [colombofilo];
      const expectedCollection: IColombofilo[] = [...additionalColombofilos, ...colombofiloCollection];
      jest.spyOn(colombofiloService, 'addColombofiloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paloma });
      comp.ngOnInit();

      expect(colombofiloService.query).toHaveBeenCalled();
      expect(colombofiloService.addColombofiloToCollectionIfMissing).toHaveBeenCalledWith(
        colombofiloCollection,
        ...additionalColombofilos.map(expect.objectContaining),
      );
      expect(comp.colombofilosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paloma: IPaloma = { id: 456 };
      const color: IColor = { id: 7378 };
      paloma.color = color;
      const colombofilo: IColombofilo = { id: 30072 };
      paloma.colombofilo = colombofilo;

      activatedRoute.data = of({ paloma });
      comp.ngOnInit();

      expect(comp.colorsSharedCollection).toContain(color);
      expect(comp.colombofilosSharedCollection).toContain(colombofilo);
      expect(comp.paloma).toEqual(paloma);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaloma>>();
      const paloma = { id: 123 };
      jest.spyOn(palomaFormService, 'getPaloma').mockReturnValue(paloma);
      jest.spyOn(palomaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paloma });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paloma }));
      saveSubject.complete();

      // THEN
      expect(palomaFormService.getPaloma).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(palomaService.update).toHaveBeenCalledWith(expect.objectContaining(paloma));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaloma>>();
      const paloma = { id: 123 };
      jest.spyOn(palomaFormService, 'getPaloma').mockReturnValue({ id: null });
      jest.spyOn(palomaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paloma: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paloma }));
      saveSubject.complete();

      // THEN
      expect(palomaFormService.getPaloma).toHaveBeenCalled();
      expect(palomaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaloma>>();
      const paloma = { id: 123 };
      jest.spyOn(palomaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paloma });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(palomaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareColor', () => {
      it('Should forward to colorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(colorService, 'compareColor');
        comp.compareColor(entity, entity2);
        expect(colorService.compareColor).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareColombofilo', () => {
      it('Should forward to colombofiloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(colombofiloService, 'compareColombofilo');
        comp.compareColombofilo(entity, entity2);
        expect(colombofiloService.compareColombofilo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
