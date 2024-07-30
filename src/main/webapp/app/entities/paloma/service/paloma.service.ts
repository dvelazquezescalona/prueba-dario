import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaloma, NewPaloma } from '../paloma.model';

export type PartialUpdatePaloma = Partial<IPaloma> & Pick<IPaloma, 'id'>;

export type EntityResponseType = HttpResponse<IPaloma>;
export type EntityArrayResponseType = HttpResponse<IPaloma[]>;

@Injectable({ providedIn: 'root' })
export class PalomaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/palomas');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(paloma: NewPaloma): Observable<EntityResponseType> {
    return this.http.post<IPaloma>(this.resourceUrl, paloma, { observe: 'response' });
  }

  update(paloma: IPaloma): Observable<EntityResponseType> {
    return this.http.put<IPaloma>(`${this.resourceUrl}/${this.getPalomaIdentifier(paloma)}`, paloma, { observe: 'response' });
  }

  partialUpdate(paloma: PartialUpdatePaloma): Observable<EntityResponseType> {
    return this.http.patch<IPaloma>(`${this.resourceUrl}/${this.getPalomaIdentifier(paloma)}`, paloma, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaloma>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaloma[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPalomaIdentifier(paloma: Pick<IPaloma, 'id'>): number {
    return paloma.id;
  }

  comparePaloma(o1: Pick<IPaloma, 'id'> | null, o2: Pick<IPaloma, 'id'> | null): boolean {
    return o1 && o2 ? this.getPalomaIdentifier(o1) === this.getPalomaIdentifier(o2) : o1 === o2;
  }

  addPalomaToCollectionIfMissing<Type extends Pick<IPaloma, 'id'>>(
    palomaCollection: Type[],
    ...palomasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const palomas: Type[] = palomasToCheck.filter(isPresent);
    if (palomas.length > 0) {
      const palomaCollectionIdentifiers = palomaCollection.map(palomaItem => this.getPalomaIdentifier(palomaItem)!);
      const palomasToAdd = palomas.filter(palomaItem => {
        const palomaIdentifier = this.getPalomaIdentifier(palomaItem);
        if (palomaCollectionIdentifiers.includes(palomaIdentifier)) {
          return false;
        }
        palomaCollectionIdentifiers.push(palomaIdentifier);
        return true;
      });
      return [...palomasToAdd, ...palomaCollection];
    }
    return palomaCollection;
  }
}
