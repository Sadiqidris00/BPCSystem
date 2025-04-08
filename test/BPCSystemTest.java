import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class BPCSystemTest {
    private BPCSystem system;

    @Before
    public void setUp() {
        system = new BPCSystem(); // Initializes with 7 specialists and 20 members
    }

    @Test
    public void testListSpecialists() {
        List<Specialist> specialists = system.getSpecialists();
        assertEquals("Should have 7 specialists", 7, specialists.size());
        assertTrue("Dr. Lee should be in the specialist list",
                specialists.stream().anyMatch(s -> s.getSpecialistName().equals("Dr. Lee")));
    }

    @Test
    public void testScheduleSession() {
        Member member = system.locateMemberById(101); // Sophia Adams
        Specialist specialist = system.locateSpecialistByName("Dr. Lee");
        TherapySession session = specialist.getSessionList().stream()
            .filter(s -> s.getSessionTime().equals("Monday 9 AM") && !s.hasReservation())
            .findFirst().orElse(null);

        assertNotNull("Member 101 should exist", member);
        assertNotNull("Dr. Lee should exist", specialist);
        assertNotNull("Monday 9 AM session should exist and be available", session);

        session.assignMember(member);
        member.reserveSession(session);

        assertTrue("Session should be reserved", session.hasReservation());
        assertEquals("Session should be assigned to Member 101", member, session.getMember());
        assertEquals("Member should have 1 reserved session", 1, member.getReservedSessions().size());
    }

    @Test
    public void testMarkSessionAsAttended() {
        Member member = system.locateMemberById(101);
        Specialist specialist = system.locateSpecialistByName("Dr. Lee");
        TherapySession session = specialist.getSessionList().stream()
            .filter(s -> s.getSessionTime().equals("Monday 9 AM") && !s.hasReservation())
            .findFirst().orElse(null);

        session.assignMember(member);
        member.reserveSession(session);

        assertFalse("Session should not be completed initially", session.isCompleted());
        session.recordCompletion();
        assertTrue("Session should be marked as completed", session.isCompleted());
        assertEquals("Member ownership should remain intact", member, session.getMember());
    }

    @Test
    public void testCancelNonCompletedSession() {
        Member member = system.locateMemberById(102); // Liam Brown
        Specialist specialist = system.locateSpecialistByName("Dr. Patel");
        TherapySession session = specialist.getSessionList().stream()
            .filter(s -> s.getSessionTime().equals("Tuesday 10 AM") && !s.hasReservation())
            .findFirst().orElse(null);

        session.assignMember(member);
        member.reserveSession(session);

        assertEquals("Member should have 1 session before cancellation", 1, member.getReservedSessions().size());
        if (session.getMember() == member && !session.isCompleted()) {
            member.getReservedSessions().remove(session);
            session.releaseReservation();
        }

        assertEquals("Session should be canceled", 0, member.getReservedSessions().size());
        assertNull("Session should have no member after cancellation", session.getMember());
    }

    @Test
    public void testCannotCancelCompletedSession() {
        Member member = system.locateMemberById(101);
        Specialist specialist = system.locateSpecialistByName("Dr. Lee");
        TherapySession session = specialist.getSessionList().stream()
            .filter(s -> s.getSessionTime().equals("Monday 9 AM") && !s.hasReservation())
            .findFirst().orElse(null);

        session.assignMember(member);
        member.reserveSession(session);
        session.recordCompletion();

        assertTrue("Session should be completed", session.isCompleted());
        boolean canCancel = !session.isCompleted() && session.getMember() == member;
        assertFalse("Should not be able to cancel a completed session", canCancel);
        assertEquals("Session should still be in reserved list", 1, member.getReservedSessions().size());
    }

    @Test
    public void testOwnershipRestriction() {
        Member member101 = system.locateMemberById(101); // Sophia Adams
        Member member102 = system.locateMemberById(102); // Liam Brown
        Specialist specialist = system.locateSpecialistByName("Dr. Lee");
        TherapySession session = specialist.getSessionList().stream()
            .filter(s -> s.getSessionTime().equals("Monday 9 AM") && !s.hasReservation())
            .findFirst().orElse(null);

        session.assignMember(member101);
        member101.reserveSession(session);

        boolean canCancel = session.getMember() == member102 && !session.isCompleted();
        assertFalse("Member 102 should not be able to cancel Member 101’s session", canCancel);
        assertEquals("Session should remain reserved", 1, member101.getReservedSessions().size());
    }

    @Test
    public void testSearchByFocusArea() {
        List<Specialist> matches = system.getSpecialists().stream()
            .filter(s -> s.getFocusArea().toLowerCase().contains("pediatric"))
            .toList();
        assertEquals("Should find 1 specialist with 'Pediatric' focus", 1, matches.size());
        assertEquals("Dr. Patel should match 'Pediatric'", "Dr. Patel", matches.get(0).getSpecialistName());
    }

    @Test
    public void testShowAllSessions() {
        int totalSessions = system.getSpecialists().stream()
            .mapToInt(s -> s.getSessionList().size())
            .sum();
        assertEquals("Should have 7 sessions initially (1 per specialist)", 7, totalSessions);
    }
}