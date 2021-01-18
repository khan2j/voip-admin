import { IOption } from 'app/shared/model/option.model';
import { DeviceType } from 'app/shared/model/enumerations/device-type.model';

export interface IDeviceModel {
  id?: number;
  name?: string;
  isConfigurable?: boolean;
  linesCount?: number;
  configTemplateContentType?: string;
  configTemplate?: any;
  firmwareFileContentType?: string;
  firmwareFile?: any;
  deviceType?: DeviceType;
  otherDeviceTypeId?: number;
  options?: IOption[];
}

export class DeviceModel implements IDeviceModel {
  constructor(
    public id?: number,
    public name?: string,
    public isConfigurable?: boolean,
    public linesCount?: number,
    public configTemplateContentType?: string,
    public configTemplate?: any,
    public firmwareFileContentType?: string,
    public firmwareFile?: any,
    public deviceType?: DeviceType,
    public otherDeviceTypeId?: number,
    public options?: IOption[]
  ) {
    this.isConfigurable = this.isConfigurable || false;
  }
}
