import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DeviceComponentsPage, DeviceDeleteDialog, DeviceUpdatePage } from './device.page-object';

const expect = chai.expect;

describe('Device e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let deviceComponentsPage: DeviceComponentsPage;
  let deviceUpdatePage: DeviceUpdatePage;
  let deviceDeleteDialog: DeviceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Devices', async () => {
    await navBarPage.goToEntity('device');
    deviceComponentsPage = new DeviceComponentsPage();
    await browser.wait(ec.visibilityOf(deviceComponentsPage.title), 5000);
    expect(await deviceComponentsPage.getTitle()).to.eq('voipadminApp.device.home.title');
    await browser.wait(ec.or(ec.visibilityOf(deviceComponentsPage.entities), ec.visibilityOf(deviceComponentsPage.noResult)), 1000);
  });

  it('should load create Device page', async () => {
    await deviceComponentsPage.clickOnCreateButton();
    deviceUpdatePage = new DeviceUpdatePage();
    expect(await deviceUpdatePage.getPageTitle()).to.eq('voipadminApp.device.home.createOrEditLabel');
    await deviceUpdatePage.cancel();
  });

  it('should create and save Devices', async () => {
    const nbButtonsBeforeCreate = await deviceComponentsPage.countDeleteButtons();

    await deviceComponentsPage.clickOnCreateButton();

    await promise.all([
      deviceUpdatePage.setMacInput('mac'),
      deviceUpdatePage.setInventoryNumberInput('inventoryNumber'),
      deviceUpdatePage.setLocationInput('location'),
      deviceUpdatePage.setHostnameInput('hostname'),
      deviceUpdatePage.setWebLoginInput('webLogin'),
      deviceUpdatePage.setWebPasswordInput('webPassword'),
      deviceUpdatePage.setIpAddressInput('ipAddress'),
      deviceUpdatePage.setSubnetMaskInput('subnetMask'),
      deviceUpdatePage.setDefaultGwInput('defaultGw'),
      deviceUpdatePage.setDns1Input('dns1'),
      deviceUpdatePage.setDns2Input('dns2'),
      deviceUpdatePage.provisioningModeSelectLastOption(),
      deviceUpdatePage.setProvisioningUrlInput('provisioningUrl'),
      deviceUpdatePage.setNtpServerInput('ntpServer'),
      deviceUpdatePage.setNotesInput('notes'),
      deviceUpdatePage.modelSelectLastOption(),
      deviceUpdatePage.responsiblePersonSelectLastOption(),
      deviceUpdatePage.parentSelectLastOption(),
    ]);

    expect(await deviceUpdatePage.getMacInput()).to.eq('mac', 'Expected Mac value to be equals to mac');
    expect(await deviceUpdatePage.getInventoryNumberInput()).to.eq(
      'inventoryNumber',
      'Expected InventoryNumber value to be equals to inventoryNumber'
    );
    expect(await deviceUpdatePage.getLocationInput()).to.eq('location', 'Expected Location value to be equals to location');
    expect(await deviceUpdatePage.getHostnameInput()).to.eq('hostname', 'Expected Hostname value to be equals to hostname');
    expect(await deviceUpdatePage.getWebLoginInput()).to.eq('webLogin', 'Expected WebLogin value to be equals to webLogin');
    expect(await deviceUpdatePage.getWebPasswordInput()).to.eq('webPassword', 'Expected WebPassword value to be equals to webPassword');
    const selectedDhcpEnabled = deviceUpdatePage.getDhcpEnabledInput();
    if (await selectedDhcpEnabled.isSelected()) {
      await deviceUpdatePage.getDhcpEnabledInput().click();
      expect(await deviceUpdatePage.getDhcpEnabledInput().isSelected(), 'Expected dhcpEnabled not to be selected').to.be.false;
    } else {
      await deviceUpdatePage.getDhcpEnabledInput().click();
      expect(await deviceUpdatePage.getDhcpEnabledInput().isSelected(), 'Expected dhcpEnabled to be selected').to.be.true;
    }
    expect(await deviceUpdatePage.getIpAddressInput()).to.eq('ipAddress', 'Expected IpAddress value to be equals to ipAddress');
    expect(await deviceUpdatePage.getSubnetMaskInput()).to.eq('subnetMask', 'Expected SubnetMask value to be equals to subnetMask');
    expect(await deviceUpdatePage.getDefaultGwInput()).to.eq('defaultGw', 'Expected DefaultGw value to be equals to defaultGw');
    expect(await deviceUpdatePage.getDns1Input()).to.eq('dns1', 'Expected Dns1 value to be equals to dns1');
    expect(await deviceUpdatePage.getDns2Input()).to.eq('dns2', 'Expected Dns2 value to be equals to dns2');
    expect(await deviceUpdatePage.getProvisioningUrlInput()).to.eq(
      'provisioningUrl',
      'Expected ProvisioningUrl value to be equals to provisioningUrl'
    );
    expect(await deviceUpdatePage.getNtpServerInput()).to.eq('ntpServer', 'Expected NtpServer value to be equals to ntpServer');
    expect(await deviceUpdatePage.getNotesInput()).to.eq('notes', 'Expected Notes value to be equals to notes');

    await deviceUpdatePage.save();
    expect(await deviceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await deviceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Device', async () => {
    const nbButtonsBeforeDelete = await deviceComponentsPage.countDeleteButtons();
    await deviceComponentsPage.clickOnLastDeleteButton();

    deviceDeleteDialog = new DeviceDeleteDialog();
    expect(await deviceDeleteDialog.getDialogTitle()).to.eq('voipadminApp.device.delete.question');
    await deviceDeleteDialog.clickOnConfirmButton();

    expect(await deviceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
