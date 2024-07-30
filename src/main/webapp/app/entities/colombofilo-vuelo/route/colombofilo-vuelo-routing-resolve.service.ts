import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IColombofiloVuelo } from '../colombofilo-vuelo.model';
import { ColombofiloVueloService } from '../service/colombofilo-vuelo.service';

export const colombofiloVueloResolve = (route: ActivatedRouteSnapshot): Observable<null | IColombofiloVuelo> => {
  const id = route.params['id'];
  if (id) {
    return inject(ColombofiloVueloService)
      .find(id)
      .pipe(
        mergeMap((colombofiloVuelo: HttpResponse<IColombofiloVuelo>) => {
          if (colombofiloVuelo.body) {
            return of(colombofiloVuelo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default colombofiloVueloResolve;
