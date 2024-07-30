import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { VueloComponent } from './list/vuelo.component';
import { VueloDetailComponent } from './detail/vuelo-detail.component';
import { VueloUpdateComponent } from './update/vuelo-update.component';
import VueloResolve from './route/vuelo-routing-resolve.service';

const vueloRoute: Routes = [
  {
    path: '',
    component: VueloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VueloDetailComponent,
    resolve: {
      vuelo: VueloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VueloUpdateComponent,
    resolve: {
      vuelo: VueloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VueloUpdateComponent,
    resolve: {
      vuelo: VueloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default vueloRoute;
