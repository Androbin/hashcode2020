public final class Library {
    public final int id;
    public final int signupTime;
    public final int shipAmount;

    public Library(final int id, final int signupTime, final int shipAmount) {
        this.id = id;
        this.signupTime = signupTime;
        this.shipAmount = shipAmount;
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }
}