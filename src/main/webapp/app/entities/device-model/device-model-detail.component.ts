import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDeviceModel } from 'app/shared/model/device-model.model';

@Component({
  selector: 'jhi-device-model-detail',
  templateUrl: './device-model-detail.component.html',
})
export class DeviceModelDetailComponent implements OnInit {
  deviceModel: IDeviceModel | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceModel }) => (this.deviceModel = deviceModel));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
