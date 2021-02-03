import { IDeviceModel } from 'app/shared/model/device-model.model';
import { IOption } from 'app/shared/model/option.model';

export interface IVendor {
  id?: number;
  name?: string;
  deviceModels?: IDeviceModel[];
  options?: IOption[];
}

export class Vendor implements IVendor {
  constructor(public id?: number, public name?: string, public deviceModels?: IDeviceModel[], public options?: IOption[]) {}
}
