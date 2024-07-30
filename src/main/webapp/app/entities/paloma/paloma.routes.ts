import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PalomaComponent } from './list/paloma.component';
import { PalomaDetailComponent } from './detail/paloma-detail.component';
import { PalomaUpdateComponent } from './update/paloma-update.component';
import PalomaResolve from './route/paloma-routing-resolve.service';

const palomaRoute: Routes = [
  {
    path: '',
    component: PalomaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PalomaDetailComponent,
    resolve: {
      paloma: PalomaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PalomaUpdateComponent,
    resolve: {
      paloma: PalomaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PalomaUpdateComponent,
    resolve: {
      paloma: PalomaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default palomaRoute;
