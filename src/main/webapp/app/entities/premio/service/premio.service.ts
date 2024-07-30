import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPremio, NewPremio } from '../premio.model';

export type PartialUpdatePremio = Partial<IPremio> & Pick<IPremio, 'id'>;

type RestOf<T extends IPremio | NewPremio> = Omit<T, 'fechaArribo'> & {
  fechaArribo?: string | null;
};

export type RestPremio = RestOf<IPremio>;

export type NewRestPremio = RestOf<NewPremio>;

export type PartialUpdateRestPremio = RestOf<PartialUpdatePremio>;

export type EntityResponseType = HttpResponse<IPremio>;
export type EntityArrayResponseType = HttpResponse<IPremio[]>;

@Injectable({ providedIn: 'root' })
export class PremioService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/premios');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(premio: NewPremio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(premio);
    return this.http
      .post<RestPremio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(premio: IPremio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(premio);
    return this.http
      .put<RestPremio>(`${this.resourceUrl}/${this.getPremioIdentifier(premio)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(premio: PartialUpdatePremio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(premio);
    return this.http
      .patch<RestPremio>(`${this.resourceUrl}/${this.getPremioIdentifier(premio)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPremio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPremio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPremioIdentifier(premio: Pick<IPremio, 'id'>): number {
    return premio.id;
  }

  comparePremio(o1: Pick<IPremio, 'id'> | null, o2: Pick<IPremio, 'id'> | null): boolean {
    return o1 && o2 ? this.getPremioIdentifier(o1) === this.getPremioIdentifier(o2) : o1 === o2;
  }

  addPremioToCollectionIfMissing<Type extends Pick<IPremio, 'id'>>(
    premioCollection: Type[],
    ...premiosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const premios: Type[] = premiosToCheck.filter(isPresent);
    if (premios.length > 0) {
      const premioCollectionIdentifiers = premioCollection.map(premioItem => this.getPremioIdentifier(premioItem)!);
      const premiosToAdd = premios.filter(premioItem => {
        const premioIdentifier = this.getPremioIdentifier(premioItem);
        if (premioCollectionIdentifiers.includes(premioIdentifier)) {
          return false;
        }
        premioCollectionIdentifiers.push(premioIdentifier);
        return true;
      });
      return [...premiosToAdd, ...premioCollection];
    }
    return premioCollection;
  }

  protected convertDateFromClient<T extends IPremio | NewPremio | PartialUpdatePremio>(premio: T): RestOf<T> {
    return {
      ...premio,
      fechaArribo: premio.fechaArribo?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPremio: RestPremio): IPremio {
    return {
      ...restPremio,
      fechaArribo: restPremio.fechaArribo ? dayjs(restPremio.fechaArribo) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPremio>): HttpResponse<IPremio> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPremio[]>): HttpResponse<IPremio[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
