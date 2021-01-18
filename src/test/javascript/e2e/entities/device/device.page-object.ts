import { element, by, ElementFinder } from 'protractor';

export class DeviceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-device div table .btn-danger'));
  title = element.all(by.css('jhi-device div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class DeviceUpdatePage {
  pageTitle = element(by.id('jhi-device-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  macInput = element(by.id('field_mac'));
  inventoryNumberInput = element(by.id('field_inventoryNumber'));
  locationInput = element(by.id('field_location'));
  hostnameInput = element(by.id('field_hostname'));
  webLoginInput = element(by.id('field_webLogin'));
  webPasswordInput = element(by.id('field_webPassword'));
  dhcpEnabledInput = element(by.id('field_dhcpEnabled'));
  ipAddressInput = element(by.id('field_ipAddress'));
  subnetMaskInput = element(by.id('field_subnetMask'));
  defaultGwInput = element(by.id('field_defaultGw'));
  dns1Input = element(by.id('field_dns1'));
  dns2Input = element(by.id('field_dns2'));
  provisioningModeSelect = element(by.id('field_provisioningMode'));
  provisioningUrlInput = element(by.id('field_provisioningUrl'));
  ntpServerInput = element(by.id('field_ntpServer'));
  notesInput = element(by.id('field_notes'));

  modelSelect = element(by.id('field_model'));
  responsiblePersonSelect = element(by.id('field_responsiblePerson'));
  parentSelect = element(by.id('field_parent'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMacInput(mac: string): Promise<void> {
    await this.macInput.sendKeys(mac);
  }

  async getMacInput(): Promise<string> {
    return await this.macInput.getAttribute('value');
  }

  async setInventoryNumberInput(inventoryNumber: string): Promise<void> {
    await this.inventoryNumberInput.sendKeys(inventoryNumber);
  }

  async getInventoryNumberInput(): Promise<string> {
    return await this.inventoryNumberInput.getAttribute('value');
  }

  async setLocationInput(location: string): Promise<void> {
    await this.locationInput.sendKeys(location);
  }

  async getLocationInput(): Promise<string> {
    return await this.locationInput.getAttribute('value');
  }

  async setHostnameInput(hostname: string): Promise<void> {
    await this.hostnameInput.sendKeys(hostname);
  }

  async getHostnameInput(): Promise<string> {
    return await this.hostnameInput.getAttribute('value');
  }

  async setWebLoginInput(webLogin: string): Promise<void> {
    await this.webLoginInput.sendKeys(webLogin);
  }

  async getWebLoginInput(): Promise<string> {
    return await this.webLoginInput.getAttribute('value');
  }

  async setWebPasswordInput(webPassword: string): Promise<void> {
    await this.webPasswordInput.sendKeys(webPassword);
  }

  async getWebPasswordInput(): Promise<string> {
    return await this.webPasswordInput.getAttribute('value');
  }

  getDhcpEnabledInput(): ElementFinder {
    return this.dhcpEnabledInput;
  }

  async setIpAddressInput(ipAddress: string): Promise<void> {
    await this.ipAddressInput.sendKeys(ipAddress);
  }

  async getIpAddressInput(): Promise<string> {
    return await this.ipAddressInput.getAttribute('value');
  }

  async setSubnetMaskInput(subnetMask: string): Promise<void> {
    await this.subnetMaskInput.sendKeys(subnetMask);
  }

  async getSubnetMaskInput(): Promise<string> {
    return await this.subnetMaskInput.getAttribute('value');
  }

  async setDefaultGwInput(defaultGw: string): Promise<void> {
    await this.defaultGwInput.sendKeys(defaultGw);
  }

  async getDefaultGwInput(): Promise<string> {
    return await this.defaultGwInput.getAttribute('value');
  }

  async setDns1Input(dns1: string): Promise<void> {
    await this.dns1Input.sendKeys(dns1);
  }

  async getDns1Input(): Promise<string> {
    return await this.dns1Input.getAttribute('value');
  }

  async setDns2Input(dns2: string): Promise<void> {
    await this.dns2Input.sendKeys(dns2);
  }

  async getDns2Input(): Promise<string> {
    return await this.dns2Input.getAttribute('value');
  }

  async setProvisioningModeSelect(provisioningMode: string): Promise<void> {
    await this.provisioningModeSelect.sendKeys(provisioningMode);
  }

  async getProvisioningModeSelect(): Promise<string> {
    return await this.provisioningModeSelect.element(by.css('option:checked')).getText();
  }

  async provisioningModeSelectLastOption(): Promise<void> {
    await this.provisioningModeSelect.all(by.tagName('option')).last().click();
  }

  async setProvisioningUrlInput(provisioningUrl: string): Promise<void> {
    await this.provisioningUrlInput.sendKeys(provisioningUrl);
  }

  async getProvisioningUrlInput(): Promise<string> {
    return await this.provisioningUrlInput.getAttribute('value');
  }

  async setNtpServerInput(ntpServer: string): Promise<void> {
    await this.ntpServerInput.sendKeys(ntpServer);
  }

  async getNtpServerInput(): Promise<string> {
    return await this.ntpServerInput.getAttribute('value');
  }

  async setNotesInput(notes: string): Promise<void> {
    await this.notesInput.sendKeys(notes);
  }

  async getNotesInput(): Promise<string> {
    return await this.notesInput.getAttribute('value');
  }

  async modelSelectLastOption(): Promise<void> {
    await this.modelSelect.all(by.tagName('option')).last().click();
  }

  async modelSelectOption(option: string): Promise<void> {
    await this.modelSelect.sendKeys(option);
  }

  getModelSelect(): ElementFinder {
    return this.modelSelect;
  }

  async getModelSelectedOption(): Promise<string> {
    return await this.modelSelect.element(by.css('option:checked')).getText();
  }

  async responsiblePersonSelectLastOption(): Promise<void> {
    await this.responsiblePersonSelect.all(by.tagName('option')).last().click();
  }

  async responsiblePersonSelectOption(option: string): Promise<void> {
    await this.responsiblePersonSelect.sendKeys(option);
  }

  getResponsiblePersonSelect(): ElementFinder {
    return this.responsiblePersonSelect;
  }

  async getResponsiblePersonSelectedOption(): Promise<string> {
    return await this.responsiblePersonSelect.element(by.css('option:checked')).getText();
  }

  async parentSelectLastOption(): Promise<void> {
    await this.parentSelect.all(by.tagName('option')).last().click();
  }

  async parentSelectOption(option: string): Promise<void> {
    await this.parentSelect.sendKeys(option);
  }

  getParentSelect(): ElementFinder {
    return this.parentSelect;
  }

  async getParentSelectedOption(): Promise<string> {
    return await this.parentSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DeviceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-device-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-device'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
