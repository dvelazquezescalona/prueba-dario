import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IColombofiloVuelo } from '../colombofilo-vuelo.model';

@Component({
  standalone: true,
  selector: 'jhi-colombofilo-vuelo-detail',
  templateUrl: './colombofilo-vuelo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ColombofiloVueloDetailComponent {
  @Input() colombofiloVuelo: IColombofiloVuelo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
