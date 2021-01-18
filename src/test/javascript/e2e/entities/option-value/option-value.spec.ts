import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OptionValueComponentsPage, OptionValueDeleteDialog, OptionValueUpdatePage } from './option-value.page-object';

const expect = chai.expect;

describe('OptionValue e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let optionValueComponentsPage: OptionValueComponentsPage;
  let optionValueUpdatePage: OptionValueUpdatePage;
  let optionValueDeleteDialog: OptionValueDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OptionValues', async () => {
    await navBarPage.goToEntity('option-value');
    optionValueComponentsPage = new OptionValueComponentsPage();
    await browser.wait(ec.visibilityOf(optionValueComponentsPage.title), 5000);
    expect(await optionValueComponentsPage.getTitle()).to.eq('voipadminApp.optionValue.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(optionValueComponentsPage.entities), ec.visibilityOf(optionValueComponentsPage.noResult)),
      1000
    );
  });

  it('should load create OptionValue page', async () => {
    await optionValueComponentsPage.clickOnCreateButton();
    optionValueUpdatePage = new OptionValueUpdatePage();
    expect(await optionValueUpdatePage.getPageTitle()).to.eq('voipadminApp.optionValue.home.createOrEditLabel');
    await optionValueUpdatePage.cancel();
  });

  it('should create and save OptionValues', async () => {
    const nbButtonsBeforeCreate = await optionValueComponentsPage.countDeleteButtons();

    await optionValueComponentsPage.clickOnCreateButton();

    await promise.all([optionValueUpdatePage.setValueInput('value'), optionValueUpdatePage.optionSelectLastOption()]);

    expect(await optionValueUpdatePage.getValueInput()).to.eq('value', 'Expected Value value to be equals to value');

    await optionValueUpdatePage.save();
    expect(await optionValueUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await optionValueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last OptionValue', async () => {
    const nbButtonsBeforeDelete = await optionValueComponentsPage.countDeleteButtons();
    await optionValueComponentsPage.clickOnLastDeleteButton();

    optionValueDeleteDialog = new OptionValueDeleteDialog();
    expect(await optionValueDeleteDialog.getDialogTitle()).to.eq('voipadminApp.optionValue.delete.question');
    await optionValueDeleteDialog.clickOnConfirmButton();

    expect(await optionValueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
