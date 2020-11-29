package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("Test", 1,1, new Money(100, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("Test"));
		testAccount.removeTimedPayment("Test");
		assertFalse(testAccount.timedPaymentExists("Test"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("Test",1,2,new Money(100,SEK),SweBank,"Alice");
		testAccount.tick();
		testAccount.tick();
		assertEquals(9999900,testAccount.getBalance().getAmount());
		assertEquals(1000100, (int)SweBank.getBalance("Alice"));
	}

	@Test
	public void testAddWithdraw() {
		assertEquals(10000000, testAccount.getBalance().getAmount());
		testAccount.withdraw(new Money(10000000,SEK));
		assertEquals(0, testAccount.getBalance().getAmount());
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(10000000, testAccount.getBalance().getAmount());
	}
}
