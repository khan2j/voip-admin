import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VoipadminTestModule } from '../../../test.module';
import { AsteriskAccountUpdateComponent } from 'app/entities/asterisk-account/asterisk-account-update.component';
import { AsteriskAccountService } from 'app/entities/asterisk-account/asterisk-account.service';
import { AsteriskAccount } from 'app/shared/model/asterisk-account.model';

describe('Component Tests', () => {
  describe('AsteriskAccount Management Update Component', () => {
    let comp: AsteriskAccountUpdateComponent;
    let fixture: ComponentFixture<AsteriskAccountUpdateComponent>;
    let service: AsteriskAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipadminTestModule],
        declarations: [AsteriskAccountUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AsteriskAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AsteriskAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AsteriskAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AsteriskAccount(123);
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
        const entity = new AsteriskAccount();
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
