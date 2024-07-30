import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMunicipio } from 'app/entities/municipio/municipio.model';
import { MunicipioService } from 'app/entities/municipio/service/municipio.service';
import { ISociedad } from '../sociedad.model';
import { SociedadService } from '../service/sociedad.service';
import { SociedadFormService, SociedadFormGroup } from './sociedad-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sociedad-update',
  templateUrl: './sociedad-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SociedadUpdateComponent implements OnInit {
  isSaving = false;
  sociedad: ISociedad | null = null;

  municipiosSharedCollection: IMunicipio[] = [];

  editForm: SociedadFormGroup = this.sociedadFormService.createSociedadFormGroup();

  constructor(
    protected sociedadService: SociedadService,
    protected sociedadFormService: SociedadFormService,
    protected municipioService: MunicipioService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareMunicipio = (o1: IMunicipio | null, o2: IMunicipio | null): boolean => this.municipioService.compareMunicipio(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sociedad }) => {
      this.sociedad = sociedad;
      if (sociedad) {
        this.updateForm(sociedad);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sociedad = this.sociedadFormService.getSociedad(this.editForm);
    if (sociedad.id !== null) {
      this.subscribeToSaveResponse(this.sociedadService.update(sociedad));
    } else {
      this.subscribeToSaveResponse(this.sociedadService.create(sociedad));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISociedad>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(sociedad: ISociedad): void {
    this.sociedad = sociedad;
    this.sociedadFormService.resetForm(this.editForm, sociedad);

    this.municipiosSharedCollection = this.municipioService.addMunicipioToCollectionIfMissing<IMunicipio>(
      this.municipiosSharedCollection,
      sociedad.municipio,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.municipioService
      .query()
      .pipe(map((res: HttpResponse<IMunicipio[]>) => res.body ?? []))
      .pipe(
        map((municipios: IMunicipio[]) =>
          this.municipioService.addMunicipioToCollectionIfMissing<IMunicipio>(municipios, this.sociedad?.municipio),
        ),
      )
      .subscribe((municipios: IMunicipio[]) => (this.municipiosSharedCollection = municipios));
  }
}
