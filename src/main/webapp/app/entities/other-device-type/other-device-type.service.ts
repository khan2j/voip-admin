import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOtherDeviceType } from 'app/shared/model/other-device-type.model';

type EntityResponseType = HttpResponse<IOtherDeviceType>;
type EntityArrayResponseType = HttpResponse<IOtherDeviceType[]>;

@Injectable({ providedIn: 'root' })
export class OtherDeviceTypeService {
  public resourceUrl = SERVER_API_URL + 'api/other-device-types';

  constructor(protected http: HttpClient) {}

  create(otherDeviceType: IOtherDeviceType): Observable<EntityResponseType> {
    return this.http.post<IOtherDeviceType>(this.resourceUrl, otherDeviceType, { observe: 'response' });
  }

  update(otherDeviceType: IOtherDeviceType): Observable<EntityResponseType> {
    return this.http.put<IOtherDeviceType>(this.resourceUrl, otherDeviceType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOtherDeviceType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOtherDeviceType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
