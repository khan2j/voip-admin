import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VoipAccountComponentsPage, VoipAccountDeleteDialog, VoipAccountUpdatePage } from './voip-account.page-object';

const expect = chai.expect;

describe('VoipAccount e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let voipAccountComponentsPage: VoipAccountComponentsPage;
  let voipAccountUpdatePage: VoipAccountUpdatePage;
  let voipAccountDeleteDialog: VoipAccountDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load VoipAccounts', async () => {
    await navBarPage.goToEntity('voip-account');
    voipAccountComponentsPage = new VoipAccountComponentsPage();
    await browser.wait(ec.visibilityOf(voipAccountComponentsPage.title), 5000);
    expect(await voipAccountComponentsPage.getTitle()).to.eq('voipadminApp.voipAccount.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(voipAccountComponentsPage.entities), ec.visibilityOf(voipAccountComponentsPage.noResult)),
      1000
    );
  });

  it('should load create VoipAccount page', async () => {
    await voipAccountComponentsPage.clickOnCreateButton();
    voipAccountUpdatePage = new VoipAccountUpdatePage();
    expect(await voipAccountUpdatePage.getPageTitle()).to.eq('voipadminApp.voipAccount.home.createOrEditLabel');
    await voipAccountUpdatePage.cancel();
  });

  it('should create and save VoipAccounts', async () => {
    const nbButtonsBeforeCreate = await voipAccountComponentsPage.countDeleteButtons();

    await voipAccountComponentsPage.clickOnCreateButton();

    await promise.all([
      voipAccountUpdatePage.setUsernameInput('username'),
      voipAccountUpdatePage.setPasswordInput('password'),
      voipAccountUpdatePage.setSipServerInput('sipServer'),
      voipAccountUpdatePage.setSipPortInput('sipPort'),
      voipAccountUpdatePage.setLineNumberInput('lineNumber'),
      voipAccountUpdatePage.asteriskAccountSelectLastOption(),
      voipAccountUpdatePage.deviceSelectLastOption(),
    ]);

    const selectedManuallyCreated = voipAccountUpdatePage.getManuallyCreatedInput();
    if (await selectedManuallyCreated.isSelected()) {
      await voipAccountUpdatePage.getManuallyCreatedInput().click();
      expect(await voipAccountUpdatePage.getManuallyCreatedInput().isSelected(), 'Expected manuallyCreated not to be selected').to.be.false;
    } else {
      await voipAccountUpdatePage.getManuallyCreatedInput().click();
      expect(await voipAccountUpdatePage.getManuallyCreatedInput().isSelected(), 'Expected manuallyCreated to be selected').to.be.true;
    }
    expect(await voipAccountUpdatePage.getUsernameInput()).to.eq('username', 'Expected Username value to be equals to username');
    expect(await voipAccountUpdatePage.getPasswordInput()).to.eq('password', 'Expected Password value to be equals to password');
    expect(await voipAccountUpdatePage.getSipServerInput()).to.eq('sipServer', 'Expected SipServer value to be equals to sipServer');
    expect(await voipAccountUpdatePage.getSipPortInput()).to.eq('sipPort', 'Expected SipPort value to be equals to sipPort');
    const selectedLineEnable = voipAccountUpdatePage.getLineEnableInput();
    if (await selectedLineEnable.isSelected()) {
      await voipAccountUpdatePage.getLineEnableInput().click();
      expect(await voipAccountUpdatePage.getLineEnableInput().isSelected(), 'Expected lineEnable not to be selected').to.be.false;
    } else {
      await voipAccountUpdatePage.getLineEnableInput().click();
      expect(await voipAccountUpdatePage.getLineEnableInput().isSelected(), 'Expected lineEnable to be selected').to.be.true;
    }
    expect(await voipAccountUpdatePage.getLineNumberInput()).to.eq('lineNumber', 'Expected LineNumber value to be equals to lineNumber');

    await voipAccountUpdatePage.save();
    expect(await voipAccountUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await voipAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last VoipAccount', async () => {
    const nbButtonsBeforeDelete = await voipAccountComponentsPage.countDeleteButtons();
    await voipAccountComponentsPage.clickOnLastDeleteButton();

    voipAccountDeleteDialog = new VoipAccountDeleteDialog();
    expect(await voipAccountDeleteDialog.getDialogTitle()).to.eq('voipadminApp.voipAccount.delete.question');
    await voipAccountDeleteDialog.clickOnConfirmButton();

    expect(await voipAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
