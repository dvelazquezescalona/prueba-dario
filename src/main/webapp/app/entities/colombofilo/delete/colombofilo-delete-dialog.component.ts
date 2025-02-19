import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IColombofilo } from '../colombofilo.model';
import { ColombofiloService } from '../service/colombofilo.service';

@Component({
  standalone: true,
  templateUrl: './colombofilo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ColombofiloDeleteDialogComponent {
  colombofilo?: IColombofilo;

  constructor(
    protected colombofiloService: ColombofiloService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.colombofiloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
