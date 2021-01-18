import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOptionValue, OptionValue } from 'app/shared/model/option-value.model';
import { OptionValueService } from './option-value.service';
import { IOption } from 'app/shared/model/option.model';
import { OptionService } from 'app/entities/option/option.service';

@Component({
  selector: 'jhi-option-value-update',
  templateUrl: './option-value-update.component.html',
})
export class OptionValueUpdateComponent implements OnInit {
  isSaving = false;
  options: IOption[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    optionId: [],
  });

  constructor(
    protected optionValueService: OptionValueService,
    protected optionService: OptionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optionValue }) => {
      this.updateForm(optionValue);

      this.optionService.query().subscribe((res: HttpResponse<IOption[]>) => (this.options = res.body || []));
    });
  }

  updateForm(optionValue: IOptionValue): void {
    this.editForm.patchValue({
      id: optionValue.id,
      value: optionValue.value,
      optionId: optionValue.optionId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const optionValue = this.createFromForm();
    if (optionValue.id !== undefined) {
      this.subscribeToSaveResponse(this.optionValueService.update(optionValue));
    } else {
      this.subscribeToSaveResponse(this.optionValueService.create(optionValue));
    }
  }

  private createFromForm(): IOptionValue {
    return {
      ...new OptionValue(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      optionId: this.editForm.get(['optionId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOptionValue>>): void {
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

  trackById(index: number, item: IOption): any {
    return item.id;
  }
}
