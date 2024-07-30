import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVuelo } from '../vuelo.model';
import { VueloService } from '../service/vuelo.service';

export const vueloResolve = (route: ActivatedRouteSnapshot): Observable<null | IVuelo> => {
  const id = route.params['id'];
  if (id) {
    return inject(VueloService)
      .find(id)
      .pipe(
        mergeMap((vuelo: HttpResponse<IVuelo>) => {
          if (vuelo.body) {
            return of(vuelo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default vueloResolve;
