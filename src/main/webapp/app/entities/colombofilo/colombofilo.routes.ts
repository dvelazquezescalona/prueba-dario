import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ColombofiloComponent } from './list/colombofilo.component';
import { ColombofiloDetailComponent } from './detail/colombofilo-detail.component';
import { ColombofiloUpdateComponent } from './update/colombofilo-update.component';
import ColombofiloResolve from './route/colombofilo-routing-resolve.service';

const colombofiloRoute: Routes = [
  {
    path: '',
    component: ColombofiloComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ColombofiloDetailComponent,
    resolve: {
      colombofilo: ColombofiloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ColombofiloUpdateComponent,
    resolve: {
      colombofilo: ColombofiloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ColombofiloUpdateComponent,
    resolve: {
      colombofilo: ColombofiloResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default colombofiloRoute;
