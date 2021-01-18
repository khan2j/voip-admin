import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { VoipadminTestModule } from '../../../test.module';
import { DeviceModelDetailComponent } from 'app/entities/device-model/device-model-detail.component';
import { DeviceModel } from 'app/shared/model/device-model.model';

describe('Component Tests', () => {
  describe('DeviceModel Management Detail Component', () => {
    let comp: DeviceModelDetailComponent;
    let fixture: ComponentFixture<DeviceModelDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ deviceModel: new DeviceModel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipadminTestModule],
        declarations: [DeviceModelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DeviceModelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeviceModelDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load deviceModel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deviceModel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
