import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { VoipadminTestModule } from '../../../test.module';
import { ResponsiblePersonComponent } from 'app/entities/responsible-person/responsible-person.component';
import { ResponsiblePersonService } from 'app/entities/responsible-person/responsible-person.service';
import { ResponsiblePerson } from 'app/shared/model/responsible-person.model';

describe('Component Tests', () => {
  describe('ResponsiblePerson Management Component', () => {
    let comp: ResponsiblePersonComponent;
    let fixture: ComponentFixture<ResponsiblePersonComponent>;
    let service: ResponsiblePersonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipadminTestModule],
        declarations: [ResponsiblePersonComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(ResponsiblePersonComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResponsiblePersonComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResponsiblePersonService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ResponsiblePerson(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.responsiblePeople && comp.responsiblePeople[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ResponsiblePerson(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.responsiblePeople && comp.responsiblePeople[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
