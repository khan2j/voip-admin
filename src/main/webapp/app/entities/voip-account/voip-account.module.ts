import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipadminSharedModule } from 'app/shared/shared.module';
import { VoipAccountComponent } from './voip-account.component';
import { VoipAccountDetailComponent } from './voip-account-detail.component';
import { VoipAccountUpdateComponent } from './voip-account-update.component';
import { VoipAccountDeleteDialogComponent } from './voip-account-delete-dialog.component';
import { voipAccountRoute } from './voip-account.route';

@NgModule({
  imports: [VoipadminSharedModule, RouterModule.forChild(voipAccountRoute)],
  declarations: [VoipAccountComponent, VoipAccountDetailComponent, VoipAccountUpdateComponent, VoipAccountDeleteDialogComponent],
  entryComponents: [VoipAccountDeleteDialogComponent],
})
export class VoipadminVoipAccountModule {}
