import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormArray, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOption, Option } from 'app/shared/model/option.model';
import { OptionService } from './option.service';
import { IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from 'app/entities/device-model/device-model.service';
import { OptionValue } from 'app/shared/model/option-value.model';

@Component({
  selector: 'jhi-option-update',
  templateUrl: './option-update.component.html',
  styleUrls: ['./option-update.component.scss'],
})
export class OptionUpdateComponent implements OnInit {
  isSaving = false;
  devicemodels: IDeviceModel[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    descr: [],
    valueType: [],
    multiple: [],
    models: [],
    possibleValues: this.fb.array([]),
  });

  constructor(
    protected optionService: OptionService,
    protected deviceModelService: DeviceModelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ option }) => {
      this.updateForm(option);

      this.deviceModelService.query().subscribe((res: HttpResponse<IDeviceModel[]>) => (this.devicemodels = res.body || []));
    });
  }

  updateForm(option: IOption): void {
    this.editForm.patchValue({
      id: option.id,
      code: option.code,
      descr: option.descr,
      valueType: option.valueType,
      multiple: option.multiple,
      models: option.models,
    });
    this.initPossibleValues(option.possibleValues);
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const option = this.createFromForm();
    if (option.id !== undefined) {
      this.subscribeToSaveResponse(this.optionService.update(option));
    } else {
      this.subscribeToSaveResponse(this.optionService.create(option));
    }
  }

  private createFromForm(): IOption {
    return {
      ...new Option(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      descr: this.editForm.get(['descr'])!.value,
      valueType: this.editForm.get(['valueType'])!.value,
      multiple: this.editForm.get(['multiple'])!.value,
      models: this.editForm.get(['models'])!.value,
      possibleValues: this.editForm.get(['possibleValues']) ? this.editForm.get(['possibleValues'])!.value : [],
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOption>>): void {
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

  trackById(index: number, item: IDeviceModel): any {
    return item.id;
  }

  getSelected(selectedVals: IDeviceModel[], option: IDeviceModel): IDeviceModel {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  possibleValues(): FormArray {
    return this.editForm.get('possibleValues') as FormArray;
  }

  addPossibleValue(): void {
    (this.editForm.get('possibleValues') as FormArray).push(
      this.fb.group({
        id: null,
        value: null,
        optionId: this.editForm.get('id')!.value,
      })
    );
  }

  removePossibleValue(index: number): void {
    (this.editForm.get('possibleValues') as FormArray).removeAt(index);
  }

  initPossibleValues(possibleValues: OptionValue[] | undefined): void {
    const possibleValuesControls = this.editForm.get('possibleValues') as FormArray;
    if (possibleValues && possibleValues.length > 0) {
      possibleValues.forEach(possibleValue => {
        possibleValuesControls.push(
          this.fb.group({
            id: possibleValue.id,
            value: possibleValue.value,
            optionId: possibleValue.optionId,
          })
        );
      });
    } else {
      this.addPossibleValue();
    }
  }
}
