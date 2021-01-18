import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AsteriskAccountComponentsPage, AsteriskAccountDeleteDialog, AsteriskAccountUpdatePage } from './asterisk-account.page-object';

const expect = chai.expect;

describe('AsteriskAccount e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let asteriskAccountComponentsPage: AsteriskAccountComponentsPage;
  let asteriskAccountUpdatePage: AsteriskAccountUpdatePage;
  let asteriskAccountDeleteDialog: AsteriskAccountDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AsteriskAccounts', async () => {
    await navBarPage.goToEntity('asterisk-account');
    asteriskAccountComponentsPage = new AsteriskAccountComponentsPage();
    await browser.wait(ec.visibilityOf(asteriskAccountComponentsPage.title), 5000);
    expect(await asteriskAccountComponentsPage.getTitle()).to.eq('voipadminApp.asteriskAccount.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(asteriskAccountComponentsPage.entities), ec.visibilityOf(asteriskAccountComponentsPage.noResult)),
      1000
    );
  });

  it('should load create AsteriskAccount page', async () => {
    await asteriskAccountComponentsPage.clickOnCreateButton();
    asteriskAccountUpdatePage = new AsteriskAccountUpdatePage();
    expect(await asteriskAccountUpdatePage.getPageTitle()).to.eq('voipadminApp.asteriskAccount.home.createOrEditLabel');
    await asteriskAccountUpdatePage.cancel();
  });

  it('should create and save AsteriskAccounts', async () => {
    const nbButtonsBeforeCreate = await asteriskAccountComponentsPage.countDeleteButtons();

    await asteriskAccountComponentsPage.clickOnCreateButton();

    await promise.all([asteriskAccountUpdatePage.setUsernameInput('username'), asteriskAccountUpdatePage.setAsteriskIdInput('asteriskId')]);

    expect(await asteriskAccountUpdatePage.getUsernameInput()).to.eq('username', 'Expected Username value to be equals to username');
    expect(await asteriskAccountUpdatePage.getAsteriskIdInput()).to.eq(
      'asteriskId',
      'Expected AsteriskId value to be equals to asteriskId'
    );

    await asteriskAccountUpdatePage.save();
    expect(await asteriskAccountUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await asteriskAccountComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AsteriskAccount', async () => {
    const nbButtonsBeforeDelete = await asteriskAccountComponentsPage.countDeleteButtons();
    await asteriskAccountComponentsPage.clickOnLastDeleteButton();

    asteriskAccountDeleteDialog = new AsteriskAccountDeleteDialog();
    expect(await asteriskAccountDeleteDialog.getDialogTitle()).to.eq('voipadminApp.asteriskAccount.delete.question');
    await asteriskAccountDeleteDialog.clickOnConfirmButton();

    expect(await asteriskAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
