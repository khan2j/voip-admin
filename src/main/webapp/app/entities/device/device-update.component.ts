import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { AbstractControl, FormBuilder, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDevice, Device } from 'app/shared/model/device.model';
import { DeviceService } from './device.service';
import { IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from 'app/entities/device-model/device-model.service';
import { IResponsiblePerson } from 'app/shared/model/responsible-person.model';
import { ResponsiblePersonService } from 'app/entities/responsible-person/responsible-person.service';
import { DeviceValidationService } from 'app/entities/device/device-validation.service';

type SelectableEntity = IDeviceModel | IResponsiblePerson | IDevice;

@Component({
  selector: 'jhi-device-update',
  templateUrl: './device-update.component.html',
})
export class DeviceUpdateComponent implements OnInit {
  isSaving = false;
  devicemodels: IDeviceModel[] = [];
  responsiblepeople: IResponsiblePerson[] = [];
  devices: IDevice[] = [];

  editForm = this.fb.group({
    id: [],
    mac: [null, [Validators.required, Validators.pattern('^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$')]],
    inventoryNumber: [],
    location: [],
    hostname: [],
    webLogin: [],
    webPassword: [],
    dhcpEnabled: [],
    ipAddress: [null, [Validators.pattern('^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$')]],
    subnetMask: [
      null,
      [
        Validators.pattern(
          '^(((255\\.){3}(255|254|252|248|240|224|192|128|0+))|((255\\.){2}(255|254|252|248|240|224|192|128|0+)\\.0)|((255\\.)(255|254|252|248|240|224|192|128|0+)(\\.0+){2})|((255|254|252|248|240|224|192|128|0+)(\\.0+){3}))$'
        ),
      ],
    ],
    defaultGw: [null, this.validationService.ipAddressOrDomainNamePattern],
    dns1: [null, this.validationService.ipAddressOrDomainNamePattern],
    dns2: [null, this.validationService.ipAddressOrDomainNamePattern],
    provisioningMode: [],
    provisioningUrl: [],
    ntpServer: [],
    notes: [],
    modelId: [],
    responsiblePersonId: [],
    parentId: [],
  });

  constructor(
    protected deviceService: DeviceService,
    protected deviceModelService: DeviceModelService,
    protected responsiblePersonService: ResponsiblePersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private validationService: DeviceValidationService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ device }) => {
      this.updateForm(device);

      this.deviceModelService.query().subscribe((res: HttpResponse<IDeviceModel[]>) => (this.devicemodels = res.body || []));

      this.responsiblePersonService
        .query()
        .subscribe((res: HttpResponse<IResponsiblePerson[]>) => (this.responsiblepeople = res.body || []));

      this.deviceService.query().subscribe((res: HttpResponse<IDevice[]>) => (this.devices = res.body || []));
    });
  }

  updateForm(device: IDevice): void {
    this.editForm.patchValue({
      id: device.id,
      mac: device.mac,
      inventoryNumber: device.inventoryNumber,
      location: device.location,
      hostname: device.hostname,
      webLogin: device.webLogin,
      webPassword: device.webPassword,
      dhcpEnabled: device.dhcpEnabled,
      ipAddress: device.ipAddress,
      subnetMask: device.subnetMask,
      defaultGw: device.defaultGw,
      dns1: device.dns1,
      dns2: device.dns2,
      provisioningMode: device.provisioningMode,
      provisioningUrl: device.provisioningUrl,
      ntpServer: device.ntpServer,
      notes: device.notes,
      modelId: device.modelId,
      responsiblePersonId: device.responsiblePersonId,
      parentId: device.parentId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const device = this.createFromForm();
    if (device.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceService.update(device));
    } else {
      this.subscribeToSaveResponse(this.deviceService.create(device));
    }
  }

  private createFromForm(): IDevice {
    return {
      ...new Device(),
      id: this.editForm.get(['id'])!.value,
      mac: this.editForm.get(['mac'])!.value,
      inventoryNumber: this.editForm.get(['inventoryNumber'])!.value,
      location: this.editForm.get(['location'])!.value,
      hostname: this.editForm.get(['hostname'])!.value,
      webLogin: this.editForm.get(['webLogin'])!.value,
      webPassword: this.editForm.get(['webPassword'])!.value,
      dhcpEnabled: this.editForm.get(['dhcpEnabled'])!.value,
      ipAddress: this.editForm.get(['ipAddress'])!.value,
      subnetMask: this.editForm.get(['subnetMask'])!.value,
      defaultGw: this.editForm.get(['defaultGw'])!.value,
      dns1: this.editForm.get(['dns1'])!.value,
      dns2: this.editForm.get(['dns2'])!.value,
      provisioningMode: this.editForm.get(['provisioningMode'])!.value,
      provisioningUrl: this.editForm.get(['provisioningUrl'])!.value,
      ntpServer: this.editForm.get(['ntpServer'])!.value,
      notes: this.editForm.get(['notes'])!.value,
      modelId: this.editForm.get(['modelId'])!.value,
      responsiblePersonId: this.editForm.get(['responsiblePersonId'])!.value,
      parentId: this.editForm.get(['parentId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDevice>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  test(): void {
    1 + 2;
  }
}
