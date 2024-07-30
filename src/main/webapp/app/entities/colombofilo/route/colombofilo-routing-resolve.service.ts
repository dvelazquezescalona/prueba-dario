import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IColombofilo } from '../colombofilo.model';
import { ColombofiloService } from '../service/colombofilo.service';

export const colombofiloResolve = (route: ActivatedRouteSnapshot): Observable<null | IColombofilo> => {
  const id = route.params['id'];
  if (id) {
    return inject(ColombofiloService)
      .find(id)
      .pipe(
        mergeMap((colombofilo: HttpResponse<IColombofilo>) => {
          if (colombofilo.body) {
            return of(colombofilo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default colombofiloResolve;
