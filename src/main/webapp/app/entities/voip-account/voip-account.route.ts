import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVoipAccount, VoipAccount } from 'app/shared/model/voip-account.model';
import { VoipAccountService } from './voip-account.service';
import { VoipAccountComponent } from './voip-account.component';
import { VoipAccountDetailComponent } from './voip-account-detail.component';
import { VoipAccountUpdateComponent } from './voip-account-update.component';

@Injectable({ providedIn: 'root' })
export class VoipAccountResolve implements Resolve<IVoipAccount> {
  constructor(private service: VoipAccountService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVoipAccount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((voipAccount: HttpResponse<VoipAccount>) => {
          if (voipAccount.body) {
            return of(voipAccount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VoipAccount());
  }
}

export const voipAccountRoute: Routes = [
  {
    path: '',
    component: VoipAccountComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'voipadminApp.voipAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VoipAccountDetailComponent,
    resolve: {
      voipAccount: VoipAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'voipadminApp.voipAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VoipAccountUpdateComponent,
    resolve: {
      voipAccount: VoipAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'voipadminApp.voipAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VoipAccountUpdateComponent,
    resolve: {
      voipAccount: VoipAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'voipadminApp.voipAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
