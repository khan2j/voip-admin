import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipadminSharedModule } from 'app/shared/shared.module';
import { OtherDeviceTypeComponent } from './other-device-type.component';
import { OtherDeviceTypeDetailComponent } from './other-device-type-detail.component';
import { OtherDeviceTypeUpdateComponent } from './other-device-type-update.component';
import { OtherDeviceTypeDeleteDialogComponent } from './other-device-type-delete-dialog.component';
import { otherDeviceTypeRoute } from './other-device-type.route';

@NgModule({
  imports: [VoipadminSharedModule, RouterModule.forChild(otherDeviceTypeRoute)],
  declarations: [
    OtherDeviceTypeComponent,
    OtherDeviceTypeDetailComponent,
    OtherDeviceTypeUpdateComponent,
    OtherDeviceTypeDeleteDialogComponent,
  ],
  entryComponents: [OtherDeviceTypeDeleteDialogComponent],
})
export class VoipadminOtherDeviceTypeModule {}
