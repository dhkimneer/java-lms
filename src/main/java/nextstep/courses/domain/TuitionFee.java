package nextstep.courses.domain;

import java.util.Objects;

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

    public Long getTuitionFee() {
        return tuitionFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TuitionFee that = (TuitionFee) o;
        return Objects.equals(tuitionFee, that.tuitionFee);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tuitionFee);
    }
}
