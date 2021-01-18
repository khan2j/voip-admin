import { IOptionValue } from 'app/shared/model/option-value.model';

export interface ISetting {
  id?: number;
  textValue?: string;
  optionCode?: string;
  optionId?: number;
  selectedValues?: IOptionValue[];
  deviceMac?: string;
  deviceId?: number;
}

export class Setting implements ISetting {
  constructor(
    public id?: number,
    public textValue?: string,
    public optionCode?: string,
    public optionId?: number,
    public selectedValues?: IOptionValue[],
    public deviceMac?: string,
    public deviceId?: number
  ) {}
}
