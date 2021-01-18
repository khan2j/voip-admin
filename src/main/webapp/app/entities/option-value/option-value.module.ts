import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipadminSharedModule } from 'app/shared/shared.module';
import { OptionValueComponent } from './option-value.component';
import { OptionValueDetailComponent } from './option-value-detail.component';
import { OptionValueUpdateComponent } from './option-value-update.component';
import { OptionValueDeleteDialogComponent } from './option-value-delete-dialog.component';
import { optionValueRoute } from './option-value.route';

@NgModule({
  imports: [VoipadminSharedModule, RouterModule.forChild(optionValueRoute)],
  declarations: [OptionValueComponent, OptionValueDetailComponent, OptionValueUpdateComponent, OptionValueDeleteDialogComponent],
  entryComponents: [OptionValueDeleteDialogComponent],
})
export class VoipadminOptionValueModule {}
