import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISociedad } from 'app/entities/sociedad/sociedad.model';
import { SociedadService } from 'app/entities/sociedad/service/sociedad.service';
import { IColombofilo } from '../colombofilo.model';
import { ColombofiloService } from '../service/colombofilo.service';
import { ColombofiloFormService, ColombofiloFormGroup } from './colombofilo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-colombofilo-update',
  templateUrl: './colombofilo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ColombofiloUpdateComponent implements OnInit {
  isSaving = false;
  colombofilo: IColombofilo | null = null;

  sociedadsSharedCollection: ISociedad[] = [];

  editForm: ColombofiloFormGroup = this.colombofiloFormService.createColombofiloFormGroup();

  constructor(
    protected colombofiloService: ColombofiloService,
    protected colombofiloFormService: ColombofiloFormService,
    protected sociedadService: SociedadService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareSociedad = (o1: ISociedad | null, o2: ISociedad | null): boolean => this.sociedadService.compareSociedad(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ colombofilo }) => {
      this.colombofilo = colombofilo;
      if (colombofilo) {
        this.updateForm(colombofilo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const colombofilo = this.colombofiloFormService.getColombofilo(this.editForm);
    if (colombofilo.id !== null) {
      this.subscribeToSaveResponse(this.colombofiloService.update(colombofilo));
    } else {
      this.subscribeToSaveResponse(this.colombofiloService.create(colombofilo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IColombofilo>>): void {
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

  protected updateForm(colombofilo: IColombofilo): void {
    this.colombofilo = colombofilo;
    this.colombofiloFormService.resetForm(this.editForm, colombofilo);

    this.sociedadsSharedCollection = this.sociedadService.addSociedadToCollectionIfMissing<ISociedad>(
      this.sociedadsSharedCollection,
      colombofilo.sociedad,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.sociedadService
      .query()
      .pipe(map((res: HttpResponse<ISociedad[]>) => res.body ?? []))
      .pipe(
        map((sociedads: ISociedad[]) =>
          this.sociedadService.addSociedadToCollectionIfMissing<ISociedad>(sociedads, this.colombofilo?.sociedad),
        ),
      )
      .subscribe((sociedads: ISociedad[]) => (this.sociedadsSharedCollection = sociedads));
  }
}
