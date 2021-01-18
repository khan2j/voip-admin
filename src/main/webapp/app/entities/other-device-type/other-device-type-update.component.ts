import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOtherDeviceType, OtherDeviceType } from 'app/shared/model/other-device-type.model';
import { OtherDeviceTypeService } from './other-device-type.service';

@Component({
  selector: 'jhi-other-device-type-update',
  templateUrl: './other-device-type-update.component.html',
})
export class OtherDeviceTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
  });

  constructor(
    protected otherDeviceTypeService: OtherDeviceTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ otherDeviceType }) => {
      this.updateForm(otherDeviceType);
    });
  }

  updateForm(otherDeviceType: IOtherDeviceType): void {
    this.editForm.patchValue({
      id: otherDeviceType.id,
      name: otherDeviceType.name,
      description: otherDeviceType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const otherDeviceType = this.createFromForm();
    if (otherDeviceType.id !== undefined) {
      this.subscribeToSaveResponse(this.otherDeviceTypeService.update(otherDeviceType));
    } else {
      this.subscribeToSaveResponse(this.otherDeviceTypeService.create(otherDeviceType));
    }
  }

  private createFromForm(): IOtherDeviceType {
    return {
      ...new OtherDeviceType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOtherDeviceType>>): void {
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
}
