import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipadminSharedModule } from 'app/shared/shared.module';
import { DeviceModelComponent } from './device-model.component';
import { DeviceModelDetailComponent } from './device-model-detail.component';
import { DeviceModelUpdateComponent } from './device-model-update.component';
import { DeviceModelDeleteDialogComponent } from './device-model-delete-dialog.component';
import { deviceModelRoute } from './device-model.route';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { NgSelectModule } from '@ng-select/ng-select';
import { MatDialogModule } from '@angular/material/dialog';
import { DeviceModelVendorChangeDialogComponent } from 'app/entities/device-model/device-model-vendor-change-dialog.component';

@NgModule({
  imports: [VoipadminSharedModule, RouterModule.forChild(deviceModelRoute), MatSlideToggleModule, NgSelectModule, MatDialogModule],
  declarations: [
    DeviceModelComponent,
    DeviceModelDetailComponent,
    DeviceModelUpdateComponent,
    DeviceModelDeleteDialogComponent,
    DeviceModelVendorChangeDialogComponent,
  ],
  entryComponents: [DeviceModelDeleteDialogComponent],
})
export class VoipadminDeviceModelModule {}
