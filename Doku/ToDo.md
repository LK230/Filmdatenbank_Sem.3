### Anforderungsanalyse
- [x] fachliche Anforderungen
- [x] technische Anforderungen
- [x] verknüpfung der Anforderungen untereinander

### Softwarearchitektur und Clean Code
- [ ] Packetuntergliederung und mit Klassendiagramm dokumentiert
- [ ] SOLID-Prinzipien angewandt und dokumentiert
- [x] Interfaces und Factories eingesetzt
- [ ] Dokumentation des Codes und Architektur
- [ ] Dokumentieren wie Komponente miteinander kommunizieren
- [ ] Dokumentation der architektonischen Entscheidungen mit Hilfe von ADR (mind. 3)
- [ ] Code-Dokumentationprinzipen beschrieben und angewandt

### git
- [x] Commits mit Commit-Messages
- [x] Tags & Changelog vorhanden
- [ ] Tags & Changelog gepflegt
- [x] Readme vorhanden
- [ ] git-Vorgehen inkl. branching dokumentiert und angewandt

### Build management & Testing
- [ ] Build Management vorhanden
- [ ] Unit-Tests für Model und Controller nach F.I.R.S.T. Prinzip vorhanden
- [ ] Integrationstests für Hauptkomponenten vorhanden

### CI/CD
- [x] .gitlab-ci.yml angelegt
- [x] Build-Lifecycle in .gitlab-ci.yml abgebildet
- [x] .gitlab-ci.yml so angepasst, dass manche Steps nur bei Bedarf ausgeführt werden
- [ ] Dokumentation, dass nur manche Steps im .gitlab-ci.yml ausgeführt werden

### Persistenz
- [x] Persistenz vorhanden
- [x] CRUD-Operationen für (No)SQL-DB teilweise implementiert
- [x] CRUD-Operationen für (No)SQL-DB implementiert & genutzt 
- [ ] DB-Operationen in Tests aufgenommen, Bereitstellung von Demo-Daten

### Schnittstellen
- [x] externe Schnittstellen vorhanden
- [x] CRUD teilweise umgesetzt
- [x] CRUD-Operationen über externe Schnittstelle bereitgestellt. Bei REST: Korrekte Nutzung der HTTP-Verben und Status-Codes
- [x] Dokumentation der Schnittstelle: 
  - [ ] Doku der Operationen
  - [ ] Warum welche Schnittstellenart (synchron/asynchron) eingesetzt

### UI
- [x] GUI vorhanden
- [x] GUI vorhanden über die die Programfunktionen ausführbar sind
- [ ] GUI austauschbar gehalten: keine Logik im Gui-Code
- [ ] (Potentiell) langlaufende Abfragen asynchron. Eine GUI-Operation (z.B. Speichern) ist ausreichend

### Parallele Programmierung
- [ ] Thread vorhanden, der nicht auf shared state zugreift
- [ ] Skizzierung möglicher Threads im Projekt
- [ ] Beschreibung, welche Probleme mit diesen Threads auftreten könnten und wie diese verhindert werden können
- [ ] Implementierung der beschriebenen Threads inkl. der Sicherungsmaßnahmen