import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ParaderoService } from '../service/paradero.service';

import { ParaderoComponent } from './paradero.component';

describe('Paradero Management Component', () => {
  let comp: ParaderoComponent;
  let fixture: ComponentFixture<ParaderoComponent>;
  let service: ParaderoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'paradero', component: ParaderoComponent }]),
        HttpClientTestingModule,
        ParaderoComponent,
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
      .overrideTemplate(ParaderoComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParaderoComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ParaderoService);

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
    expect(comp.paraderos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to paraderoService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getParaderoIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getParaderoIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
