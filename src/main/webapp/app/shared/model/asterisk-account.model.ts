export interface IAsteriskAccount {
  id?: number;
  username?: string;
  asteriskId?: string;
  voipAccountId?: number;
}

export class AsteriskAccount implements IAsteriskAccount {
  constructor(public id?: number, public username?: string, public asteriskId?: string, public voipAccountId?: number) {}
}
