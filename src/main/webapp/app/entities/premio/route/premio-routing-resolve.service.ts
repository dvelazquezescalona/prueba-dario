import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPremio } from '../premio.model';
import { PremioService } from '../service/premio.service';

export const premioResolve = (route: ActivatedRouteSnapshot): Observable<null | IPremio> => {
  const id = route.params['id'];
  if (id) {
    return inject(PremioService)
      .find(id)
      .pipe(
        mergeMap((premio: HttpResponse<IPremio>) => {
          if (premio.body) {
            return of(premio.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default premioResolve;
