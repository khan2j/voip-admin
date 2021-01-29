import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDeviceModel, DeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from './device-model.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IOtherDeviceType } from 'app/shared/model/other-device-type.model';
import { OtherDeviceTypeService } from 'app/entities/other-device-type/other-device-type.service';
import { IOption } from 'app/shared/model/option.model';
import { OptionService } from 'app/entities/option/option.service';

@Component({
  selector: 'jhi-device-model-update',
  templateUrl: './device-model-update.component.html',
})
export class DeviceModelUpdateComponent implements OnInit {
  isSaving = false;
  otherdevicetypes: IOtherDeviceType[] = [];
  options: IOption[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    isConfigurable: [null, [Validators.required]],
    linesCount: [],
    configTemplate: [],
    configTemplateContentType: [],
    firmwareFile: [],
    firmwareFileContentType: [],
    deviceType: [],
    otherDeviceTypeId: [],
    options: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected deviceModelService: DeviceModelService,
    protected otherDeviceTypeService: OtherDeviceTypeService,
    protected optionService: OptionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceModel }) => {
      this.updateForm(deviceModel);

      this.otherDeviceTypeService.query().subscribe((res: HttpResponse<IOtherDeviceType[]>) => (this.otherdevicetypes = res.body || []));
      this.optionService.query().subscribe((res: HttpResponse<IOption[]>) => {
        if (!!res.body && res.body.length > 0) {
          this.options = res.body.map(option => {
            option.codeWithDescr = `${option.code} (${option.descr})`;
            return option;
          });
        } else {
          this.options = [];
        }
      });
    });
  }

  updateForm(deviceModel: IDeviceModel): void {
    this.editForm.patchValue({
      id: deviceModel.id,
      name: deviceModel.name,
      isConfigurable: deviceModel.isConfigurable,
      linesCount: deviceModel.linesCount,
      configTemplate: deviceModel.configTemplate,
      configTemplateContentType: deviceModel.configTemplateContentType,
      firmwareFile: deviceModel.firmwareFile,
      firmwareFileContentType: deviceModel.firmwareFileContentType,
      deviceType: deviceModel.deviceType,
      otherDeviceTypeId: deviceModel.otherDeviceTypeId,
      options: deviceModel.options
        ? deviceModel.options.map(option => {
            option.codeWithDescr = `${option.code} (${option.descr})`;
            return option;
          })
        : [],
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('voipadminApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deviceModel = this.createFromForm();
    if (deviceModel.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceModelService.update(deviceModel));
    } else {
      this.subscribeToSaveResponse(this.deviceModelService.create(deviceModel));
    }
  }

  private createFromForm(): IDeviceModel {
    return {
      ...new DeviceModel(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      isConfigurable: this.editForm.get(['isConfigurable'])!.value,
      linesCount: this.editForm.get(['linesCount'])!.value,
      configTemplateContentType: this.editForm.get(['configTemplateContentType'])!.value,
      configTemplate: this.editForm.get(['configTemplate'])!.value,
      firmwareFileContentType: this.editForm.get(['firmwareFileContentType'])!.value,
      firmwareFile: this.editForm.get(['firmwareFile'])!.value,
      deviceType: this.editForm.get(['deviceType'])!.value,
      otherDeviceTypeId: this.editForm.get(['otherDeviceTypeId'])!.value,
      options: this.editForm.get(['options'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeviceModel>>): void {
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

  trackById(index: number, item: IOtherDeviceType): any {
    return item.id;
  }
}
