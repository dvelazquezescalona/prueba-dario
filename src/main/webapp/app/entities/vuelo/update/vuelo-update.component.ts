import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IParadero } from 'app/entities/paradero/paradero.model';
import { ParaderoService } from 'app/entities/paradero/service/paradero.service';
import { IVuelo } from '../vuelo.model';
import { VueloService } from '../service/vuelo.service';
import { VueloFormService, VueloFormGroup } from './vuelo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-vuelo-update',
  templateUrl: './vuelo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VueloUpdateComponent implements OnInit {
  isSaving = false;
  vuelo: IVuelo | null = null;

  paraderosSharedCollection: IParadero[] = [];

  editForm: VueloFormGroup = this.vueloFormService.createVueloFormGroup();

  constructor(
    protected vueloService: VueloService,
    protected vueloFormService: VueloFormService,
    protected paraderoService: ParaderoService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareParadero = (o1: IParadero | null, o2: IParadero | null): boolean => this.paraderoService.compareParadero(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vuelo }) => {
      this.vuelo = vuelo;
      if (vuelo) {
        this.updateForm(vuelo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vuelo = this.vueloFormService.getVuelo(this.editForm);
    if (vuelo.id !== null) {
      this.subscribeToSaveResponse(this.vueloService.update(vuelo));
    } else {
      this.subscribeToSaveResponse(this.vueloService.create(vuelo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVuelo>>): void {
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

  protected updateForm(vuelo: IVuelo): void {
    this.vuelo = vuelo;
    this.vueloFormService.resetForm(this.editForm, vuelo);

    this.paraderosSharedCollection = this.paraderoService.addParaderoToCollectionIfMissing<IParadero>(
      this.paraderosSharedCollection,
      vuelo.paradero,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.paraderoService
      .query()
      .pipe(map((res: HttpResponse<IParadero[]>) => res.body ?? []))
      .pipe(
        map((paraderos: IParadero[]) => this.paraderoService.addParaderoToCollectionIfMissing<IParadero>(paraderos, this.vuelo?.paradero)),
      )
      .subscribe((paraderos: IParadero[]) => (this.paraderosSharedCollection = paraderos));
  }
}
