import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BPCSystem {
    private List<Specialist> specialists = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private JTextArea outputArea;
    private JFrame frame;

    public BPCSystem() {
        loadInitialData();
    }
    
    // Load hardcoded data: 7 specialists and 20 members
    private void loadInitialData() {
        // Add 7 specialists
        specialists.add(new Specialist("Dr. Lee", "Orthopedic Rehabilitation"));
        specialists.add(new Specialist("Dr. Patel", "Pediatric Physiotherapy"));
        specialists.add(new Specialist("Dr. Garcia", "Geriatric Care"));
        specialists.add(new Specialist("Dr. Nguyen", "Cardiopulmonary Rehabilitation"));
        specialists.add(new Specialist("Dr. Kim", "Vestibular Rehabilitation"));
        specialists.add(new Specialist("Dr. Rossi", "Manual Therapy"));
        specialists.add(new Specialist("Dr. Smith", "Sports Injuries"));

        // Add 20 members
        String[] memberNames = {
            "Sophia Adams", "Liam Brown", "Olivia Clark", "Noah Davis", "Emma Evans",
            "Jackson Ford", "Ava Gray", "Lucas Hill", "Isabella Jones", "Aiden King",
            "Mia Lee", "Ethan Moore", "Amelia Nelson", "Mason Parker", "Harper Reed",
            "Logan Scott", "Evelyn Taylor", "Elijah Turner", "Abigail Walker", "James Young"
        };
        for (int i = 0; i < 20; i++) {
            members.add(new Member(101 + i, memberNames[i]));
        }

        // Add sample sessions for each specialist
        specialists.get(0).scheduleNewSession(new TherapySession(specialists.get(0), "Joint Mobilization", "Monday 9 AM"));
        specialists.get(1).scheduleNewSession(new TherapySession(specialists.get(1), "Developmental Therapy", "Tuesday 10 AM"));
        specialists.get(2).scheduleNewSession(new TherapySession(specialists.get(2), "Fall Prevention", "Wednesday 11 AM"));
        specialists.get(3).scheduleNewSession(new TherapySession(specialists.get(3), "Cardiac Rehab", "Thursday 2 PM"));
        specialists.get(4).scheduleNewSession(new TherapySession(specialists.get(4), "Balance Retraining", "Friday 1 PM"));
        specialists.get(5).scheduleNewSession(new TherapySession(specialists.get(5), "Soft Tissue Mobilization", "Saturday 10 AM"));
        specialists.get(6).scheduleNewSession(new TherapySession(specialists.get(6), "Sports Massage", "Monday 11 AM"));
    }
    
    
    //Gui Setupp
    public void showGUI() {
        frame = new JFrame("BPC System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton listSpecialistsBtn = new JButton("List Specialists");
        JButton scheduleSessionBtn = new JButton("Schedule Session");
        JButton markAttendedBtn = new JButton("Mark Session as Attended"); // New button
        JButton showAllSessionsBtn = new JButton("Show All Sessions");
        JButton cancelSessionBtn = new JButton("Cancel Session");
        JButton searchByFocusBtn = new JButton("Search by Focus Area");
        JButton exitBtn = new JButton("Exit");

        buttonPanel.add(listSpecialistsBtn);
        buttonPanel.add(scheduleSessionBtn);
        buttonPanel.add(markAttendedBtn);
        buttonPanel.add(showAllSessionsBtn);
        buttonPanel.add(cancelSessionBtn);
        buttonPanel.add(searchByFocusBtn);
        buttonPanel.add(exitBtn);

        // Output area
        outputArea = new JTextArea(20, 70);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Layout
        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Action listeners
        listSpecialistsBtn.addActionListener(e -> listSpecialists());
        scheduleSessionBtn.addActionListener(e -> scheduleSession());
        markAttendedBtn.addActionListener(e -> markSessionAsAttended());
        showAllSessionsBtn.addActionListener(e -> showAllSessions());
        cancelSessionBtn.addActionListener(e -> cancelSession());
        searchByFocusBtn.addActionListener(e -> findByFocusArea());
        exitBtn.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }
    //List all specialists
    private void listSpecialists() {
        outputArea.append("\n--- Available Specialists ---\n");
        for (Specialist s : specialists) {
            outputArea.append("- " + s.getSpecialistName() + " (" + s.getFocusArea() + ")\n");
        }
    }

    //Schedule session
    private void scheduleSession() {
        String memberIdStr = JOptionPane.showInputDialog(frame, "Enter Member ID:");
        if (memberIdStr == null) return;
        try {
            int memberId = Integer.parseInt(memberIdStr);
            Member member = locateMemberById(memberId);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found.");
                return;
            }

            String specialistName = JOptionPane.showInputDialog(frame, "Enter Specialist Name:");
            if (specialistName == null) return;
            Specialist specialist = locateSpecialistByName(specialistName);
            if (specialist == null) {
                JOptionPane.showMessageDialog(frame, "Specialist not found.");
                return;
            }

            StringBuilder sb = new StringBuilder("Available Sessions:\n");
            for (TherapySession session : specialist.getSessionList()) {
                if (!session.hasReservation()) {
                    sb.append(session.getSessionTime()).append("\n");
                }
            }
            String sessionTime = JOptionPane.showInputDialog(frame, sb.toString() + "\nEnter Session Time to Reserve:");
            if (sessionTime == null) return;

            for (TherapySession session : specialist.getSessionList()) {
                if (session.getSessionTime().equalsIgnoreCase(sessionTime) && !session.hasReservation()) {
                    session.assignMember(member);
                    member.reserveSession(session);
                    JOptionPane.showMessageDialog(frame, "Session reserved successfully!");
                    outputArea.append("Session reserved for " + member.getFullName() + " at " + sessionTime + "\n");
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "No available session found.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid member ID.");
        }
    }
    
    // Mark a session as attended
    private void markSessionAsAttended() {
        String memberIdStr = JOptionPane.showInputDialog(frame, "Enter Member ID:");
        if (memberIdStr == null) return;
        try {
            int memberId = Integer.parseInt(memberIdStr);
            Member member = locateMemberById(memberId);
            if (member == null) {
                JOptionPane.showMessageDialog(frame, "Member not found.");
                return;
            }

            if (member.getReservedSessions().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No sessions scheduled for this member.");
                return;
            }

            StringBuilder sb = new StringBuilder("Your Scheduled Sessions:\n");
            for (TherapySession session : member.getReservedSessions()) {
                sb.append(session.getSessionTime()).append("\n");
            }
            String sessionTime = JOptionPane.showInputDialog(frame, sb.toString() + "\nEnter Session Time to Mark as Attended:");
            if (sessionTime == null) return;

            for (TherapySession session : member.getReservedSessions()) {
                if (session.getSessionTime().trim().equalsIgnoreCase(sessionTime.trim())) {
                    session.recordCompletion();
                    JOptionPane.showMessageDialog(frame, "Session marked as attended successfully!");
                    outputArea.append("Session marked as attended for " + member.getFullName() + " at " + sessionTime + "\n");
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Session not found.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid member ID.");
        }
    }
    


}