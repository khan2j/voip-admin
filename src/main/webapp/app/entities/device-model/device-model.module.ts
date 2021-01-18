import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipadminSharedModule } from 'app/shared/shared.module';
import { DeviceModelComponent } from './device-model.component';
import { DeviceModelDetailComponent } from './device-model-detail.component';
import { DeviceModelUpdateComponent } from './device-model-update.component';
import { DeviceModelDeleteDialogComponent } from './device-model-delete-dialog.component';
import { deviceModelRoute } from './device-model.route';

@NgModule({
  imports: [VoipadminSharedModule, RouterModule.forChild(deviceModelRoute)],
  declarations: [DeviceModelComponent, DeviceModelDetailComponent, DeviceModelUpdateComponent, DeviceModelDeleteDialogComponent],
  entryComponents: [DeviceModelDeleteDialogComponent],
})
export class VoipadminDeviceModelModule {}
