import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PremioComponent } from './list/premio.component';
import { PremioDetailComponent } from './detail/premio-detail.component';
import { PremioUpdateComponent } from './update/premio-update.component';
import PremioResolve from './route/premio-routing-resolve.service';

const premioRoute: Routes = [
  {
    path: '',
    component: PremioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PremioDetailComponent,
    resolve: {
      premio: PremioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PremioUpdateComponent,
    resolve: {
      premio: PremioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PremioUpdateComponent,
    resolve: {
      premio: PremioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default premioRoute;
