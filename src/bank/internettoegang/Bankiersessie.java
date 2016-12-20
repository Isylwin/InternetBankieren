package bank.internettoegang;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;

import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.RemotePublisher;

public class Bankiersessie implements IBankiersessie {

	private static final long serialVersionUID = 1L;
	private long laatsteAanroep;
	private int reknr;
	private IBank bank;
	private IRemotePublisherForDomain publisher;

	public Bankiersessie(int reknr, IBank bank, IRemotePublisherForDomain publisher) throws RemoteException {
		laatsteAanroep = System.currentTimeMillis();
		this.reknr = reknr;
		this.bank = bank;
		this.publisher = publisher;

		publisher.registerProperty(reknr + "");
	}

	public boolean isGeldig() {
		return System.currentTimeMillis() - laatsteAanroep < GELDIGHEIDSDUUR;
	}

	@Override
	public boolean maakOver(int bestemming, Money bedrag)
			throws NumberDoesntExistException, InvalidSessionException,
			RemoteException {
		
		updateLaatsteAanroep();
		
		if (reknr == bestemming)
			throw new RuntimeException(
					"source and destination must be different");
		if (!bedrag.isPositive())
			throw new RuntimeException("amount must be positive");
		
		boolean result = bank.maakOver(reknr, bestemming, bedrag);

		if(!result) {
			return false;
		}

		try {
			publisher.inform(reknr + "", null, getRekening());
			publisher.inform(bestemming + "", null,  bank.getRekening(bestemming));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		return true;
	}

	private void updateLaatsteAanroep() throws InvalidSessionException {
		if (!isGeldig()) {
			throw new InvalidSessionException("session has been expired");
		}
		
		laatsteAanroep = System.currentTimeMillis();
	}

	@Override
	public IRekening getRekening() throws InvalidSessionException,
			RemoteException {

		updateLaatsteAanroep();
		
		return bank.getRekening(reknr);
	}

	@Override
	public void logUit() throws RemoteException {
		publisher.unregisterProperty(reknr + "");
		//UnicastRemoteObject.unexportObject(this, true);
	}

}
