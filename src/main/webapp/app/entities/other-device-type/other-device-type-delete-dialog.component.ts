import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOtherDeviceType } from 'app/shared/model/other-device-type.model';
import { OtherDeviceTypeService } from './other-device-type.service';

@Component({
  templateUrl: './other-device-type-delete-dialog.component.html',
})
export class OtherDeviceTypeDeleteDialogComponent {
  otherDeviceType?: IOtherDeviceType;

  constructor(
    protected otherDeviceTypeService: OtherDeviceTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.otherDeviceTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('otherDeviceTypeListModification');
      this.activeModal.close();
    });
  }
}
