import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ColombofiloVueloService } from '../service/colombofilo-vuelo.service';

import { ColombofiloVueloComponent } from './colombofilo-vuelo.component';

describe('ColombofiloVuelo Management Component', () => {
  let comp: ColombofiloVueloComponent;
  let fixture: ComponentFixture<ColombofiloVueloComponent>;
  let service: ColombofiloVueloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'colombofilo-vuelo', component: ColombofiloVueloComponent }]),
        HttpClientTestingModule,
        ColombofiloVueloComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ColombofiloVueloComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ColombofiloVueloComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ColombofiloVueloService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.colombofiloVuelos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to colombofiloVueloService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getColombofiloVueloIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getColombofiloVueloIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
