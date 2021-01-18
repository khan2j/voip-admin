import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DeviceService } from 'app/entities/device/device.service';
import { IDevice, Device } from 'app/shared/model/device.model';
import { ProvisioningMode } from 'app/shared/model/enumerations/provisioning-mode.model';

describe('Service Tests', () => {
  describe('Device Service', () => {
    let injector: TestBed;
    let service: DeviceService;
    let httpMock: HttpTestingController;
    let elemDefault: IDevice;
    let expectedResult: IDevice | IDevice[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DeviceService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Device(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        ProvisioningMode.FTP,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Device', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Device()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Device', () => {
        const returnedFromService = Object.assign(
          {
            mac: 'BBBBBB',
            inventoryNumber: 'BBBBBB',
            location: 'BBBBBB',
            hostname: 'BBBBBB',
            webLogin: 'BBBBBB',
            webPassword: 'BBBBBB',
            dhcpEnabled: true,
            ipAddress: 'BBBBBB',
            subnetMask: 'BBBBBB',
            defaultGw: 'BBBBBB',
            dns1: 'BBBBBB',
            dns2: 'BBBBBB',
            provisioningMode: 'BBBBBB',
            provisioningUrl: 'BBBBBB',
            ntpServer: 'BBBBBB',
            notes: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Device', () => {
        const returnedFromService = Object.assign(
          {
            mac: 'BBBBBB',
            inventoryNumber: 'BBBBBB',
            location: 'BBBBBB',
            hostname: 'BBBBBB',
            webLogin: 'BBBBBB',
            webPassword: 'BBBBBB',
            dhcpEnabled: true,
            ipAddress: 'BBBBBB',
            subnetMask: 'BBBBBB',
            defaultGw: 'BBBBBB',
            dns1: 'BBBBBB',
            dns2: 'BBBBBB',
            provisioningMode: 'BBBBBB',
            provisioningUrl: 'BBBBBB',
            ntpServer: 'BBBBBB',
            notes: 'BBBBBB',
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

      it('should delete a Device', () => {
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
