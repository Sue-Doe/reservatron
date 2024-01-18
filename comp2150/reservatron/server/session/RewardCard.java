package comp2150.reservatron.server.session;

public class RewardCard {
    private static int nextCardNumber = 1;
    private static final int BONUS_THRESHOLD = 7;
    private String cardNumber;
    private int points;

    public RewardCard() {
        cardNumber = String.format("%07d", nextCardNumber);
        nextCardNumber++;
        points = 0;
    }
    // getting called on cancel and reserve rn, if cancel i need to minus
    // implement the double point business
    public void pointUpdate(int numNights) {
        int add = numNights;
        if(numNights >= BONUS_THRESHOLD) {
            add += numNights;
        }
        this.points += add;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String toString() {
        return "(" + cardNumber + ")" + " Balance: " + points;
    }
    public void revokePoints(int duration) {
        int remove = duration;
        if(duration >= BONUS_THRESHOLD) {
            remove += duration;
        }
        this.points -= remove;
    }
}
