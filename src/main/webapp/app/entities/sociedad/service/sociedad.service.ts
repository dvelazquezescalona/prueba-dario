import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISociedad, NewSociedad } from '../sociedad.model';

export type PartialUpdateSociedad = Partial<ISociedad> & Pick<ISociedad, 'id'>;

export type EntityResponseType = HttpResponse<ISociedad>;
export type EntityArrayResponseType = HttpResponse<ISociedad[]>;

@Injectable({ providedIn: 'root' })
export class SociedadService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sociedads');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(sociedad: NewSociedad): Observable<EntityResponseType> {
    return this.http.post<ISociedad>(this.resourceUrl, sociedad, { observe: 'response' });
  }

  update(sociedad: ISociedad): Observable<EntityResponseType> {
    return this.http.put<ISociedad>(`${this.resourceUrl}/${this.getSociedadIdentifier(sociedad)}`, sociedad, { observe: 'response' });
  }

  partialUpdate(sociedad: PartialUpdateSociedad): Observable<EntityResponseType> {
    return this.http.patch<ISociedad>(`${this.resourceUrl}/${this.getSociedadIdentifier(sociedad)}`, sociedad, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISociedad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISociedad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSociedadIdentifier(sociedad: Pick<ISociedad, 'id'>): number {
    return sociedad.id;
  }

  compareSociedad(o1: Pick<ISociedad, 'id'> | null, o2: Pick<ISociedad, 'id'> | null): boolean {
    return o1 && o2 ? this.getSociedadIdentifier(o1) === this.getSociedadIdentifier(o2) : o1 === o2;
  }

  addSociedadToCollectionIfMissing<Type extends Pick<ISociedad, 'id'>>(
    sociedadCollection: Type[],
    ...sociedadsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sociedads: Type[] = sociedadsToCheck.filter(isPresent);
    if (sociedads.length > 0) {
      const sociedadCollectionIdentifiers = sociedadCollection.map(sociedadItem => this.getSociedadIdentifier(sociedadItem)!);
      const sociedadsToAdd = sociedads.filter(sociedadItem => {
        const sociedadIdentifier = this.getSociedadIdentifier(sociedadItem);
        if (sociedadCollectionIdentifiers.includes(sociedadIdentifier)) {
          return false;
        }
        sociedadCollectionIdentifiers.push(sociedadIdentifier);
        return true;
      });
      return [...sociedadsToAdd, ...sociedadCollection];
    }
    return sociedadCollection;
  }
}
