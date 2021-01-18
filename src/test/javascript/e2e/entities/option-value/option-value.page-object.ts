import { element, by, ElementFinder } from 'protractor';

export class OptionValueComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-option-value div table .btn-danger'));
  title = element.all(by.css('jhi-option-value div h2#page-heading span')).first();
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

export class OptionValueUpdatePage {
  pageTitle = element(by.id('jhi-option-value-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  valueInput = element(by.id('field_value'));

  optionSelect = element(by.id('field_option'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setValueInput(value: string): Promise<void> {
    await this.valueInput.sendKeys(value);
  }

  async getValueInput(): Promise<string> {
    return await this.valueInput.getAttribute('value');
  }

  async optionSelectLastOption(): Promise<void> {
    await this.optionSelect.all(by.tagName('option')).last().click();
  }

  async optionSelectOption(option: string): Promise<void> {
    await this.optionSelect.sendKeys(option);
  }

  getOptionSelect(): ElementFinder {
    return this.optionSelect;
  }

  async getOptionSelectedOption(): Promise<string> {
    return await this.optionSelect.element(by.css('option:checked')).getText();
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

export class OptionValueDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-optionValue-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-optionValue'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
