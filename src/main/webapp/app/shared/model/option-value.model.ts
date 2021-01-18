import { ISetting } from 'app/shared/model/setting.model';

export interface IOptionValue {
  id?: number;
  value?: string;
  optionId?: number;
  settings?: ISetting[];
}

export class OptionValue implements IOptionValue {
  constructor(public id?: number, public value?: string, public optionId?: number, public settings?: ISetting[]) {}
}
