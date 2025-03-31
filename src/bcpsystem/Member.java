import java.util.ArrayList;
import java.util.List;

public class Member {
    private int memberId;
    private String fullName;
    private List<TherapySession> reservedSessions = new ArrayList<>();
    private List<TherapySession> scheduledSessions = new ArrayList<>();

    public Member(int memberId, String fullName) {
        this.memberId = memberId;
        this.fullName = fullName;
    }
    
    public List<TherapySession> getReservedSessions() {
        return reservedSessions;
    }
    public int getMemberId() { return memberId; }
    public String getFullName() { return fullName; }
    public List<TherapySession> getScheduledSessions() { return scheduledSessions; }
    public void reserveSession(TherapySession session) { reservedSessions.add(session); }
}
