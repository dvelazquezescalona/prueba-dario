import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISociedad } from 'app/entities/sociedad/sociedad.model';
import { SociedadService } from 'app/entities/sociedad/service/sociedad.service';
import { IParadero } from '../paradero.model';
import { ParaderoService } from '../service/paradero.service';
import { ParaderoFormService, ParaderoFormGroup } from './paradero-form.service';

@Component({
  standalone: true,
  selector: 'jhi-paradero-update',
  templateUrl: './paradero-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ParaderoUpdateComponent implements OnInit {
  isSaving = false;
  paradero: IParadero | null = null;

  sociedadsSharedCollection: ISociedad[] = [];

  editForm: ParaderoFormGroup = this.paraderoFormService.createParaderoFormGroup();

  constructor(
    protected paraderoService: ParaderoService,
    protected paraderoFormService: ParaderoFormService,
    protected sociedadService: SociedadService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareSociedad = (o1: ISociedad | null, o2: ISociedad | null): boolean => this.sociedadService.compareSociedad(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paradero }) => {
      this.paradero = paradero;
      if (paradero) {
        this.updateForm(paradero);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paradero = this.paraderoFormService.getParadero(this.editForm);
    if (paradero.id !== null) {
      this.subscribeToSaveResponse(this.paraderoService.update(paradero));
    } else {
      this.subscribeToSaveResponse(this.paraderoService.create(paradero));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParadero>>): void {
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

  protected updateForm(paradero: IParadero): void {
    this.paradero = paradero;
    this.paraderoFormService.resetForm(this.editForm, paradero);

    this.sociedadsSharedCollection = this.sociedadService.addSociedadToCollectionIfMissing<ISociedad>(
      this.sociedadsSharedCollection,
      paradero.sociedad,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.sociedadService
      .query()
      .pipe(map((res: HttpResponse<ISociedad[]>) => res.body ?? []))
      .pipe(
        map((sociedads: ISociedad[]) =>
          this.sociedadService.addSociedadToCollectionIfMissing<ISociedad>(sociedads, this.paradero?.sociedad),
        ),
      )
      .subscribe((sociedads: ISociedad[]) => (this.sociedadsSharedCollection = sociedads));
  }
}
