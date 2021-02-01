import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipadminSharedModule } from 'app/shared/shared.module';
import { OptionComponent } from './option.component';
import { OptionDetailComponent } from './option-detail.component';
import { OptionUpdateComponent } from './option-update.component';
import { OptionDeleteDialogComponent } from './option-delete-dialog.component';
import { optionRoute } from './option.route';
import { MatRadioModule } from '@angular/material/radio';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [VoipadminSharedModule, RouterModule.forChild(optionRoute), MatRadioModule, MatSlideToggleModule, NgSelectModule],
  declarations: [OptionComponent, OptionDetailComponent, OptionUpdateComponent, OptionDeleteDialogComponent],
  entryComponents: [OptionDeleteDialogComponent],
})
export class VoipadminOptionModule {}
