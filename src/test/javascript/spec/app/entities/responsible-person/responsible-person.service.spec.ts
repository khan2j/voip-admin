import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ResponsiblePersonService } from 'app/entities/responsible-person/responsible-person.service';
import { IResponsiblePerson, ResponsiblePerson } from 'app/shared/model/responsible-person.model';

describe('Service Tests', () => {
  describe('ResponsiblePerson Service', () => {
    let injector: TestBed;
    let service: ResponsiblePersonService;
    let httpMock: HttpTestingController;
    let elemDefault: IResponsiblePerson;
    let expectedResult: IResponsiblePerson | IResponsiblePerson[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ResponsiblePersonService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ResponsiblePerson(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ResponsiblePerson', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ResponsiblePerson()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ResponsiblePerson', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            firstName: 'BBBBBB',
            secondName: 'BBBBBB',
            lastName: 'BBBBBB',
            position: 'BBBBBB',
            room: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ResponsiblePerson', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            firstName: 'BBBBBB',
            secondName: 'BBBBBB',
            lastName: 'BBBBBB',
            position: 'BBBBBB',
            room: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ResponsiblePerson', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
