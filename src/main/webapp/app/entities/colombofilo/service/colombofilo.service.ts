import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IColombofilo, NewColombofilo } from '../colombofilo.model';

export type PartialUpdateColombofilo = Partial<IColombofilo> & Pick<IColombofilo, 'id'>;

export type EntityResponseType = HttpResponse<IColombofilo>;
export type EntityArrayResponseType = HttpResponse<IColombofilo[]>;

@Injectable({ providedIn: 'root' })
export class ColombofiloService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/colombofilos');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(colombofilo: NewColombofilo): Observable<EntityResponseType> {
    return this.http.post<IColombofilo>(this.resourceUrl, colombofilo, { observe: 'response' });
  }

  update(colombofilo: IColombofilo): Observable<EntityResponseType> {
    return this.http.put<IColombofilo>(`${this.resourceUrl}/${this.getColombofiloIdentifier(colombofilo)}`, colombofilo, {
      observe: 'response',
    });
  }

  partialUpdate(colombofilo: PartialUpdateColombofilo): Observable<EntityResponseType> {
    return this.http.patch<IColombofilo>(`${this.resourceUrl}/${this.getColombofiloIdentifier(colombofilo)}`, colombofilo, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IColombofilo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IColombofilo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getColombofiloIdentifier(colombofilo: Pick<IColombofilo, 'id'>): number {
    return colombofilo.id;
  }

  compareColombofilo(o1: Pick<IColombofilo, 'id'> | null, o2: Pick<IColombofilo, 'id'> | null): boolean {
    return o1 && o2 ? this.getColombofiloIdentifier(o1) === this.getColombofiloIdentifier(o2) : o1 === o2;
  }

  addColombofiloToCollectionIfMissing<Type extends Pick<IColombofilo, 'id'>>(
    colombofiloCollection: Type[],
    ...colombofilosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const colombofilos: Type[] = colombofilosToCheck.filter(isPresent);
    if (colombofilos.length > 0) {
      const colombofiloCollectionIdentifiers = colombofiloCollection.map(
        colombofiloItem => this.getColombofiloIdentifier(colombofiloItem)!,
      );
      const colombofilosToAdd = colombofilos.filter(colombofiloItem => {
        const colombofiloIdentifier = this.getColombofiloIdentifier(colombofiloItem);
        if (colombofiloCollectionIdentifiers.includes(colombofiloIdentifier)) {
          return false;
        }
        colombofiloCollectionIdentifiers.push(colombofiloIdentifier);
        return true;
      });
      return [...colombofilosToAdd, ...colombofiloCollection];
    }
    return colombofiloCollection;
  }
}
