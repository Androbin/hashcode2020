import java.util.*;
import java.lang.Math;

public final class SolverSanity {
	private SolverSanity() {
	}

	public static boolean sanity(final Input input, final List<Output> outputs) {
		int days = input.days;
		int maxSignupTs = 0;
		int maxScanTs = 0;

		for (final Output output : outputs) {
			int signupTime = output.library.signupTime;
			maxSignupTs += signupTime;
			maxScanTs = Math.max(maxScanTs, maxSignupTs + output.calcScanningTime());

		}

		// can all libraries be signed-up and books be scanned in the set number of days?
		return maxScanTs <= days;
	}
}