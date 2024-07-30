import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISociedad } from '../sociedad.model';
import { SociedadService } from '../service/sociedad.service';

@Component({
  standalone: true,
  templateUrl: './sociedad-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SociedadDeleteDialogComponent {
  sociedad?: ISociedad;

  constructor(
    protected sociedadService: SociedadService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sociedadService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
