import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IColor } from '../color.model';
import { ColorService } from '../service/color.service';

export const colorResolve = (route: ActivatedRouteSnapshot): Observable<null | IColor> => {
  const id = route.params['id'];
  if (id) {
    return inject(ColorService)
      .find(id)
      .pipe(
        mergeMap((color: HttpResponse<IColor>) => {
          if (color.body) {
            return of(color.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default colorResolve;
