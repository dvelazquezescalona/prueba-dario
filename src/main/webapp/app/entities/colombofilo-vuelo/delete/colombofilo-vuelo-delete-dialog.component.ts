import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IColombofiloVuelo } from '../colombofilo-vuelo.model';
import { ColombofiloVueloService } from '../service/colombofilo-vuelo.service';

@Component({
  standalone: true,
  templateUrl: './colombofilo-vuelo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ColombofiloVueloDeleteDialogComponent {
  colombofiloVuelo?: IColombofiloVuelo;

  constructor(
    protected colombofiloVueloService: ColombofiloVueloService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.colombofiloVueloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
