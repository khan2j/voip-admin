import { element, by, ElementFinder } from 'protractor';

export class AsteriskAccountComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-asterisk-account div table .btn-danger'));
  title = element.all(by.css('jhi-asterisk-account div h2#page-heading span')).first();
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

export class AsteriskAccountUpdatePage {
  pageTitle = element(by.id('jhi-asterisk-account-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  usernameInput = element(by.id('field_username'));
  asteriskIdInput = element(by.id('field_asteriskId'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUsernameInput(username: string): Promise<void> {
    await this.usernameInput.sendKeys(username);
  }

  async getUsernameInput(): Promise<string> {
    return await this.usernameInput.getAttribute('value');
  }

  async setAsteriskIdInput(asteriskId: string): Promise<void> {
    await this.asteriskIdInput.sendKeys(asteriskId);
  }

  async getAsteriskIdInput(): Promise<string> {
    return await this.asteriskIdInput.getAttribute('value');
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

export class AsteriskAccountDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-asteriskAccount-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-asteriskAccount'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
