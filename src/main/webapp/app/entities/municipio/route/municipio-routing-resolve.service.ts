import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMunicipio } from '../municipio.model';
import { MunicipioService } from '../service/municipio.service';

export const municipioResolve = (route: ActivatedRouteSnapshot): Observable<null | IMunicipio> => {
  const id = route.params['id'];
  if (id) {
    return inject(MunicipioService)
      .find(id)
      .pipe(
        mergeMap((municipio: HttpResponse<IMunicipio>) => {
          if (municipio.body) {
            return of(municipio.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default municipioResolve;
