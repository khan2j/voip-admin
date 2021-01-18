import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OptionComponentsPage, OptionDeleteDialog, OptionUpdatePage } from './option.page-object';

const expect = chai.expect;

describe('Option e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let optionComponentsPage: OptionComponentsPage;
  let optionUpdatePage: OptionUpdatePage;
  let optionDeleteDialog: OptionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Options', async () => {
    await navBarPage.goToEntity('option');
    optionComponentsPage = new OptionComponentsPage();
    await browser.wait(ec.visibilityOf(optionComponentsPage.title), 5000);
    expect(await optionComponentsPage.getTitle()).to.eq('voipadminApp.option.home.title');
    await browser.wait(ec.or(ec.visibilityOf(optionComponentsPage.entities), ec.visibilityOf(optionComponentsPage.noResult)), 1000);
  });

  it('should load create Option page', async () => {
    await optionComponentsPage.clickOnCreateButton();
    optionUpdatePage = new OptionUpdatePage();
    expect(await optionUpdatePage.getPageTitle()).to.eq('voipadminApp.option.home.createOrEditLabel');
    await optionUpdatePage.cancel();
  });

  it('should create and save Options', async () => {
    const nbButtonsBeforeCreate = await optionComponentsPage.countDeleteButtons();

    await optionComponentsPage.clickOnCreateButton();

    await promise.all([
      optionUpdatePage.setCodeInput('code'),
      optionUpdatePage.setDescrInput('descr'),
      optionUpdatePage.valueTypeSelectLastOption(),
      // optionUpdatePage.modelsSelectLastOption(),
    ]);

    expect(await optionUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await optionUpdatePage.getDescrInput()).to.eq('descr', 'Expected Descr value to be equals to descr');
    const selectedMultiple = optionUpdatePage.getMultipleInput();
    if (await selectedMultiple.isSelected()) {
      await optionUpdatePage.getMultipleInput().click();
      expect(await optionUpdatePage.getMultipleInput().isSelected(), 'Expected multiple not to be selected').to.be.false;
    } else {
      await optionUpdatePage.getMultipleInput().click();
      expect(await optionUpdatePage.getMultipleInput().isSelected(), 'Expected multiple to be selected').to.be.true;
    }

    await optionUpdatePage.save();
    expect(await optionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await optionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Option', async () => {
    const nbButtonsBeforeDelete = await optionComponentsPage.countDeleteButtons();
    await optionComponentsPage.clickOnLastDeleteButton();

    optionDeleteDialog = new OptionDeleteDialog();
    expect(await optionDeleteDialog.getDialogTitle()).to.eq('voipadminApp.option.delete.question');
    await optionDeleteDialog.clickOnConfirmButton();

    expect(await optionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
