package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("newAccount");
		assertEquals(0,(int)SweBank.getBalance("newAccount"));
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika",new Money(10000, SEK));
		assertEquals(10000, (int)SweBank.getBalance("Ulrika"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.withdraw("Ulrika",new Money(20000, SEK));
		assertEquals(-20000, (int)SweBank.getBalance("Ulrika"));
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(0, (int)Nordea.getBalance("Bob"));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(2000, SEK));
		SweBank.transfer("Ulrika","Bob",new Money(1000, SEK));
		assertEquals(1000, (int)SweBank.getBalance("Ulrika"));
		assertEquals(1000, (int)SweBank.getBalance("Bob"));

	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(2000, SEK));
		SweBank.addTimedPayment("Ulrika", "test", 1,1, new Money(2000, SEK), DanskeBank, "Gertrud");
		SweBank.tick();
		assertEquals(0, (int)SweBank.getBalance("Ulrika"));
		assertEquals(1500, (int)DanskeBank.getBalance("Gertrud"));
		SweBank.removeTimedPayment("Ulrika", "test");
		SweBank.tick();
		assertEquals(0, (int)SweBank.getBalance("Ulrika"));
		assertEquals(1500, (int)DanskeBank.getBalance("Gertrud"));
	}
}
