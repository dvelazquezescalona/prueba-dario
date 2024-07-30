import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPaloma } from '../paloma.model';
import { PalomaService } from '../service/paloma.service';

@Component({
  standalone: true,
  templateUrl: './paloma-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PalomaDeleteDialogComponent {
  paloma?: IPaloma;

  constructor(
    protected palomaService: PalomaService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.palomaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
