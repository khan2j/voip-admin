import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VoipadminTestModule } from '../../../test.module';
import { VoipAccountUpdateComponent } from 'app/entities/voip-account/voip-account-update.component';
import { VoipAccountService } from 'app/entities/voip-account/voip-account.service';
import { VoipAccount } from 'app/shared/model/voip-account.model';

describe('Component Tests', () => {
  describe('VoipAccount Management Update Component', () => {
    let comp: VoipAccountUpdateComponent;
    let fixture: ComponentFixture<VoipAccountUpdateComponent>;
    let service: VoipAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipadminTestModule],
        declarations: [VoipAccountUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VoipAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoipAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VoipAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VoipAccount(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new VoipAccount();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
