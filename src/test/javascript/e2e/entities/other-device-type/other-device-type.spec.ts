import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OtherDeviceTypeComponentsPage, OtherDeviceTypeDeleteDialog, OtherDeviceTypeUpdatePage } from './other-device-type.page-object';

const expect = chai.expect;

describe('OtherDeviceType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let otherDeviceTypeComponentsPage: OtherDeviceTypeComponentsPage;
  let otherDeviceTypeUpdatePage: OtherDeviceTypeUpdatePage;
  let otherDeviceTypeDeleteDialog: OtherDeviceTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OtherDeviceTypes', async () => {
    await navBarPage.goToEntity('other-device-type');
    otherDeviceTypeComponentsPage = new OtherDeviceTypeComponentsPage();
    await browser.wait(ec.visibilityOf(otherDeviceTypeComponentsPage.title), 5000);
    expect(await otherDeviceTypeComponentsPage.getTitle()).to.eq('voipadminApp.otherDeviceType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(otherDeviceTypeComponentsPage.entities), ec.visibilityOf(otherDeviceTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create OtherDeviceType page', async () => {
    await otherDeviceTypeComponentsPage.clickOnCreateButton();
    otherDeviceTypeUpdatePage = new OtherDeviceTypeUpdatePage();
    expect(await otherDeviceTypeUpdatePage.getPageTitle()).to.eq('voipadminApp.otherDeviceType.home.createOrEditLabel');
    await otherDeviceTypeUpdatePage.cancel();
  });

  it('should create and save OtherDeviceTypes', async () => {
    const nbButtonsBeforeCreate = await otherDeviceTypeComponentsPage.countDeleteButtons();

    await otherDeviceTypeComponentsPage.clickOnCreateButton();

    await promise.all([otherDeviceTypeUpdatePage.setNameInput('name'), otherDeviceTypeUpdatePage.setDescriptionInput('description')]);

    expect(await otherDeviceTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await otherDeviceTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await otherDeviceTypeUpdatePage.save();
    expect(await otherDeviceTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await otherDeviceTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last OtherDeviceType', async () => {
    const nbButtonsBeforeDelete = await otherDeviceTypeComponentsPage.countDeleteButtons();
    await otherDeviceTypeComponentsPage.clickOnLastDeleteButton();

    otherDeviceTypeDeleteDialog = new OtherDeviceTypeDeleteDialog();
    expect(await otherDeviceTypeDeleteDialog.getDialogTitle()).to.eq('voipadminApp.otherDeviceType.delete.question');
    await otherDeviceTypeDeleteDialog.clickOnConfirmButton();

    expect(await otherDeviceTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
