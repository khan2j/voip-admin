import { element, by, ElementFinder } from 'protractor';

export class ResponsiblePersonComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-responsible-person div table .btn-danger'));
  title = element.all(by.css('jhi-responsible-person div h2#page-heading span')).first();
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

export class ResponsiblePersonUpdatePage {
  pageTitle = element(by.id('jhi-responsible-person-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codeInput = element(by.id('field_code'));
  firstNameInput = element(by.id('field_firstName'));
  secondNameInput = element(by.id('field_secondName'));
  lastNameInput = element(by.id('field_lastName'));
  positionInput = element(by.id('field_position'));
  roomInput = element(by.id('field_room'));

  departmentSelect = element(by.id('field_department'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeInput(code: string): Promise<void> {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput(): Promise<string> {
    return await this.codeInput.getAttribute('value');
  }

  async setFirstNameInput(firstName: string): Promise<void> {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput(): Promise<string> {
    return await this.firstNameInput.getAttribute('value');
  }

  async setSecondNameInput(secondName: string): Promise<void> {
    await this.secondNameInput.sendKeys(secondName);
  }

  async getSecondNameInput(): Promise<string> {
    return await this.secondNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName: string): Promise<void> {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput(): Promise<string> {
    return await this.lastNameInput.getAttribute('value');
  }

  async setPositionInput(position: string): Promise<void> {
    await this.positionInput.sendKeys(position);
  }

  async getPositionInput(): Promise<string> {
    return await this.positionInput.getAttribute('value');
  }

  async setRoomInput(room: string): Promise<void> {
    await this.roomInput.sendKeys(room);
  }

  async getRoomInput(): Promise<string> {
    return await this.roomInput.getAttribute('value');
  }

  async departmentSelectLastOption(): Promise<void> {
    await this.departmentSelect.all(by.tagName('option')).last().click();
  }

  async departmentSelectOption(option: string): Promise<void> {
    await this.departmentSelect.sendKeys(option);
  }

  getDepartmentSelect(): ElementFinder {
    return this.departmentSelect;
  }

  async getDepartmentSelectedOption(): Promise<string> {
    return await this.departmentSelect.element(by.css('option:checked')).getText();
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

export class ResponsiblePersonDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-responsiblePerson-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-responsiblePerson'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
