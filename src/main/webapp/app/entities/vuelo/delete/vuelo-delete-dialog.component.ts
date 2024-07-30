import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IVuelo } from '../vuelo.model';
import { VueloService } from '../service/vuelo.service';

@Component({
  standalone: true,
  templateUrl: './vuelo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class VueloDeleteDialogComponent {
  vuelo?: IVuelo;

  constructor(
    protected vueloService: VueloService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vueloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
