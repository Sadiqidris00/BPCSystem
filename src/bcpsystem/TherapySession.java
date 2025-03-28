public class TherapySession {
    private Specialist specialist;
    private String serviceType;
    private String sessionTime;
    private Member member; 
    private boolean completed;

    public TherapySession(Specialist specialist, String serviceType, String sessionTime) {
        this.specialist = specialist;
        this.serviceType = serviceType;
        this.sessionTime = sessionTime;
        this.member = null;
        this.completed = false;
    }

    public boolean hasReservation() {
        return member != null;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void assignMember(Member member) {
        if (this.member == null) {
            this.member = member;
        }
    }

    public void releaseReservation() {
        this.member = null;
        this.completed = false;
    }

    public void recordCompletion() {
        if (member != null) {
            this.completed = true;
        } else {
            System.out.println("No member assigned to this session.");
        }
    }

    public String getServiceType() {
        return serviceType;
    }

    public Member getMember() {
        return member;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    @Override
    public String toString() {
        return "Session Time: " + sessionTime + " | Member: " +
               (member != null ? member.getFullName() : "Open") +
               " | Completed: " + (completed ? "Yes" : "No");
    }
}