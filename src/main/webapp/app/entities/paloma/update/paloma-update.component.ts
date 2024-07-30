import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IColor } from 'app/entities/color/color.model';
import { ColorService } from 'app/entities/color/service/color.service';
import { IColombofilo } from 'app/entities/colombofilo/colombofilo.model';
import { ColombofiloService } from 'app/entities/colombofilo/service/colombofilo.service';
import { PalomaService } from '../service/paloma.service';
import { IPaloma } from '../paloma.model';
import { PalomaFormService, PalomaFormGroup } from './paloma-form.service';

@Component({
  standalone: true,
  selector: 'jhi-paloma-update',
  templateUrl: './paloma-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PalomaUpdateComponent implements OnInit {
  isSaving = false;
  paloma: IPaloma | null = null;

  colorsSharedCollection: IColor[] = [];
  colombofilosSharedCollection: IColombofilo[] = [];

  editForm: PalomaFormGroup = this.palomaFormService.createPalomaFormGroup();

  constructor(
    protected palomaService: PalomaService,
    protected palomaFormService: PalomaFormService,
    protected colorService: ColorService,
    protected colombofiloService: ColombofiloService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareColor = (o1: IColor | null, o2: IColor | null): boolean => this.colorService.compareColor(o1, o2);

  compareColombofilo = (o1: IColombofilo | null, o2: IColombofilo | null): boolean => this.colombofiloService.compareColombofilo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paloma }) => {
      this.paloma = paloma;
      if (paloma) {
        this.updateForm(paloma);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paloma = this.palomaFormService.getPaloma(this.editForm);
    if (paloma.id !== null) {
      this.subscribeToSaveResponse(this.palomaService.update(paloma));
    } else {
      this.subscribeToSaveResponse(this.palomaService.create(paloma));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaloma>>): void {
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

  protected updateForm(paloma: IPaloma): void {
    this.paloma = paloma;
    this.palomaFormService.resetForm(this.editForm, paloma);

    this.colorsSharedCollection = this.colorService.addColorToCollectionIfMissing<IColor>(this.colorsSharedCollection, paloma.color);
    this.colombofilosSharedCollection = this.colombofiloService.addColombofiloToCollectionIfMissing<IColombofilo>(
      this.colombofilosSharedCollection,
      paloma.colombofilo,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.colorService
      .query()
      .pipe(map((res: HttpResponse<IColor[]>) => res.body ?? []))
      .pipe(map((colors: IColor[]) => this.colorService.addColorToCollectionIfMissing<IColor>(colors, this.paloma?.color)))
      .subscribe((colors: IColor[]) => (this.colorsSharedCollection = colors));

    this.colombofiloService
      .query()
      .pipe(map((res: HttpResponse<IColombofilo[]>) => res.body ?? []))
      .pipe(
        map((colombofilos: IColombofilo[]) =>
          this.colombofiloService.addColombofiloToCollectionIfMissing<IColombofilo>(colombofilos, this.paloma?.colombofilo),
        ),
      )
      .subscribe((colombofilos: IColombofilo[]) => (this.colombofilosSharedCollection = colombofilos));
  }
}
