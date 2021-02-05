import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeviceModel } from 'app/shared/model/device-model.model';

@Component({
  templateUrl: './device-model-change-dialog.component.html',
})
export class DeviceModelChangeDialogComponent {
  oldValue?: IDeviceModel;
  newValue?: IDeviceModel;

  constructor(public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.close(this.oldValue);
  }

  confirmModelChange(): void {
    this.activeModal.close(this.newValue);
  }
}
