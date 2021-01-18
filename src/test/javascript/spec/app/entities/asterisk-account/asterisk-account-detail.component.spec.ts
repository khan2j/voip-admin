import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoipadminTestModule } from '../../../test.module';
import { AsteriskAccountDetailComponent } from 'app/entities/asterisk-account/asterisk-account-detail.component';
import { AsteriskAccount } from 'app/shared/model/asterisk-account.model';

describe('Component Tests', () => {
  describe('AsteriskAccount Management Detail Component', () => {
    let comp: AsteriskAccountDetailComponent;
    let fixture: ComponentFixture<AsteriskAccountDetailComponent>;
    const route = ({ data: of({ asteriskAccount: new AsteriskAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipadminTestModule],
        declarations: [AsteriskAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AsteriskAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AsteriskAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load asteriskAccount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.asteriskAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
