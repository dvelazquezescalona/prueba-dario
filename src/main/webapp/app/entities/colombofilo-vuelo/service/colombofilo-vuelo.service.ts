import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IColombofiloVuelo, NewColombofiloVuelo } from '../colombofilo-vuelo.model';

export type PartialUpdateColombofiloVuelo = Partial<IColombofiloVuelo> & Pick<IColombofiloVuelo, 'id'>;

export type EntityResponseType = HttpResponse<IColombofiloVuelo>;
export type EntityArrayResponseType = HttpResponse<IColombofiloVuelo[]>;

@Injectable({ providedIn: 'root' })
export class ColombofiloVueloService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/colombofilo-vuelos');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(colombofiloVuelo: NewColombofiloVuelo): Observable<EntityResponseType> {
    return this.http.post<IColombofiloVuelo>(this.resourceUrl, colombofiloVuelo, { observe: 'response' });
  }

  update(colombofiloVuelo: IColombofiloVuelo): Observable<EntityResponseType> {
    return this.http.put<IColombofiloVuelo>(
      `${this.resourceUrl}/${this.getColombofiloVueloIdentifier(colombofiloVuelo)}`,
      colombofiloVuelo,
      { observe: 'response' },
    );
  }

  partialUpdate(colombofiloVuelo: PartialUpdateColombofiloVuelo): Observable<EntityResponseType> {
    return this.http.patch<IColombofiloVuelo>(
      `${this.resourceUrl}/${this.getColombofiloVueloIdentifier(colombofiloVuelo)}`,
      colombofiloVuelo,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IColombofiloVuelo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IColombofiloVuelo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getColombofiloVueloIdentifier(colombofiloVuelo: Pick<IColombofiloVuelo, 'id'>): number {
    return colombofiloVuelo.id;
  }

  compareColombofiloVuelo(o1: Pick<IColombofiloVuelo, 'id'> | null, o2: Pick<IColombofiloVuelo, 'id'> | null): boolean {
    return o1 && o2 ? this.getColombofiloVueloIdentifier(o1) === this.getColombofiloVueloIdentifier(o2) : o1 === o2;
  }

  addColombofiloVueloToCollectionIfMissing<Type extends Pick<IColombofiloVuelo, 'id'>>(
    colombofiloVueloCollection: Type[],
    ...colombofiloVuelosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const colombofiloVuelos: Type[] = colombofiloVuelosToCheck.filter(isPresent);
    if (colombofiloVuelos.length > 0) {
      const colombofiloVueloCollectionIdentifiers = colombofiloVueloCollection.map(
        colombofiloVueloItem => this.getColombofiloVueloIdentifier(colombofiloVueloItem)!,
      );
      const colombofiloVuelosToAdd = colombofiloVuelos.filter(colombofiloVueloItem => {
        const colombofiloVueloIdentifier = this.getColombofiloVueloIdentifier(colombofiloVueloItem);
        if (colombofiloVueloCollectionIdentifiers.includes(colombofiloVueloIdentifier)) {
          return false;
        }
        colombofiloVueloCollectionIdentifiers.push(colombofiloVueloIdentifier);
        return true;
      });
      return [...colombofiloVuelosToAdd, ...colombofiloVueloCollection];
    }
    return colombofiloVueloCollection;
  }
}
