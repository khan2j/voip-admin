import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'device',
        loadChildren: () => import('./device/device.module').then(m => m.VoipadminDeviceModule),
      },
      {
        path: 'device-model',
        loadChildren: () => import('./device-model/device-model.module').then(m => m.VoipadminDeviceModelModule),
      },
      {
        path: 'other-device-type',
        loadChildren: () => import('./other-device-type/other-device-type.module').then(m => m.VoipadminOtherDeviceTypeModule),
      },
      {
        path: 'responsible-person',
        loadChildren: () => import('./responsible-person/responsible-person.module').then(m => m.VoipadminResponsiblePersonModule),
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.VoipadminDepartmentModule),
      },
      {
        path: 'voip-account',
        loadChildren: () => import('./voip-account/voip-account.module').then(m => m.VoipadminVoipAccountModule),
      },
      {
        path: 'asterisk-account',
        loadChildren: () => import('./asterisk-account/asterisk-account.module').then(m => m.VoipadminAsteriskAccountModule),
      },
      {
        path: 'setting',
        loadChildren: () => import('./setting/setting.module').then(m => m.VoipadminSettingModule),
      },
      {
        path: 'option',
        loadChildren: () => import('./option/option.module').then(m => m.VoipadminOptionModule),
      },
      {
        path: 'option-value',
        loadChildren: () => import('./option-value/option-value.module').then(m => m.VoipadminOptionValueModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class VoipadminEntityModule {}
