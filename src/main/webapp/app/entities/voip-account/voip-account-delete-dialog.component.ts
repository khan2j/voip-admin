import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVoipAccount } from 'app/shared/model/voip-account.model';
import { VoipAccountService } from './voip-account.service';

@Component({
  templateUrl: './voip-account-delete-dialog.component.html',
})
export class VoipAccountDeleteDialogComponent {
  voipAccount?: IVoipAccount;

  constructor(
    protected voipAccountService: VoipAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voipAccountService.delete(id).subscribe(() => {
      this.eventManager.broadcast('voipAccountListModification');
      this.activeModal.close();
    });
  }
}
