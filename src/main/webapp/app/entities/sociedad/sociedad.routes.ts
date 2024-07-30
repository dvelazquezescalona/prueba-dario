import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SociedadComponent } from './list/sociedad.component';
import { SociedadDetailComponent } from './detail/sociedad-detail.component';
import { SociedadUpdateComponent } from './update/sociedad-update.component';
import SociedadResolve from './route/sociedad-routing-resolve.service';

const sociedadRoute: Routes = [
  {
    path: '',
    component: SociedadComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SociedadDetailComponent,
    resolve: {
      sociedad: SociedadResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SociedadUpdateComponent,
    resolve: {
      sociedad: SociedadResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SociedadUpdateComponent,
    resolve: {
      sociedad: SociedadResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sociedadRoute;
