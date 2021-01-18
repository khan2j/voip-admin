import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipadminSharedModule } from 'app/shared/shared.module';
import { ResponsiblePersonComponent } from './responsible-person.component';
import { ResponsiblePersonDetailComponent } from './responsible-person-detail.component';
import { ResponsiblePersonUpdateComponent } from './responsible-person-update.component';
import { ResponsiblePersonDeleteDialogComponent } from './responsible-person-delete-dialog.component';
import { responsiblePersonRoute } from './responsible-person.route';

@NgModule({
  imports: [VoipadminSharedModule, RouterModule.forChild(responsiblePersonRoute)],
  declarations: [
    ResponsiblePersonComponent,
    ResponsiblePersonDetailComponent,
    ResponsiblePersonUpdateComponent,
    ResponsiblePersonDeleteDialogComponent,
  ],
  entryComponents: [ResponsiblePersonDeleteDialogComponent],
})
export class VoipadminResponsiblePersonModule {}
