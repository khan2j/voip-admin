import { element, by, ElementFinder } from 'protractor';

export class VoipAccountComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-voip-account div table .btn-danger'));
  title = element.all(by.css('jhi-voip-account div h2#page-heading span')).first();
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

export class VoipAccountUpdatePage {
  pageTitle = element(by.id('jhi-voip-account-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  manuallyCreatedInput = element(by.id('field_manuallyCreated'));
  usernameInput = element(by.id('field_username'));
  passwordInput = element(by.id('field_password'));
  sipServerInput = element(by.id('field_sipServer'));
  sipPortInput = element(by.id('field_sipPort'));
  lineEnableInput = element(by.id('field_lineEnable'));
  lineNumberInput = element(by.id('field_lineNumber'));

  asteriskAccountSelect = element(by.id('field_asteriskAccount'));
  deviceSelect = element(by.id('field_device'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  getManuallyCreatedInput(): ElementFinder {
    return this.manuallyCreatedInput;
  }

  async setUsernameInput(username: string): Promise<void> {
    await this.usernameInput.sendKeys(username);
  }

  async getUsernameInput(): Promise<string> {
    return await this.usernameInput.getAttribute('value');
  }

  async setPasswordInput(password: string): Promise<void> {
    await this.passwordInput.sendKeys(password);
  }

  async getPasswordInput(): Promise<string> {
    return await this.passwordInput.getAttribute('value');
  }

  async setSipServerInput(sipServer: string): Promise<void> {
    await this.sipServerInput.sendKeys(sipServer);
  }

  async getSipServerInput(): Promise<string> {
    return await this.sipServerInput.getAttribute('value');
  }

  async setSipPortInput(sipPort: string): Promise<void> {
    await this.sipPortInput.sendKeys(sipPort);
  }

  async getSipPortInput(): Promise<string> {
    return await this.sipPortInput.getAttribute('value');
  }

  getLineEnableInput(): ElementFinder {
    return this.lineEnableInput;
  }

  async setLineNumberInput(lineNumber: string): Promise<void> {
    await this.lineNumberInput.sendKeys(lineNumber);
  }

  async getLineNumberInput(): Promise<string> {
    return await this.lineNumberInput.getAttribute('value');
  }

  async asteriskAccountSelectLastOption(): Promise<void> {
    await this.asteriskAccountSelect.all(by.tagName('option')).last().click();
  }

  async asteriskAccountSelectOption(option: string): Promise<void> {
    await this.asteriskAccountSelect.sendKeys(option);
  }

  getAsteriskAccountSelect(): ElementFinder {
    return this.asteriskAccountSelect;
  }

  async getAsteriskAccountSelectedOption(): Promise<string> {
    return await this.asteriskAccountSelect.element(by.css('option:checked')).getText();
  }

  async deviceSelectLastOption(): Promise<void> {
    await this.deviceSelect.all(by.tagName('option')).last().click();
  }

  async deviceSelectOption(option: string): Promise<void> {
    await this.deviceSelect.sendKeys(option);
  }

  getDeviceSelect(): ElementFinder {
    return this.deviceSelect;
  }

  async getDeviceSelectedOption(): Promise<string> {
    return await this.deviceSelect.element(by.css('option:checked')).getText();
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

export class VoipAccountDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-voipAccount-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-voipAccount'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
