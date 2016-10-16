package tests;

import org.junit.Test;

import teamidentifier.KMeans;

import static org.junit.Assert.assertEquals;

public class KMeansTests {
	String message = "Robert";

	@Test
	public void distanceAnswer() {
		double x[] = { 21, 2, 4, 4 };
		double u[] = { 21, 3, 4, 5 };
		Double answer = new Double(0);

		try {
			answer = KMeans.distance(x, u);
		} catch (Exception e) {
		}

		assertEquals(answer.toString(), "2.0");
	}

	@Test
	public void distanceValidInput() {
		double x[] = { 21, 2, 4 };
		double u[] = { 21, 3, 4, 5 };
		boolean valid;

		try {
			KMeans.distance(x, u);
			valid = true;
		} catch (Exception e) {
			valid = false;
		}

		assertEquals(valid, false);
	}

}
