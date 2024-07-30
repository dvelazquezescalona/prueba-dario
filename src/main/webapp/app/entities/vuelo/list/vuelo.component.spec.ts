import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VueloService } from '../service/vuelo.service';

import { VueloComponent } from './vuelo.component';

describe('Vuelo Management Component', () => {
  let comp: VueloComponent;
  let fixture: ComponentFixture<VueloComponent>;
  let service: VueloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'vuelo', component: VueloComponent }]), HttpClientTestingModule, VueloComponent],
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
      .overrideTemplate(VueloComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VueloComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(VueloService);

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
    expect(comp.vuelos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to vueloService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getVueloIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getVueloIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
