import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResponsiblePerson } from 'app/shared/model/responsible-person.model';
import { ResponsiblePersonService } from './responsible-person.service';

@Component({
  templateUrl: './responsible-person-delete-dialog.component.html',
})
export class ResponsiblePersonDeleteDialogComponent {
  responsiblePerson?: IResponsiblePerson;

  constructor(
    protected responsiblePersonService: ResponsiblePersonService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.responsiblePersonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('responsiblePersonListModification');
      this.activeModal.close();
    });
  }
}
