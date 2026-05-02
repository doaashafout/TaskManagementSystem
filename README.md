# Task Management System

A JavaFX-based desktop application designed to manage and organize tasks efficiently using a clean and interactive graphical user interface.

---

## Overview

The Task Management System allows users to:

- Add new tasks manually  
- View all tasks in a structured list  
- Analyze tasks using different operations  
- Load and save data using a CSV file  

Each task includes:
- ID  
- Title  
- Status (Open / Closed)  
- Added By  
- Creation Date  

---

##  Features

### Task Management
- Add new tasks with validation  
- Automatically assign unique IDs  
- Save tasks to a CSV file  

### Task Analysis
- Show tasks added by a specific user  
- Display the earliest four tasks  
- Find tasks starting with 'A' and having 7 letters  
- Identify the most active contributor  
- Count open and closed tasks  
- Count tasks added by a specific user  

### User Interface
- Built using JavaFX and FXML  
- Clean and user-friendly layout  
- Menu bar with:
  - Exit option  
  - Appearance customization (Font size, family, style)  
  - About section  

---

## Project Structure
src/
├── app/
├── controllers/
├── models/
├── views/
├── styles/
├── data/


---

##  Technologies Used

- Java  
- JavaFX  
- FXML  
- CSV File Handling  
- Java Streams  

---

## How to Run

1. Open the project in NetBeans or any Java IDE  
2. Make sure JavaFX is configured properly  
3. Run the application  

---

## 👩‍💻 Author

**Doaa Shafout**

---

## Notes

- Data is stored in a CSV file (`tasks.csv`)  
- All operations are implemented using Java Streams  
- The UI updates dynamically based on user actions  

