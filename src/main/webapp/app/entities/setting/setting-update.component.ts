import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISetting, Setting } from 'app/shared/model/setting.model';
import { SettingService } from './setting.service';
import { IOption } from 'app/shared/model/option.model';
import { OptionService } from 'app/entities/option/option.service';
import { IOptionValue } from 'app/shared/model/option-value.model';
import { OptionValueService } from 'app/entities/option-value/option-value.service';
import { IDevice } from 'app/shared/model/device.model';
import { DeviceService } from 'app/entities/device/device.service';

type SelectableEntity = IOption | IOptionValue | IDevice;

@Component({
  selector: 'jhi-setting-update',
  templateUrl: './setting-update.component.html',
})
export class SettingUpdateComponent implements OnInit {
  isSaving = false;
  options: IOption[] = [];
  optionvalues: IOptionValue[] = [];
  devices: IDevice[] = [];

  editForm = this.fb.group({
    id: [],
    textValue: [],
    option: [],
    selectedValues: [],
    deviceId: [],
  });

  constructor(
    protected settingService: SettingService,
    protected optionService: OptionService,
    protected optionValueService: OptionValueService,
    protected deviceService: DeviceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ setting }) => {
      this.updateForm(setting);

      this.optionService.query().subscribe((res: HttpResponse<IOption[]>) => (this.options = res.body || []));

      this.optionValueService.query().subscribe((res: HttpResponse<IOptionValue[]>) => (this.optionvalues = res.body || []));

      this.deviceService.query().subscribe((res: HttpResponse<IDevice[]>) => (this.devices = res.body || []));
    });
  }

  updateForm(setting: ISetting): void {
    this.editForm.patchValue({
      id: setting.id,
      textValue: setting.textValue,
      optionId: setting.option,
      selectedValues: setting.selectedValues,
      deviceId: setting.deviceId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const setting = this.createFromForm();
    if (setting.id !== undefined) {
      this.subscribeToSaveResponse(this.settingService.update(setting));
    } else {
      this.subscribeToSaveResponse(this.settingService.create(setting));
    }
  }

  private createFromForm(): ISetting {
    return {
      ...new Setting(),
      id: this.editForm.get(['id'])!.value,
      textValue: this.editForm.get(['textValue'])!.value,
      option: this.editForm.get(['option'])!.value,
      selectedValues: this.editForm.get(['selectedValues'])!.value,
      deviceId: this.editForm.get(['deviceId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISetting>>): void {
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

  getSelected(selectedVals: IOptionValue[], option: IOptionValue): IOptionValue {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
