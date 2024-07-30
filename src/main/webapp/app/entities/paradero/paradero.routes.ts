import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ParaderoComponent } from './list/paradero.component';
import { ParaderoDetailComponent } from './detail/paradero-detail.component';
import { ParaderoUpdateComponent } from './update/paradero-update.component';
import ParaderoResolve from './route/paradero-routing-resolve.service';

const paraderoRoute: Routes = [
  {
    path: '',
    component: ParaderoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParaderoDetailComponent,
    resolve: {
      paradero: ParaderoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParaderoUpdateComponent,
    resolve: {
      paradero: ParaderoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParaderoUpdateComponent,
    resolve: {
      paradero: ParaderoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default paraderoRoute;
