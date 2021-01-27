import { ISetting } from 'app/shared/model/setting.model';
import { IVoipAccount } from 'app/shared/model/voip-account.model';
import { ProvisioningMode } from 'app/shared/model/enumerations/provisioning-mode.model';

export interface IDevice {
  id?: number;
  mac?: string;
  inventoryNumber?: string;
  location?: string;
  hostname?: string;
  webLogin?: string;
  webPassword?: string;
  dhcpEnabled?: boolean;
  ipAddress?: string;
  subnetMask?: string;
  defaultGw?: string;
  dns1?: string;
  dns2?: string;
  provisioningMode?: ProvisioningMode;
  provisioningUrl?: string;
  ntpServer?: string;
  notes?: string;
  settings?: ISetting[];
  voipAccounts?: IVoipAccount[];
  children?: IDevice[];
  modelName?: string;
  modelId?: number;
  responsiblePersonLastName?: string;
  responsiblePersonFullName?: string;
  responsiblePersonId?: number;
  parentId?: number;
}

export class Device implements IDevice {
  constructor(
    public id?: number,
    public mac?: string,
    public inventoryNumber?: string,
    public location?: string,
    public hostname?: string,
    public webLogin?: string,
    public webPassword?: string,
    public dhcpEnabled?: boolean,
    public ipAddress?: string,
    public subnetMask?: string,
    public defaultGw?: string,
    public dns1?: string,
    public dns2?: string,
    public provisioningMode?: ProvisioningMode,
    public provisioningUrl?: string,
    public ntpServer?: string,
    public notes?: string,
    public settings?: ISetting[],
    public voipAccounts?: IVoipAccount[],
    public children?: IDevice[],
    public modelName?: string,
    public modelId?: number,
    public responsiblePersonLastName?: string,
    public responsiblePersonFullName?: string,
    public responsiblePersonId?: number,
    public parentId?: number
  ) {
    this.dhcpEnabled = this.dhcpEnabled || false;
  }
}
