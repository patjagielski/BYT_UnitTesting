package b_Money;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100, DKK10;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
		DKK10 = new Money(10000, DKK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(0, EUR0.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("1000 EUR", EUR10.toString());
	}

	@Test
	public void testUniversalValue() {
		assertEquals(1500, (int)EUR10.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertEquals(false, EUR0.equals(EUR10));
		assertEquals(true, EUR10.equals(SEK100));
	}

	@Test
	public void testAdd() {
		assertEquals(2000 +  " EUR", EUR10.add(SEK100).toString());
	}

	@Test
	public void testSub() {
		assertEquals(0 +  " EUR", EUR10.sub(SEK100).toString());
	}

	@Test
	public void testIsZero() {
		assertEquals(true, EUR0.isZero());
		assertEquals(false, SEK100.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals(-1000 + " EUR", EUR10.negate().toString());
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, EUR0.compareTo(EUR0));
		assertEquals(-1, DKK10.compareTo(SEK100));
		assertEquals(1, EUR10.compareTo(DKK10));
	}
}
