**# IoT-Dashboard Webanwendung**

Die IoT-Dashboard Webanwendung ist eine Plattform zur Überwachung von Temperatur- und Luftfeuchtigkeitsdaten, die von einem Raspberry Pi gesammelt werden. Die Anwendung ist in Spring Boot, JavaScript, HTML und SCSS geschrieben und bietet ein benutzerfreundliches Dashboard.

## Installation

1. **Repository klonen:**

    ```bash
    git clone https://github.com/pkmrtz/IoT-Dashboard-Webapp.git
    cd IoT-Dashboard
    ```

2. **Anwendung starten:**

    Die Anwendung ist mit Spring Boot entwickelt. Du kannst sie mit deiner bevorzugten IDE ausführen oder mit dem folgenden Gradle-Befehl:

    ```bash
    ./gradlew bootRun
    ```

    Die Anwendung startet unter `http://localhost:8080`.

3. **Raspi-Skript einrichten:**

    Gehe auf das Repo des [Raspi-Skriptes](https://github.com/pkmrtz/IoT-Dashboard-Skripte) und befolge die Anweisungen, um das Skript einzurichten und asuzuführen.

4. **Datenbank konfigurieren:**

    Die Anwendung verwendet MySQL zur Speicherung von Daten. Konfiguriere die Datenbankverbindung in der Datei `application.properties`.

5. **Auf die Anwendung zugreifen:**

    Öffne deinen Browser und gehe zu `http://localhost:8080`, um auf das IoT-Dashboard zuzugreifen.

## Funktionen

- **Datensammlung:**
    - Die Anwendung sammelt Temperatur- und Luftfeuchtigkeitsdaten von einem Raspberry Pi mithilfe des Skripts in [IoT-Dashboard-Skripte](https://github.com/pkmrtz/IoT-Dashboard-Skripte).

- **Datenbank:**
    - Die gesammelten Daten werden in einer MySQL-Datenbank gespeichert.

- **Datenpräsentation:**
    - Das Frontend ruft Daten von der Backend über eine REST-API ab.
    - Daten werden mithilfe von Chart.js präsentiert, sodass Benutzer Temperatur- und Luftfeuchtigkeitstrends visualisieren können.
    - Benutzer können Diagramme nach Zeitintervallen sortieren.

- **Geräteverwaltung:**
    - Die Anwendung bietet eine Seite mit einer Tabelle aller registrierten Geräte mit IDs und Tokens.

- **Sicherheit:**
    - Das Posten von Daten in die Datenbank erfordert ein CSRF-Token, Cookies und die Geräte-ID mit Token zur Authentifizierung.
