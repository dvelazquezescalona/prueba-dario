import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMunicipio } from '../municipio.model';
import { MunicipioService } from '../service/municipio.service';

@Component({
  standalone: true,
  templateUrl: './municipio-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MunicipioDeleteDialogComponent {
  municipio?: IMunicipio;

  constructor(
    protected municipioService: MunicipioService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.municipioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
