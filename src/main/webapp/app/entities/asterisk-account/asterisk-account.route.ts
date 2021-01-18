import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAsteriskAccount, AsteriskAccount } from 'app/shared/model/asterisk-account.model';
import { AsteriskAccountService } from './asterisk-account.service';
import { AsteriskAccountComponent } from './asterisk-account.component';
import { AsteriskAccountDetailComponent } from './asterisk-account-detail.component';
import { AsteriskAccountUpdateComponent } from './asterisk-account-update.component';

@Injectable({ providedIn: 'root' })
export class AsteriskAccountResolve implements Resolve<IAsteriskAccount> {
  constructor(private service: AsteriskAccountService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAsteriskAccount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((asteriskAccount: HttpResponse<AsteriskAccount>) => {
          if (asteriskAccount.body) {
            return of(asteriskAccount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AsteriskAccount());
  }
}

export const asteriskAccountRoute: Routes = [
  {
    path: '',
    component: AsteriskAccountComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'voipadminApp.asteriskAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AsteriskAccountDetailComponent,
    resolve: {
      asteriskAccount: AsteriskAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'voipadminApp.asteriskAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AsteriskAccountUpdateComponent,
    resolve: {
      asteriskAccount: AsteriskAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'voipadminApp.asteriskAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AsteriskAccountUpdateComponent,
    resolve: {
      asteriskAccount: AsteriskAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'voipadminApp.asteriskAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
