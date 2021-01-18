import { element, by, ElementFinder } from 'protractor';

export class SettingComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-setting div table .btn-danger'));
  title = element.all(by.css('jhi-setting div h2#page-heading span')).first();
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

export class SettingUpdatePage {
  pageTitle = element(by.id('jhi-setting-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  textValueInput = element(by.id('field_textValue'));

  optionSelect = element(by.id('field_option'));
  selectedValuesSelect = element(by.id('field_selectedValues'));
  deviceSelect = element(by.id('field_device'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTextValueInput(textValue: string): Promise<void> {
    await this.textValueInput.sendKeys(textValue);
  }

  async getTextValueInput(): Promise<string> {
    return await this.textValueInput.getAttribute('value');
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

  async selectedValuesSelectLastOption(): Promise<void> {
    await this.selectedValuesSelect.all(by.tagName('option')).last().click();
  }

  async selectedValuesSelectOption(option: string): Promise<void> {
    await this.selectedValuesSelect.sendKeys(option);
  }

  getSelectedValuesSelect(): ElementFinder {
    return this.selectedValuesSelect;
  }

  async getSelectedValuesSelectedOption(): Promise<string> {
    return await this.selectedValuesSelect.element(by.css('option:checked')).getText();
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

export class SettingDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-setting-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-setting'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
