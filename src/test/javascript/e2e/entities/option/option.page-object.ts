import { element, by, ElementFinder } from 'protractor';

export class OptionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-option div table .btn-danger'));
  title = element.all(by.css('jhi-option div h2#page-heading span')).first();
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

export class OptionUpdatePage {
  pageTitle = element(by.id('jhi-option-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codeInput = element(by.id('field_code'));
  descrInput = element(by.id('field_descr'));
  valueTypeSelect = element(by.id('field_valueType'));
  multipleInput = element(by.id('field_multiple'));

  modelsSelect = element(by.id('field_models'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeInput(code: string): Promise<void> {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput(): Promise<string> {
    return await this.codeInput.getAttribute('value');
  }

  async setDescrInput(descr: string): Promise<void> {
    await this.descrInput.sendKeys(descr);
  }

  async getDescrInput(): Promise<string> {
    return await this.descrInput.getAttribute('value');
  }

  async setValueTypeSelect(valueType: string): Promise<void> {
    await this.valueTypeSelect.sendKeys(valueType);
  }

  async getValueTypeSelect(): Promise<string> {
    return await this.valueTypeSelect.element(by.css('option:checked')).getText();
  }

  async valueTypeSelectLastOption(): Promise<void> {
    await this.valueTypeSelect.all(by.tagName('option')).last().click();
  }

  getMultipleInput(): ElementFinder {
    return this.multipleInput;
  }

  async modelsSelectLastOption(): Promise<void> {
    await this.modelsSelect.all(by.tagName('option')).last().click();
  }

  async modelsSelectOption(option: string): Promise<void> {
    await this.modelsSelect.sendKeys(option);
  }

  getModelsSelect(): ElementFinder {
    return this.modelsSelect;
  }

  async getModelsSelectedOption(): Promise<string> {
    return await this.modelsSelect.element(by.css('option:checked')).getText();
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

export class OptionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-option-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-option'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
