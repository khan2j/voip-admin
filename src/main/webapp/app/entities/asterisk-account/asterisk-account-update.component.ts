import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAsteriskAccount, AsteriskAccount } from 'app/shared/model/asterisk-account.model';
import { AsteriskAccountService } from './asterisk-account.service';

@Component({
  selector: 'jhi-asterisk-account-update',
  templateUrl: './asterisk-account-update.component.html',
})
export class AsteriskAccountUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    username: [],
    asteriskId: [],
  });

  constructor(
    protected asteriskAccountService: AsteriskAccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ asteriskAccount }) => {
      this.updateForm(asteriskAccount);
    });
  }

  updateForm(asteriskAccount: IAsteriskAccount): void {
    this.editForm.patchValue({
      id: asteriskAccount.id,
      username: asteriskAccount.username,
      asteriskId: asteriskAccount.asteriskId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const asteriskAccount = this.createFromForm();
    if (asteriskAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.asteriskAccountService.update(asteriskAccount));
    } else {
      this.subscribeToSaveResponse(this.asteriskAccountService.create(asteriskAccount));
    }
  }

  private createFromForm(): IAsteriskAccount {
    return {
      ...new AsteriskAccount(),
      id: this.editForm.get(['id'])!.value,
      username: this.editForm.get(['username'])!.value,
      asteriskId: this.editForm.get(['asteriskId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAsteriskAccount>>): void {
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
