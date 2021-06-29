package learn.capstone.domain;

public class Validations {

    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isZeroOrLessThanZero(int value) {
        return (value <= 0);
    }
}
