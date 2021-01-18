import { IOptionValue } from 'app/shared/model/option-value.model';
import { IDeviceModel } from 'app/shared/model/device-model.model';
import { OptionValueType } from 'app/shared/model/enumerations/option-value-type.model';

export interface IOption {
  id?: number;
  code?: string;
  descr?: string;
  valueType?: OptionValueType;
  multiple?: boolean;
  possibleValues?: IOptionValue[];
  models?: IDeviceModel[];
}

export class Option implements IOption {
  constructor(
    public id?: number,
    public code?: string,
    public descr?: string,
    public valueType?: OptionValueType,
    public multiple?: boolean,
    public possibleValues?: IOptionValue[],
    public models?: IDeviceModel[]
  ) {
    this.multiple = this.multiple || false;
  }
}
