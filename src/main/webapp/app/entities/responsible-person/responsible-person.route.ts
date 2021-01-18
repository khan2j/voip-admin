import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResponsiblePerson, ResponsiblePerson } from 'app/shared/model/responsible-person.model';
import { ResponsiblePersonService } from './responsible-person.service';
import { ResponsiblePersonComponent } from './responsible-person.component';
import { ResponsiblePersonDetailComponent } from './responsible-person-detail.component';
import { ResponsiblePersonUpdateComponent } from './responsible-person-update.component';

@Injectable({ providedIn: 'root' })
export class ResponsiblePersonResolve implements Resolve<IResponsiblePerson> {
  constructor(private service: ResponsiblePersonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResponsiblePerson> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((responsiblePerson: HttpResponse<ResponsiblePerson>) => {
          if (responsiblePerson.body) {
            return of(responsiblePerson.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResponsiblePerson());
  }
}

export const responsiblePersonRoute: Routes = [
  {
    path: '',
    component: ResponsiblePersonComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'voipadminApp.responsiblePerson.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResponsiblePersonDetailComponent,
    resolve: {
      responsiblePerson: ResponsiblePersonResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'voipadminApp.responsiblePerson.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResponsiblePersonUpdateComponent,
    resolve: {
      responsiblePerson: ResponsiblePersonResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'voipadminApp.responsiblePerson.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResponsiblePersonUpdateComponent,
    resolve: {
      responsiblePerson: ResponsiblePersonResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'voipadminApp.responsiblePerson.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
