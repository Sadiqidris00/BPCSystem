public class TherapySession {
    // Manages the appointments sessions functionalities
    // Book
    // Cancel
    // Complete etc
    
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
    public void assignMember(Member member) { 
        this.member = member; 
    }
    public void recordCompletion() { 
        this.completed = true; 
    }
    public String getServiceType() { 
        return serviceType; 
    }
    public String getSessionTime() { 
        return sessionTime; 
    }
     public void releaseReservation() {
        this.member = null;
    }
    public Member getMember() {
        return member;
    }

    public boolean isCompleted() {
        return completed;
    }
    @Override
    public String toString() {
        return "Time: " + sessionTime + " | Service: " + serviceType + " | Member: " +
                (member != null ? member.getFullName() : "None") + " | Status: " +
                (hasReservation() ? (completed ? "Completed" : "Reserved") : "Available");
    }
}