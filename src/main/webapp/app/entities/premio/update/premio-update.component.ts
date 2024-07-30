import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPaloma } from 'app/entities/paloma/paloma.model';
import { PalomaService } from 'app/entities/paloma/service/paloma.service';
import { IVuelo } from 'app/entities/vuelo/vuelo.model';
import { VueloService } from 'app/entities/vuelo/service/vuelo.service';
import { PremioService } from '../service/premio.service';
import { IPremio } from '../premio.model';
import { PremioFormService, PremioFormGroup } from './premio-form.service';

@Component({
  standalone: true,
  selector: 'jhi-premio-update',
  templateUrl: './premio-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PremioUpdateComponent implements OnInit {
  isSaving = false;
  premio: IPremio | null = null;

  palomasSharedCollection: IPaloma[] = [];
  vuelosSharedCollection: IVuelo[] = [];

  editForm: PremioFormGroup = this.premioFormService.createPremioFormGroup();

  constructor(
    protected premioService: PremioService,
    protected premioFormService: PremioFormService,
    protected palomaService: PalomaService,
    protected vueloService: VueloService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  comparePaloma = (o1: IPaloma | null, o2: IPaloma | null): boolean => this.palomaService.comparePaloma(o1, o2);

  compareVuelo = (o1: IVuelo | null, o2: IVuelo | null): boolean => this.vueloService.compareVuelo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ premio }) => {
      this.premio = premio;
      if (premio) {
        this.updateForm(premio);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const premio = this.premioFormService.getPremio(this.editForm);
    if (premio.id !== null) {
      this.subscribeToSaveResponse(this.premioService.update(premio));
    } else {
      this.subscribeToSaveResponse(this.premioService.create(premio));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPremio>>): void {
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

  protected updateForm(premio: IPremio): void {
    this.premio = premio;
    this.premioFormService.resetForm(this.editForm, premio);

    this.palomasSharedCollection = this.palomaService.addPalomaToCollectionIfMissing<IPaloma>(this.palomasSharedCollection, premio.paloma);
    this.vuelosSharedCollection = this.vueloService.addVueloToCollectionIfMissing<IVuelo>(this.vuelosSharedCollection, premio.vuelo);
  }

  protected loadRelationshipsOptions(): void {
    this.palomaService
      .query()
      .pipe(map((res: HttpResponse<IPaloma[]>) => res.body ?? []))
      .pipe(map((palomas: IPaloma[]) => this.palomaService.addPalomaToCollectionIfMissing<IPaloma>(palomas, this.premio?.paloma)))
      .subscribe((palomas: IPaloma[]) => (this.palomasSharedCollection = palomas));

    this.vueloService
      .query()
      .pipe(map((res: HttpResponse<IVuelo[]>) => res.body ?? []))
      .pipe(map((vuelos: IVuelo[]) => this.vueloService.addVueloToCollectionIfMissing<IVuelo>(vuelos, this.premio?.vuelo)))
      .subscribe((vuelos: IVuelo[]) => (this.vuelosSharedCollection = vuelos));
  }
}
