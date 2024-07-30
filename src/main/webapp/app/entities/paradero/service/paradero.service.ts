import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParadero, NewParadero } from '../paradero.model';

export type PartialUpdateParadero = Partial<IParadero> & Pick<IParadero, 'id'>;

export type EntityResponseType = HttpResponse<IParadero>;
export type EntityArrayResponseType = HttpResponse<IParadero[]>;

@Injectable({ providedIn: 'root' })
export class ParaderoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/paraderos');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(paradero: NewParadero): Observable<EntityResponseType> {
    return this.http.post<IParadero>(this.resourceUrl, paradero, { observe: 'response' });
  }

  update(paradero: IParadero): Observable<EntityResponseType> {
    return this.http.put<IParadero>(`${this.resourceUrl}/${this.getParaderoIdentifier(paradero)}`, paradero, { observe: 'response' });
  }

  partialUpdate(paradero: PartialUpdateParadero): Observable<EntityResponseType> {
    return this.http.patch<IParadero>(`${this.resourceUrl}/${this.getParaderoIdentifier(paradero)}`, paradero, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParadero>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParadero[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getParaderoIdentifier(paradero: Pick<IParadero, 'id'>): number {
    return paradero.id;
  }

  compareParadero(o1: Pick<IParadero, 'id'> | null, o2: Pick<IParadero, 'id'> | null): boolean {
    return o1 && o2 ? this.getParaderoIdentifier(o1) === this.getParaderoIdentifier(o2) : o1 === o2;
  }

  addParaderoToCollectionIfMissing<Type extends Pick<IParadero, 'id'>>(
    paraderoCollection: Type[],
    ...paraderosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const paraderos: Type[] = paraderosToCheck.filter(isPresent);
    if (paraderos.length > 0) {
      const paraderoCollectionIdentifiers = paraderoCollection.map(paraderoItem => this.getParaderoIdentifier(paraderoItem)!);
      const paraderosToAdd = paraderos.filter(paraderoItem => {
        const paraderoIdentifier = this.getParaderoIdentifier(paraderoItem);
        if (paraderoCollectionIdentifiers.includes(paraderoIdentifier)) {
          return false;
        }
        paraderoCollectionIdentifiers.push(paraderoIdentifier);
        return true;
      });
      return [...paraderosToAdd, ...paraderoCollection];
    }
    return paraderoCollection;
  }
}
