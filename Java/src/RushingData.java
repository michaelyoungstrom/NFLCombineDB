public class RushingData {

    private String rushingId;
    private String playerId;
    private int yards;
    private int touchdowns;
    private int longest;
    private float yardsPerAttempt;
    private float yardsPerGame;

    //Constructor
    public RushingData(String rushingId, String playerId, int yards, int touchdowns, int longest, float yardsPerAttempt,
                       float yardsPerGame){
        this.rushingId = rushingId;
        this.playerId = playerId;
        this.yards = yards;
        this.touchdowns = touchdowns;
        this.longest = longest;
        this.yardsPerAttempt = yardsPerAttempt;
        this.yardsPerGame = yardsPerGame;
    }
}
