# Warehouse Management Simulation

A Java-based console application simulating a real-world warehouse logistics network. This project implements strict Object-Oriented Programming (OOP) principles, featuring role-based terminal access, package-structured logic, component capacity tracking, and state persistence via flat-file data streams.

---

## 🚀 Features

* **Role-Based Access Control:** Distinct terminal interfaces and actions tailored for three explicit system actors:
  * **Director (`Riaditel`):** Handles Human Resources tasks such as hiring and dismissing warehouse workers.
  * **Worker (`Pracovnik`):** Navigates through storage rooms, handles inventory adjustments, and manipulates goods inside racks.
  * **Customer (`Zakaznik`):** Deposits items into the system and retrieves them using unique identifiers.
* **Storage Capacity Management:** Segregated storage handling separating large warehouses (`VelkySklad`) with expandable racks from small warehouses (`MalySklad`) featuring rigid, fixed capacity ceilings.
* **State Persistence:** Automatic backup routine that serializes the current state of the warehouse (including rooms, items, employees, and user databases) to a text file on exit, and cleanly re-hydrates the ecosystem upon system launch.
* **Modular Architecture:** Structured clean code decoupling domain entities, interfaces, file management, and input-handling console terminals.

---

## 📁 Project Architecture & Packages

The codebase is strictly organized into functional domain packages:

| Package | Component / Interface | Description |
| :--- | :--- | :--- |
| **Root** | `Main` | System bootstrap layer; handles configuration loading and initial identity verification menu. |
| `sklad` | `Sklad`, `IIdentifikovatelny`, `VytvaracTovaru` | Manages global warehouse data frames, unique ID schemas, and GUI/helper utilities for item creation. |
| `miestnosti` | `ISkladovaMiestnost`, `Miestnost`, `VelkySklad`, `MalySklad` | Handles structural floor layout mechanics, containing employee directories and structural inventory racks. |
| `osoby` | `Osoba`, `Pracovnik`, `Riaditel`, `Zakaznik` | Abstract and concrete models representing personnel and external system actors. |
| `predmety` | `Regal`, `Tovar` | Models physics properties of inventory holdings: spatial constraints (racks) and target items (goods). |
| `terminaly` | `ITerminal`, `Terminal[Actor]` | Interactive IO loops mapping terminal console workflows dynamically based on logged-in privileges. |
| `pracaSoSuborom`| `CitacSuboru`, `ZapisovacSuboru` | Lower-level file stream readers/writers providing long-term persistence cycles. |

---

## 🛠️ Prerequisites & Build

* **Java Development Kit (JDK):** Version 11 or higher recommended.
* **IDE:** IntelliJ IDEA (preferred) or any standard Java compiler.

### Compiling and Packing via CLI
To compile the source code and export a portable executable JAR archive:
```bash
# Navigate to source folder and compile
javac -d out src/**/*.java

# Build executable JAR
jar cfe WarehouseSimulation.jar Main -C out .