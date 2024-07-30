import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SociedadService } from '../service/sociedad.service';

import { SociedadComponent } from './sociedad.component';

describe('Sociedad Management Component', () => {
  let comp: SociedadComponent;
  let fixture: ComponentFixture<SociedadComponent>;
  let service: SociedadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'sociedad', component: SociedadComponent }]),
        HttpClientTestingModule,
        SociedadComponent,
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
      .overrideTemplate(SociedadComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SociedadComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SociedadService);

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
    expect(comp.sociedads?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to sociedadService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getSociedadIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getSociedadIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
