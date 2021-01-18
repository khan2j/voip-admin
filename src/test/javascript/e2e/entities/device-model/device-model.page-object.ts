import { element, by, ElementFinder } from 'protractor';

export class DeviceModelComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-device-model div table .btn-danger'));
  title = element.all(by.css('jhi-device-model div h2#page-heading span')).first();
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

export class DeviceModelUpdatePage {
  pageTitle = element(by.id('jhi-device-model-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  isConfigurableInput = element(by.id('field_isConfigurable'));
  linesCountInput = element(by.id('field_linesCount'));
  configTemplateInput = element(by.id('file_configTemplate'));
  firmwareFileInput = element(by.id('file_firmwareFile'));
  deviceTypeSelect = element(by.id('field_deviceType'));

  otherDeviceTypeSelect = element(by.id('field_otherDeviceType'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  getIsConfigurableInput(): ElementFinder {
    return this.isConfigurableInput;
  }

  async setLinesCountInput(linesCount: string): Promise<void> {
    await this.linesCountInput.sendKeys(linesCount);
  }

  async getLinesCountInput(): Promise<string> {
    return await this.linesCountInput.getAttribute('value');
  }

  async setConfigTemplateInput(configTemplate: string): Promise<void> {
    await this.configTemplateInput.sendKeys(configTemplate);
  }

  async getConfigTemplateInput(): Promise<string> {
    return await this.configTemplateInput.getAttribute('value');
  }

  async setFirmwareFileInput(firmwareFile: string): Promise<void> {
    await this.firmwareFileInput.sendKeys(firmwareFile);
  }

  async getFirmwareFileInput(): Promise<string> {
    return await this.firmwareFileInput.getAttribute('value');
  }

  async setDeviceTypeSelect(deviceType: string): Promise<void> {
    await this.deviceTypeSelect.sendKeys(deviceType);
  }

  async getDeviceTypeSelect(): Promise<string> {
    return await this.deviceTypeSelect.element(by.css('option:checked')).getText();
  }

  async deviceTypeSelectLastOption(): Promise<void> {
    await this.deviceTypeSelect.all(by.tagName('option')).last().click();
  }

  async otherDeviceTypeSelectLastOption(): Promise<void> {
    await this.otherDeviceTypeSelect.all(by.tagName('option')).last().click();
  }

  async otherDeviceTypeSelectOption(option: string): Promise<void> {
    await this.otherDeviceTypeSelect.sendKeys(option);
  }

  getOtherDeviceTypeSelect(): ElementFinder {
    return this.otherDeviceTypeSelect;
  }

  async getOtherDeviceTypeSelectedOption(): Promise<string> {
    return await this.otherDeviceTypeSelect.element(by.css('option:checked')).getText();
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

export class DeviceModelDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-deviceModel-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-deviceModel'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
