package testutil;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsCloseTo extends TypeSafeDiagnosingMatcher<Double> {

    private double comparison;
    private double allowedDeviation;

    private IsCloseTo(double comparison, Double allowedDeviation) {
        this.comparison = comparison;
        this.allowedDeviation = allowedDeviation == null ? 0.01 : allowedDeviation;
    }

    @Override
    public void describeTo(Description description) {
        final double percentage = allowedDeviation * 100;
        description.appendText(String.format("%s +/- %s%%", this.comparison, percentage));
    }

    @Override
    protected boolean matchesSafely(Double aDouble, Description mismatchDescription) {
        double deviation = Math.abs((comparison - aDouble) / comparison);
        mismatchDescription.appendText(String.format("was %f (%f)", aDouble, deviation));
        return deviation <= this.allowedDeviation;
    }

    public static IsCloseTo closeTo(double comparison, Double allowedDeviation) {
        return new IsCloseTo(comparison, allowedDeviation);
    }

    public static IsCloseTo closeTo(double comparison) {
        return new IsCloseTo(comparison, null);
    }

}