# BPCSystem - Physiotherapy Booking and Management System

## Overview
BPCSystem is a Java-based desktop application that allows physiotherapy clinics to manage appointments effectively. Users can book, cancel, and mark sessions as attended. The GUI is built using Swing and supports various functionalities like listing specialists and searching by focus area.

---

## Project Structure
```
BPCSystem/
├── build/                  
├── dist/
│   ├── BPCSystem.jar       
│   └── README.txt          
├── nbproject/              
├── src/                    
│   └── (default package)/
│       ├── BPCSystem.java
│       ├── Specialist.java
│       ├── Member.java
│       └── TherapySession.java
├── test/                   
│   └── (default package)/
│       └── BPCSystemTest.java
├── manifest.mf             
└── build.xml               
```

---

## Prerequisites
- Java JDK 8 or later
- (Optional) NetBeans IDE 8.2 or later

Check Java version:
```bash
java -version
```

---

## Build Instructions
Using NetBeans:
1. Open NetBeans > File > Open Project > Select `BPCSystem` folder
2. Right-click the project > Click `Build`
3. `dist/BPCSystem.jar` will be created

---

## Run Instructions
Run from terminal:
```bash
cd path/to/BPCSystem/dist
java -jar "BPCSystem.jar"
```

Expected: A GUI window titled "BPC System" will open.

---

## Features
- **List Specialists**: View all available specialists
- **Schedule Session**: Book a new session
- **Cancel Session**: Cancel a booked session
- **Mark Session as Attended**: Mark session as completed
- **Search by Focus Area**: Filter specialists by specialty
- **Show All Sessions**: List all scheduled sessions
- **Exit Button**: Closes the app

---

## Testing
Test class: `test/BPCSystemTest.java`

To run tests in NetBeans:
1. Right-click the project
2. Click `Test`

Sample test snippet:
```java
@Test
public void testFindPhysiotherapistByName() {
    Physiotherapist physio = system.findPhysiotherapistByName("Dr. Smith");
    assertNotNull(physio);
    assertEquals("Dr. Smith", physio.getName());
}
```

---

## Development Notes
- **Language**: Java
- **IDE**: NetBeans
- **UI**: Swing
- **Testing**: JUnit

---

## Contributors
- Sadiq Idris - Developer
