import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaloma } from '../paloma.model';
import { PalomaService } from '../service/paloma.service';

export const palomaResolve = (route: ActivatedRouteSnapshot): Observable<null | IPaloma> => {
  const id = route.params['id'];
  if (id) {
    return inject(PalomaService)
      .find(id)
      .pipe(
        mergeMap((paloma: HttpResponse<IPaloma>) => {
          if (paloma.body) {
            return of(paloma.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default palomaResolve;
