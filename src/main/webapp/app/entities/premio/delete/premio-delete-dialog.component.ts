import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPremio } from '../premio.model';
import { PremioService } from '../service/premio.service';

@Component({
  standalone: true,
  templateUrl: './premio-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PremioDeleteDialogComponent {
  premio?: IPremio;

  constructor(
    protected premioService: PremioService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.premioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
