import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVoipAccount } from 'app/shared/model/voip-account.model';

type EntityResponseType = HttpResponse<IVoipAccount>;
type EntityArrayResponseType = HttpResponse<IVoipAccount[]>;

@Injectable({ providedIn: 'root' })
export class VoipAccountService {
  public resourceUrl = SERVER_API_URL + 'api/voip-accounts';

  constructor(protected http: HttpClient) {}

  create(voipAccount: IVoipAccount): Observable<EntityResponseType> {
    return this.http.post<IVoipAccount>(this.resourceUrl, voipAccount, { observe: 'response' });
  }

  update(voipAccount: IVoipAccount): Observable<EntityResponseType> {
    return this.http.put<IVoipAccount>(this.resourceUrl, voipAccount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVoipAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVoipAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
