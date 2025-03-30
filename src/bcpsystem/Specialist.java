import java.util.ArrayList;
import java.util.List;

public class Specialist {
    private String specialistName;
    private String focusArea;
    private List<TherapySession> sessionList = new ArrayList<>();

    public Specialist(String specialistName, String focusArea) {
        this.specialistName = specialistName;
        this.focusArea = focusArea;
    }

    public String getSpecialistName() { return specialistName; }
    public String getFocusArea() { return focusArea; }
    public List<TherapySession> getSessionList() { return sessionList; }
    public void scheduleNewSession(TherapySession session) { sessionList.add(session); }
}