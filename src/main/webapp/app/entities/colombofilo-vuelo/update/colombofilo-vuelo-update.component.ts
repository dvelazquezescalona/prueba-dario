import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IColombofilo } from 'app/entities/colombofilo/colombofilo.model';
import { ColombofiloService } from 'app/entities/colombofilo/service/colombofilo.service';
import { IVuelo } from 'app/entities/vuelo/vuelo.model';
import { VueloService } from 'app/entities/vuelo/service/vuelo.service';
import { ColombofiloVueloService } from '../service/colombofilo-vuelo.service';
import { IColombofiloVuelo } from '../colombofilo-vuelo.model';
import { ColombofiloVueloFormService, ColombofiloVueloFormGroup } from './colombofilo-vuelo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-colombofilo-vuelo-update',
  templateUrl: './colombofilo-vuelo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ColombofiloVueloUpdateComponent implements OnInit {
  isSaving = false;
  colombofiloVuelo: IColombofiloVuelo | null = null;

  colombofilosSharedCollection: IColombofilo[] = [];
  vuelosSharedCollection: IVuelo[] = [];

  editForm: ColombofiloVueloFormGroup = this.colombofiloVueloFormService.createColombofiloVueloFormGroup();

  constructor(
    protected colombofiloVueloService: ColombofiloVueloService,
    protected colombofiloVueloFormService: ColombofiloVueloFormService,
    protected colombofiloService: ColombofiloService,
    protected vueloService: VueloService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareColombofilo = (o1: IColombofilo | null, o2: IColombofilo | null): boolean => this.colombofiloService.compareColombofilo(o1, o2);

  compareVuelo = (o1: IVuelo | null, o2: IVuelo | null): boolean => this.vueloService.compareVuelo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ colombofiloVuelo }) => {
      this.colombofiloVuelo = colombofiloVuelo;
      if (colombofiloVuelo) {
        this.updateForm(colombofiloVuelo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const colombofiloVuelo = this.colombofiloVueloFormService.getColombofiloVuelo(this.editForm);
    if (colombofiloVuelo.id !== null) {
      this.subscribeToSaveResponse(this.colombofiloVueloService.update(colombofiloVuelo));
    } else {
      this.subscribeToSaveResponse(this.colombofiloVueloService.create(colombofiloVuelo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IColombofiloVuelo>>): void {
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

  protected updateForm(colombofiloVuelo: IColombofiloVuelo): void {
    this.colombofiloVuelo = colombofiloVuelo;
    this.colombofiloVueloFormService.resetForm(this.editForm, colombofiloVuelo);

    this.colombofilosSharedCollection = this.colombofiloService.addColombofiloToCollectionIfMissing<IColombofilo>(
      this.colombofilosSharedCollection,
      colombofiloVuelo.colombofilo,
    );
    this.vuelosSharedCollection = this.vueloService.addVueloToCollectionIfMissing<IVuelo>(
      this.vuelosSharedCollection,
      colombofiloVuelo.vuelo,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.colombofiloService
      .query()
      .pipe(map((res: HttpResponse<IColombofilo[]>) => res.body ?? []))
      .pipe(
        map((colombofilos: IColombofilo[]) =>
          this.colombofiloService.addColombofiloToCollectionIfMissing<IColombofilo>(colombofilos, this.colombofiloVuelo?.colombofilo),
        ),
      )
      .subscribe((colombofilos: IColombofilo[]) => (this.colombofilosSharedCollection = colombofilos));

    this.vueloService
      .query()
      .pipe(map((res: HttpResponse<IVuelo[]>) => res.body ?? []))
      .pipe(map((vuelos: IVuelo[]) => this.vueloService.addVueloToCollectionIfMissing<IVuelo>(vuelos, this.colombofiloVuelo?.vuelo)))
      .subscribe((vuelos: IVuelo[]) => (this.vuelosSharedCollection = vuelos));
  }
}
