import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IVoipAccount, VoipAccount } from 'app/shared/model/voip-account.model';
import { VoipAccountService } from './voip-account.service';
import { IAsteriskAccount } from 'app/shared/model/asterisk-account.model';
import { AsteriskAccountService } from 'app/entities/asterisk-account/asterisk-account.service';
import { IDevice } from 'app/shared/model/device.model';
import { DeviceService } from 'app/entities/device/device.service';

type SelectableEntity = IAsteriskAccount | IDevice;

@Component({
  selector: 'jhi-voip-account-update',
  templateUrl: './voip-account-update.component.html',
})
export class VoipAccountUpdateComponent implements OnInit {
  isSaving = false;
  asteriskaccounts: IAsteriskAccount[] = [];
  devices: IDevice[] = [];

  editForm = this.fb.group({
    id: [],
    manuallyCreated: [],
    username: [null, [Validators.required]],
    password: [],
    sipServer: [],
    sipPort: [],
    lineEnable: [],
    lineNumber: [],
    asteriskAccountId: [],
    deviceId: [],
  });

  constructor(
    protected voipAccountService: VoipAccountService,
    protected asteriskAccountService: AsteriskAccountService,
    protected deviceService: DeviceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voipAccount }) => {
      this.updateForm(voipAccount);

      this.asteriskAccountService
        .query({ filter: 'voipaccount-is-null' })
        .pipe(
          map((res: HttpResponse<IAsteriskAccount[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAsteriskAccount[]) => {
          if (!voipAccount.asteriskAccountId) {
            this.asteriskaccounts = resBody;
          } else {
            this.asteriskAccountService
              .find(voipAccount.asteriskAccountId)
              .pipe(
                map((subRes: HttpResponse<IAsteriskAccount>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAsteriskAccount[]) => (this.asteriskaccounts = concatRes));
          }
        });

      this.deviceService.query().subscribe((res: HttpResponse<IDevice[]>) => (this.devices = res.body || []));
    });
  }

  updateForm(voipAccount: IVoipAccount): void {
    this.editForm.patchValue({
      id: voipAccount.id,
      manuallyCreated: voipAccount.manuallyCreated,
      username: voipAccount.username,
      password: voipAccount.password,
      sipServer: voipAccount.sipServer,
      sipPort: voipAccount.sipPort,
      lineEnable: voipAccount.lineEnable,
      lineNumber: voipAccount.lineNumber,
      asteriskAccountId: voipAccount.asteriskAccountId,
      deviceId: voipAccount.deviceId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voipAccount = this.createFromForm();
    if (voipAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.voipAccountService.update(voipAccount));
    } else {
      this.subscribeToSaveResponse(this.voipAccountService.create(voipAccount));
    }
  }

  private createFromForm(): IVoipAccount {
    return {
      ...new VoipAccount(),
      id: this.editForm.get(['id'])!.value,
      manuallyCreated: this.editForm.get(['manuallyCreated'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
      sipServer: this.editForm.get(['sipServer'])!.value,
      sipPort: this.editForm.get(['sipPort'])!.value,
      lineEnable: this.editForm.get(['lineEnable'])!.value,
      lineNumber: this.editForm.get(['lineNumber'])!.value,
      asteriskAccountId: this.editForm.get(['asteriskAccountId'])!.value,
      deviceId: this.editForm.get(['deviceId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoipAccount>>): void {
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
}
