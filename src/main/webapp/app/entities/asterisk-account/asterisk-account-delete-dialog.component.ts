import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAsteriskAccount } from 'app/shared/model/asterisk-account.model';
import { AsteriskAccountService } from './asterisk-account.service';

@Component({
  templateUrl: './asterisk-account-delete-dialog.component.html',
})
export class AsteriskAccountDeleteDialogComponent {
  asteriskAccount?: IAsteriskAccount;

  constructor(
    protected asteriskAccountService: AsteriskAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.asteriskAccountService.delete(id).subscribe(() => {
      this.eventManager.broadcast('asteriskAccountListModification');
      this.activeModal.close();
    });
  }
}
