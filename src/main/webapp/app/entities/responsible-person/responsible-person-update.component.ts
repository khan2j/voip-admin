import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IResponsiblePerson, ResponsiblePerson } from 'app/shared/model/responsible-person.model';
import { ResponsiblePersonService } from './responsible-person.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';

@Component({
  selector: 'jhi-responsible-person-update',
  templateUrl: './responsible-person-update.component.html',
})
export class ResponsiblePersonUpdateComponent implements OnInit {
  isSaving = false;
  departments: IDepartment[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    firstName: [null, [Validators.required]],
    secondName: [],
    lastName: [null, [Validators.required]],
    position: [],
    room: [],
    departmentId: [],
  });

  constructor(
    protected responsiblePersonService: ResponsiblePersonService,
    protected departmentService: DepartmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsiblePerson }) => {
      this.updateForm(responsiblePerson);

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    });
  }

  updateForm(responsiblePerson: IResponsiblePerson): void {
    this.editForm.patchValue({
      id: responsiblePerson.id,
      code: responsiblePerson.code,
      firstName: responsiblePerson.firstName,
      secondName: responsiblePerson.secondName,
      lastName: responsiblePerson.lastName,
      position: responsiblePerson.position,
      room: responsiblePerson.room,
      departmentId: responsiblePerson.departmentId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const responsiblePerson = this.createFromForm();
    if (responsiblePerson.id !== undefined) {
      this.subscribeToSaveResponse(this.responsiblePersonService.update(responsiblePerson));
    } else {
      this.subscribeToSaveResponse(this.responsiblePersonService.create(responsiblePerson));
    }
  }

  private createFromForm(): IResponsiblePerson {
    return {
      ...new ResponsiblePerson(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      secondName: this.editForm.get(['secondName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      position: this.editForm.get(['position'])!.value,
      room: this.editForm.get(['room'])!.value,
      departmentId: this.editForm.get(['departmentId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsiblePerson>>): void {
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

  trackById(index: number, item: IDepartment): any {
    return item.id;
  }
}
