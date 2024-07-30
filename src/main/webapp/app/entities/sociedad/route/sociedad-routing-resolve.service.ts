import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISociedad } from '../sociedad.model';
import { SociedadService } from '../service/sociedad.service';

export const sociedadResolve = (route: ActivatedRouteSnapshot): Observable<null | ISociedad> => {
  const id = route.params['id'];
  if (id) {
    return inject(SociedadService)
      .find(id)
      .pipe(
        mergeMap((sociedad: HttpResponse<ISociedad>) => {
          if (sociedad.body) {
            return of(sociedad.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default sociedadResolve;
