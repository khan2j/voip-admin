import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoipadminTestModule } from '../../../test.module';
import { VoipAccountDetailComponent } from 'app/entities/voip-account/voip-account-detail.component';
import { VoipAccount } from 'app/shared/model/voip-account.model';

describe('Component Tests', () => {
  describe('VoipAccount Management Detail Component', () => {
    let comp: VoipAccountDetailComponent;
    let fixture: ComponentFixture<VoipAccountDetailComponent>;
    const route = ({ data: of({ voipAccount: new VoipAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipadminTestModule],
        declarations: [VoipAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VoipAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VoipAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load voipAccount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.voipAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
