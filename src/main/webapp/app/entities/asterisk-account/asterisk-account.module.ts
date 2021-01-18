import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipadminSharedModule } from 'app/shared/shared.module';
import { AsteriskAccountComponent } from './asterisk-account.component';
import { AsteriskAccountDetailComponent } from './asterisk-account-detail.component';
import { AsteriskAccountUpdateComponent } from './asterisk-account-update.component';
import { AsteriskAccountDeleteDialogComponent } from './asterisk-account-delete-dialog.component';
import { asteriskAccountRoute } from './asterisk-account.route';

@NgModule({
  imports: [VoipadminSharedModule, RouterModule.forChild(asteriskAccountRoute)],
  declarations: [
    AsteriskAccountComponent,
    AsteriskAccountDetailComponent,
    AsteriskAccountUpdateComponent,
    AsteriskAccountDeleteDialogComponent,
  ],
  entryComponents: [AsteriskAccountDeleteDialogComponent],
})
export class VoipadminAsteriskAccountModule {}
