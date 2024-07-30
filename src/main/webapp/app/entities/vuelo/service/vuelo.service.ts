import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVuelo, NewVuelo } from '../vuelo.model';

export type PartialUpdateVuelo = Partial<IVuelo> & Pick<IVuelo, 'id'>;

type RestOf<T extends IVuelo | NewVuelo> = Omit<T, 'fecha'> & {
  fecha?: string | null;
};

export type RestVuelo = RestOf<IVuelo>;

export type NewRestVuelo = RestOf<NewVuelo>;

export type PartialUpdateRestVuelo = RestOf<PartialUpdateVuelo>;

export type EntityResponseType = HttpResponse<IVuelo>;
export type EntityArrayResponseType = HttpResponse<IVuelo[]>;

@Injectable({ providedIn: 'root' })
export class VueloService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vuelos');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(vuelo: NewVuelo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vuelo);
    return this.http.post<RestVuelo>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(vuelo: IVuelo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vuelo);
    return this.http
      .put<RestVuelo>(`${this.resourceUrl}/${this.getVueloIdentifier(vuelo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(vuelo: PartialUpdateVuelo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vuelo);
    return this.http
      .patch<RestVuelo>(`${this.resourceUrl}/${this.getVueloIdentifier(vuelo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestVuelo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestVuelo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVueloIdentifier(vuelo: Pick<IVuelo, 'id'>): number {
    return vuelo.id;
  }

  compareVuelo(o1: Pick<IVuelo, 'id'> | null, o2: Pick<IVuelo, 'id'> | null): boolean {
    return o1 && o2 ? this.getVueloIdentifier(o1) === this.getVueloIdentifier(o2) : o1 === o2;
  }

  addVueloToCollectionIfMissing<Type extends Pick<IVuelo, 'id'>>(
    vueloCollection: Type[],
    ...vuelosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const vuelos: Type[] = vuelosToCheck.filter(isPresent);
    if (vuelos.length > 0) {
      const vueloCollectionIdentifiers = vueloCollection.map(vueloItem => this.getVueloIdentifier(vueloItem)!);
      const vuelosToAdd = vuelos.filter(vueloItem => {
        const vueloIdentifier = this.getVueloIdentifier(vueloItem);
        if (vueloCollectionIdentifiers.includes(vueloIdentifier)) {
          return false;
        }
        vueloCollectionIdentifiers.push(vueloIdentifier);
        return true;
      });
      return [...vuelosToAdd, ...vueloCollection];
    }
    return vueloCollection;
  }

  protected convertDateFromClient<T extends IVuelo | NewVuelo | PartialUpdateVuelo>(vuelo: T): RestOf<T> {
    return {
      ...vuelo,
      fecha: vuelo.fecha?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restVuelo: RestVuelo): IVuelo {
    return {
      ...restVuelo,
      fecha: restVuelo.fecha ? dayjs(restVuelo.fecha) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestVuelo>): HttpResponse<IVuelo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestVuelo[]>): HttpResponse<IVuelo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
