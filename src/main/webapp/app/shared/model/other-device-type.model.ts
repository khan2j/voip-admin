export interface IOtherDeviceType {
  id?: number;
  name?: string;
  description?: string;
}

export class OtherDeviceType implements IOtherDeviceType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
