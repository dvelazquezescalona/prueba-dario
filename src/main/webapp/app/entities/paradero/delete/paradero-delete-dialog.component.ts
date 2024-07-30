import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IParadero } from '../paradero.model';
import { ParaderoService } from '../service/paradero.service';

@Component({
  standalone: true,
  templateUrl: './paradero-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ParaderoDeleteDialogComponent {
  paradero?: IParadero;

  constructor(
    protected paraderoService: ParaderoService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paraderoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
