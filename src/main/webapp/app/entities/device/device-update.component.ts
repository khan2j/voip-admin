import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { AbstractControl, FormArray, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDevice, Device } from 'app/shared/model/device.model';
import { DeviceService } from './device.service';
import { IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from 'app/entities/device-model/device-model.service';
import { IResponsiblePerson } from 'app/shared/model/responsible-person.model';
import { ResponsiblePersonService } from 'app/entities/responsible-person/responsible-person.service';
import { DeviceValidationService } from 'app/entities/device/device-validation.service';
import { ISetting, Setting } from 'app/shared/model/setting.model';
import { IOption } from 'app/shared/model/option.model';
import { OptionService } from 'app/entities/option/option.service';
import { IOptionValue } from 'app/shared/model/option-value.model';
import { DeviceModelVendorChangeDialogComponent } from 'app/entities/device-model/device-model-vendor-change-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

type SelectableEntity = IDeviceModel | IResponsiblePerson | IDevice;

@Component({
  selector: 'jhi-device-update',
  templateUrl: './device-update.component.html',
  styleUrls: ['./device-update.component.scss'],
})
export class DeviceUpdateComponent implements OnInit {
  isSaving = false;
  devicemodels: IDeviceModel[] = [];
  responsiblepeople: IResponsiblePerson[] = [];
  devices: IDevice[] = [];
  options: IOption[] = [];
  settingPossibleValues: IOptionValue[][] = [];

  oldModelId: number | undefined;

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
    settings: this.fb.array([]),
  });

  constructor(
    protected deviceService: DeviceService,
    protected deviceModelService: DeviceModelService,
    protected responsiblePersonService: ResponsiblePersonService,
    protected optionService: OptionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private validationService: DeviceValidationService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ device }) => {
      this.updateForm(device);

      this.deviceModelService.query().subscribe((res: HttpResponse<IDeviceModel[]>) => {
        this.devicemodels = res.body
          ? res.body.map(model => {
              model.nameWithVendor = `${model.vendorName} ${model.name}`;
              return model;
            })
          : [];
      });

      this.responsiblePersonService
        .query()
        .subscribe((res: HttpResponse<IResponsiblePerson[]>) => (this.responsiblepeople = res.body || []));

      this.deviceService.query().subscribe((res: HttpResponse<IDevice[]>) => (this.devices = res.body || []));
    });
  }

  settings(): FormArray {
    return this.editForm.get('settings') as FormArray;
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
    if (device.modelId) {
      this.updateModelOptions(device.modelId);
      this.initSettings(device.settings);
      this.oldModelId = device.modelId;
    }
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
      settings: this.settings()
        .controls.map(settingControl => this.createSettingFromForm(settingControl))
        .filter(setting => setting.option && (setting.textValue || (setting.selectedValues && setting.selectedValues.length > 0))),
    };
  }

  private createSettingFromForm(settingControl: AbstractControl): ISetting {
    return {
      ...new Setting(),
      id: settingControl.get('id')?.value,
      // optionId: settingControl.get('optionId')?.value?.id,
      option: settingControl.get('option')?.value,
      textValue: settingControl.get('textValue')?.value,
      selectedValues:
        settingControl.get('option')?.value?.valueType === 'SELECT'
          ? settingControl.get('option')?.value?.multiple
            ? settingControl.get('selectedValues')?.value
            : [settingControl.get('selectedValues')?.value]
          : null,
      deviceId: this.editForm.get(['id'])!.value,
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

  updateModelOptions(modelId: number): void {
    this.optionService.findByModelId(modelId).subscribe((res: HttpResponse<IOption[]>) => {
      if (!!res.body && res.body.length > 0) {
        this.options = res.body.map(option => {
          option.codeWithDescr = `${option.code} (${option.descr})`;
          return option;
        });
      } else {
        this.options = [];
      }
    });
  }

  initSettings(settings: ISetting[] | undefined): void {
    const settingsControls = this.editForm.get('settings') as FormArray;
    if (settings && settings.length > 0) {
      settings.forEach((setting: ISetting, index: number) => {
        if (setting.option) {
          setting.option.codeWithDescr = `${setting.option.code} (${setting.option.descr})`;
          this.addPossibleValuesForOption(setting.option, index);
        }
        settingsControls.push(
          this.fb.group({
            id: [setting.id],
            textValue: [setting.textValue],
            selectedValues: [
              setting?.option?.multiple ? setting.selectedValues : setting.selectedValues ? setting.selectedValues[0] : null,
            ],
            option: [setting.option],
            deviceId: [setting.deviceId],
          })
        );
      });
    } else {
      this.addSetting();
    }
  }

  onSettingOptionChange(option: IOption, arrayIndex: number): void {
    this.addPossibleValuesForOption(option, arrayIndex);
  }

  addSetting(): void {
    this.settings().push(
      this.fb.group({
        id: [],
        textValue: [],
        selectedValues: [[]],
        option: [],
        deviceId: [this.editForm.get('id')!.value],
      })
    );
  }

  removeSetting(index: number): void {
    this.settings().removeAt(index);
  }

  addPossibleValuesForOption(option: IOption, arrayIndex: number): void {
    if (option.possibleValues) {
      this.settingPossibleValues[arrayIndex] = option.possibleValues;
    }
  }

  onDeviceModelChange(deviceModel: IDeviceModel): void {
    const modalRef = this.modalService.open(DeviceModelVendorChangeDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.oldValue = this.oldModelId;
    modalRef.componentInstance.newValue = deviceModel.id;
    modalRef.result.then(result => {
      this.editForm.patchValue({
        modelId: result,
      });
      if (result !== this.oldModelId) {
        this.updateModelOptions(result);
        this.settings().clear();
        this.initSettings([]);
      }
    });
  }
}
