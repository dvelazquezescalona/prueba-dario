import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IParadero } from '../paradero.model';
import { ParaderoService } from '../service/paradero.service';

export const paraderoResolve = (route: ActivatedRouteSnapshot): Observable<null | IParadero> => {
  const id = route.params['id'];
  if (id) {
    return inject(ParaderoService)
      .find(id)
      .pipe(
        mergeMap((paradero: HttpResponse<IParadero>) => {
          if (paradero.body) {
            return of(paradero.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default paraderoResolve;
