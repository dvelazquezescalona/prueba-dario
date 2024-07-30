import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ColombofiloVueloComponent } from './list/colombofilo-vuelo.component';
import { ColombofiloVueloDetailComponent } from './detail/colombofilo-vuelo-detail.component';
import { ColombofiloVueloUpdateComponent } from './update/colombofilo-vuelo-update.component';
import ColombofiloVueloResolve from './route/colombofilo-vuelo-routing-resolve.service';

const colombofiloVueloRoute: Routes = [
  {
    path: '',
    component: ColombofiloVueloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ColombofiloVueloDetailComponent,
    resolve: {
      colombofiloVuelo: ColombofiloVueloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ColombofiloVueloUpdateComponent,
    resolve: {
      colombofiloVuelo: ColombofiloVueloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ColombofiloVueloUpdateComponent,
    resolve: {
      colombofiloVuelo: ColombofiloVueloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default colombofiloVueloRoute;
