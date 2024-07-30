import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMunicipio, NewMunicipio } from '../municipio.model';

export type PartialUpdateMunicipio = Partial<IMunicipio> & Pick<IMunicipio, 'id'>;

export type EntityResponseType = HttpResponse<IMunicipio>;
export type EntityArrayResponseType = HttpResponse<IMunicipio[]>;

@Injectable({ providedIn: 'root' })
export class MunicipioService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/municipios');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(municipio: NewMunicipio): Observable<EntityResponseType> {
    return this.http.post<IMunicipio>(this.resourceUrl, municipio, { observe: 'response' });
  }

  update(municipio: IMunicipio): Observable<EntityResponseType> {
    return this.http.put<IMunicipio>(`${this.resourceUrl}/${this.getMunicipioIdentifier(municipio)}`, municipio, { observe: 'response' });
  }

  partialUpdate(municipio: PartialUpdateMunicipio): Observable<EntityResponseType> {
    return this.http.patch<IMunicipio>(`${this.resourceUrl}/${this.getMunicipioIdentifier(municipio)}`, municipio, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMunicipio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMunicipio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMunicipioIdentifier(municipio: Pick<IMunicipio, 'id'>): number {
    return municipio.id;
  }

  compareMunicipio(o1: Pick<IMunicipio, 'id'> | null, o2: Pick<IMunicipio, 'id'> | null): boolean {
    return o1 && o2 ? this.getMunicipioIdentifier(o1) === this.getMunicipioIdentifier(o2) : o1 === o2;
  }

  addMunicipioToCollectionIfMissing<Type extends Pick<IMunicipio, 'id'>>(
    municipioCollection: Type[],
    ...municipiosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const municipios: Type[] = municipiosToCheck.filter(isPresent);
    if (municipios.length > 0) {
      const municipioCollectionIdentifiers = municipioCollection.map(municipioItem => this.getMunicipioIdentifier(municipioItem)!);
      const municipiosToAdd = municipios.filter(municipioItem => {
        const municipioIdentifier = this.getMunicipioIdentifier(municipioItem);
        if (municipioCollectionIdentifiers.includes(municipioIdentifier)) {
          return false;
        }
        municipioCollectionIdentifiers.push(municipioIdentifier);
        return true;
      });
      return [...municipiosToAdd, ...municipioCollection];
    }
    return municipioCollection;
  }
}
