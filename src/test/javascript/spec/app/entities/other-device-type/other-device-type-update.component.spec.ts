import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VoipadminTestModule } from '../../../test.module';
import { OtherDeviceTypeUpdateComponent } from 'app/entities/other-device-type/other-device-type-update.component';
import { OtherDeviceTypeService } from 'app/entities/other-device-type/other-device-type.service';
import { OtherDeviceType } from 'app/shared/model/other-device-type.model';

describe('Component Tests', () => {
  describe('OtherDeviceType Management Update Component', () => {
    let comp: OtherDeviceTypeUpdateComponent;
    let fixture: ComponentFixture<OtherDeviceTypeUpdateComponent>;
    let service: OtherDeviceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipadminTestModule],
        declarations: [OtherDeviceTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OtherDeviceTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OtherDeviceTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OtherDeviceTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OtherDeviceType(123);
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
        const entity = new OtherDeviceType();
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
