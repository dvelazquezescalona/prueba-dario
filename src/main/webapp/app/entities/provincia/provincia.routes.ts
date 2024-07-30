import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ProvinciaComponent } from './list/provincia.component';
import { ProvinciaDetailComponent } from './detail/provincia-detail.component';
import { ProvinciaUpdateComponent } from './update/provincia-update.component';
import ProvinciaResolve from './route/provincia-routing-resolve.service';

const provinciaRoute: Routes = [
  {
    path: '',
    component: ProvinciaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProvinciaDetailComponent,
    resolve: {
      provincia: ProvinciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProvinciaUpdateComponent,
    resolve: {
      provincia: ProvinciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProvinciaUpdateComponent,
    resolve: {
      provincia: ProvinciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default provinciaRoute;
