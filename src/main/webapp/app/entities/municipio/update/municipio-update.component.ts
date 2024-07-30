import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProvincia } from 'app/entities/provincia/provincia.model';
import { ProvinciaService } from 'app/entities/provincia/service/provincia.service';
import { IMunicipio } from '../municipio.model';
import { MunicipioService } from '../service/municipio.service';
import { MunicipioFormService, MunicipioFormGroup } from './municipio-form.service';

@Component({
  standalone: true,
  selector: 'jhi-municipio-update',
  templateUrl: './municipio-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MunicipioUpdateComponent implements OnInit {
  isSaving = false;
  municipio: IMunicipio | null = null;

  provinciasSharedCollection: IProvincia[] = [];

  editForm: MunicipioFormGroup = this.municipioFormService.createMunicipioFormGroup();

  constructor(
    protected municipioService: MunicipioService,
    protected municipioFormService: MunicipioFormService,
    protected provinciaService: ProvinciaService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareProvincia = (o1: IProvincia | null, o2: IProvincia | null): boolean => this.provinciaService.compareProvincia(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ municipio }) => {
      this.municipio = municipio;
      if (municipio) {
        this.updateForm(municipio);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const municipio = this.municipioFormService.getMunicipio(this.editForm);
    if (municipio.id !== null) {
      this.subscribeToSaveResponse(this.municipioService.update(municipio));
    } else {
      this.subscribeToSaveResponse(this.municipioService.create(municipio));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMunicipio>>): void {
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

  protected updateForm(municipio: IMunicipio): void {
    this.municipio = municipio;
    this.municipioFormService.resetForm(this.editForm, municipio);

    this.provinciasSharedCollection = this.provinciaService.addProvinciaToCollectionIfMissing<IProvincia>(
      this.provinciasSharedCollection,
      municipio.provincia,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.provinciaService
      .query()
      .pipe(map((res: HttpResponse<IProvincia[]>) => res.body ?? []))
      .pipe(
        map((provincias: IProvincia[]) =>
          this.provinciaService.addProvinciaToCollectionIfMissing<IProvincia>(provincias, this.municipio?.provincia),
        ),
      )
      .subscribe((provincias: IProvincia[]) => (this.provinciasSharedCollection = provincias));
  }
}
