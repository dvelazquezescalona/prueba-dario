import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ColorComponent } from './list/color.component';
import { ColorDetailComponent } from './detail/color-detail.component';
import { ColorUpdateComponent } from './update/color-update.component';
import ColorResolve from './route/color-routing-resolve.service';

const colorRoute: Routes = [
  {
    path: '',
    component: ColorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ColorDetailComponent,
    resolve: {
      color: ColorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ColorUpdateComponent,
    resolve: {
      color: ColorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ColorUpdateComponent,
    resolve: {
      color: ColorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default colorRoute;
