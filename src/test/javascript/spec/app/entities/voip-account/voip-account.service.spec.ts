import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { VoipAccountService } from 'app/entities/voip-account/voip-account.service';
import { IVoipAccount, VoipAccount } from 'app/shared/model/voip-account.model';

describe('Service Tests', () => {
  describe('VoipAccount Service', () => {
    let injector: TestBed;
    let service: VoipAccountService;
    let httpMock: HttpTestingController;
    let elemDefault: IVoipAccount;
    let expectedResult: IVoipAccount | IVoipAccount[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VoipAccountService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new VoipAccount(0, false, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a VoipAccount', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new VoipAccount()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VoipAccount', () => {
        const returnedFromService = Object.assign(
          {
            manuallyCreated: true,
            username: 'BBBBBB',
            password: 'BBBBBB',
            sipServer: 'BBBBBB',
            sipPort: 'BBBBBB',
            lineEnable: true,
            lineNumber: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VoipAccount', () => {
        const returnedFromService = Object.assign(
          {
            manuallyCreated: true,
            username: 'BBBBBB',
            password: 'BBBBBB',
            sipServer: 'BBBBBB',
            sipPort: 'BBBBBB',
            lineEnable: true,
            lineNumber: 'BBBBBB',
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

      it('should delete a VoipAccount', () => {
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
