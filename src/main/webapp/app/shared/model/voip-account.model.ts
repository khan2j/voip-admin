export interface IVoipAccount {
  id?: number;
  manuallyCreated?: boolean;
  username?: string;
  password?: string;
  sipServer?: string;
  sipPort?: string;
  lineEnable?: boolean;
  lineNumber?: number;
  asteriskAccountId?: number;
  deviceMac?: string;
  deviceId?: number;
}

export class VoipAccount implements IVoipAccount {
  constructor(
    public id?: number,
    public manuallyCreated?: boolean,
    public username?: string,
    public password?: string,
    public sipServer?: string,
    public sipPort?: string,
    public lineEnable?: boolean,
    public lineNumber?: number,
    public asteriskAccountId?: number,
    public deviceMac?: string,
    public deviceId?: number
  ) {
    this.manuallyCreated = this.manuallyCreated || false;
    this.lineEnable = this.lineEnable || false;
  }
}
