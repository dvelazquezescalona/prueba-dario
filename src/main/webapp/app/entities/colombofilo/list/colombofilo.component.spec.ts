import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ColombofiloService } from '../service/colombofilo.service';

import { ColombofiloComponent } from './colombofilo.component';

describe('Colombofilo Management Component', () => {
  let comp: ColombofiloComponent;
  let fixture: ComponentFixture<ColombofiloComponent>;
  let service: ColombofiloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'colombofilo', component: ColombofiloComponent }]),
        HttpClientTestingModule,
        ColombofiloComponent,
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
      .overrideTemplate(ColombofiloComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ColombofiloComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ColombofiloService);

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
    expect(comp.colombofilos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to colombofiloService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getColombofiloIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getColombofiloIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
