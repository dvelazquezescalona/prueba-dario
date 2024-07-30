import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IColombofiloVuelo } from '../colombofilo-vuelo.model';
import { ColombofiloVueloService } from '../service/colombofilo-vuelo.service';

import colombofiloVueloResolve from './colombofilo-vuelo-routing-resolve.service';

describe('ColombofiloVuelo routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ColombofiloVueloService;
  let resultColombofiloVuelo: IColombofiloVuelo | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(ColombofiloVueloService);
    resultColombofiloVuelo = undefined;
  });

  describe('resolve', () => {
    it('should return IColombofiloVuelo returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        colombofiloVueloResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultColombofiloVuelo = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultColombofiloVuelo).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        colombofiloVueloResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultColombofiloVuelo = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultColombofiloVuelo).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IColombofiloVuelo>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        colombofiloVueloResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultColombofiloVuelo = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultColombofiloVuelo).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
