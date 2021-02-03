import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVendor } from 'app/shared/model/vendor.model';

@Component({
  templateUrl: './device-model-vendor-change-dialog.component.html',
})
export class DeviceModelVendorChangeDialogComponent {
  oldValue?: IVendor;
  newValue?: IVendor;

  constructor(public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.close(this.oldValue);
  }

  confirmVendorChange(): void {
    this.activeModal.close(this.newValue);
  }
}
