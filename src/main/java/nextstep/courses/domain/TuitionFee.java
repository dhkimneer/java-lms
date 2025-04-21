package nextstep.courses.domain;

public class TuitionFee {

    private final Long tuitionFee;

    public TuitionFee(Long tuitionFee) {
        validateFee(tuitionFee);
        this.tuitionFee = tuitionFee;
    }

    private void validateFee(Long tuitionFee) {
        if (tuitionFee == null || tuitionFee <= 0) {
            throw new IllegalArgumentException("Invalid tuition fee");
        }
    }

    public boolean matches(Long paidAmount) {
        return paidAmount != null && paidAmount.equals(tuitionFee);
    }
}
