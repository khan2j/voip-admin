import { IOptionValue } from 'app/shared/model/option-value.model';
import { IOption } from 'app/shared/model/option.model';

export interface ISetting {
  id?: number;
  textValue?: string;
  optionCode?: string;
  option?: IOption;
  selectedValues?: IOptionValue[];
  deviceMac?: string;
  deviceId?: number;
}

export class Setting implements ISetting {
  constructor(
    public id?: number,
    public textValue?: string,
    public optionCode?: string,
    public option?: IOption,
    public selectedValues?: IOptionValue[],
    public deviceMac?: string,
    public deviceId?: number
  ) {}
}
