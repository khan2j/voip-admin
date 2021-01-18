import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoipadminTestModule } from '../../../test.module';
import { OptionValueDetailComponent } from 'app/entities/option-value/option-value-detail.component';
import { OptionValue } from 'app/shared/model/option-value.model';

describe('Component Tests', () => {
  describe('OptionValue Management Detail Component', () => {
    let comp: OptionValueDetailComponent;
    let fixture: ComponentFixture<OptionValueDetailComponent>;
    const route = ({ data: of({ optionValue: new OptionValue(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipadminTestModule],
        declarations: [OptionValueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OptionValueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OptionValueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load optionValue on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.optionValue).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
