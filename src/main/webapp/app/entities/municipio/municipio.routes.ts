import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MunicipioComponent } from './list/municipio.component';
import { MunicipioDetailComponent } from './detail/municipio-detail.component';
import { MunicipioUpdateComponent } from './update/municipio-update.component';
import MunicipioResolve from './route/municipio-routing-resolve.service';

const municipioRoute: Routes = [
  {
    path: '',
    component: MunicipioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MunicipioDetailComponent,
    resolve: {
      municipio: MunicipioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MunicipioUpdateComponent,
    resolve: {
      municipio: MunicipioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MunicipioUpdateComponent,
    resolve: {
      municipio: MunicipioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default municipioRoute;
