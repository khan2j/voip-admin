import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAsteriskAccount } from 'app/shared/model/asterisk-account.model';

type EntityResponseType = HttpResponse<IAsteriskAccount>;
type EntityArrayResponseType = HttpResponse<IAsteriskAccount[]>;

@Injectable({ providedIn: 'root' })
export class AsteriskAccountService {
  public resourceUrl = SERVER_API_URL + 'api/asterisk-accounts';

  constructor(protected http: HttpClient) {}

  create(asteriskAccount: IAsteriskAccount): Observable<EntityResponseType> {
    return this.http.post<IAsteriskAccount>(this.resourceUrl, asteriskAccount, { observe: 'response' });
  }

  update(asteriskAccount: IAsteriskAccount): Observable<EntityResponseType> {
    return this.http.put<IAsteriskAccount>(this.resourceUrl, asteriskAccount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAsteriskAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAsteriskAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
