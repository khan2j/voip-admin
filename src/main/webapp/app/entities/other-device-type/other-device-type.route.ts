import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOtherDeviceType, OtherDeviceType } from 'app/shared/model/other-device-type.model';
import { OtherDeviceTypeService } from './other-device-type.service';
import { OtherDeviceTypeComponent } from './other-device-type.component';
import { OtherDeviceTypeDetailComponent } from './other-device-type-detail.component';
import { OtherDeviceTypeUpdateComponent } from './other-device-type-update.component';

@Injectable({ providedIn: 'root' })
export class OtherDeviceTypeResolve implements Resolve<IOtherDeviceType> {
  constructor(private service: OtherDeviceTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOtherDeviceType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((otherDeviceType: HttpResponse<OtherDeviceType>) => {
          if (otherDeviceType.body) {
            return of(otherDeviceType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OtherDeviceType());
  }
}

export const otherDeviceTypeRoute: Routes = [
  {
    path: '',
    component: OtherDeviceTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'voipadminApp.otherDeviceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OtherDeviceTypeDetailComponent,
    resolve: {
      otherDeviceType: OtherDeviceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'voipadminApp.otherDeviceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OtherDeviceTypeUpdateComponent,
    resolve: {
      otherDeviceType: OtherDeviceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'voipadminApp.otherDeviceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OtherDeviceTypeUpdateComponent,
    resolve: {
      otherDeviceType: OtherDeviceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'voipadminApp.otherDeviceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
