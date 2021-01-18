import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoipadminTestModule } from '../../../test.module';
import { OtherDeviceTypeDetailComponent } from 'app/entities/other-device-type/other-device-type-detail.component';
import { OtherDeviceType } from 'app/shared/model/other-device-type.model';

describe('Component Tests', () => {
  describe('OtherDeviceType Management Detail Component', () => {
    let comp: OtherDeviceTypeDetailComponent;
    let fixture: ComponentFixture<OtherDeviceTypeDetailComponent>;
    const route = ({ data: of({ otherDeviceType: new OtherDeviceType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipadminTestModule],
        declarations: [OtherDeviceTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OtherDeviceTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OtherDeviceTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load otherDeviceType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.otherDeviceType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
