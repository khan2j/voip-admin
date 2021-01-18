import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOptionValue } from 'app/shared/model/option-value.model';
import { OptionValueService } from './option-value.service';

@Component({
  templateUrl: './option-value-delete-dialog.component.html',
})
export class OptionValueDeleteDialogComponent {
  optionValue?: IOptionValue;

  constructor(
    protected optionValueService: OptionValueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.optionValueService.delete(id).subscribe(() => {
      this.eventManager.broadcast('optionValueListModification');
      this.activeModal.close();
    });
  }
}
