# IU-Testshop

## Projektbeschreibung

Der IU-Testshop ist eine fiktive E-Commerce-Webanwendung für die Fallstudie „Implementierung von automatisierten Tests für eine Webanwendung“ im Modul DevOps und Continuous Delivery.

Im Mittelpunkt steht eine automatisierte Teststrategie innerhalb einer CI/CD-Lieferkette. Änderungen werden auf mehreren Testebenen geprüft, durch JaCoCo und SonarQube Cloud bewertet und anschließend gegen eine auf Railway bereitgestellte Staging-Umgebung per Selenium getestet.

## Funktionen

- Produktkatalog und Produktsuche
- Warenkorb
- simulierter Zahlungsprozess
- Bestellübersicht und Speicherung von Bestellungen

## Technologien

- Java 21 und Spring Boot
- Spring Data JPA, Thymeleaf und H2
- Maven
- JUnit 5 und Mockito
- Selenium WebDriver
- JaCoCo
- SonarQube Cloud
- GitHub Actions
- Docker
- Railway Staging

## Automatisierte Tests

Die Tests sind nach Testebenen getrennt:

- Unit-Tests prüfen insbesondere Produkt-, Warenkorb-, Bestell- und Controllerlogik.
- Integrationstests prüfen unter anderem Repository-Zugriffe und den Spring-Anwendungskontext.
- E2E-Tests prüfen den Checkout-Happy-Path sowie den Schutz vor einem Zahlungsaufruf mit leerem Warenkorb gegen die laufende Railway-Staging-Umgebung.

Der aktuelle Testbestand umfasst 27 automatisierte Tests. Im verifizierten CI-Lauf werden alle Tests erfolgreich ausgeführt.

## CI/CD und Quality Gates

Die GitHub-Actions-Pipeline führt die Prüfungen gestaffelt aus:

1. Unit-Tests
2. Integrationstests, Build und Code-Qualität
3. JaCoCo Line-Coverage-Gate von 70 %
4. SonarQube-Cloud-Analyse mit blockierendem Quality Gate
5. Healthcheck der Railway-Staging-Umgebung
6. Selenium-E2E-Tests gegen Staging

Die zuletzt gemessene JaCoCo Line Coverage beträgt 77,65 % (132 von 170 Zeilen). Testberichte, JaCoCo-Report und das erzeugte JAR werden als GitHub-Actions-Artefakte gespeichert.

## Lokale Ausführung

```bash
./mvnw spring-boot:run
```

Die Anwendung verwendet lokal standardmäßig Port 8080. Der Healthcheck ist unter `/actuator/health` verfügbar.

Unit-Tests:

```bash
./mvnw test
```

Vollständiger Maven-Verify-Lauf mit Integrationstests und Coverage-Gate:

```bash
./mvnw verify
```

Für den Selenium-E2E-Test muss eine laufende Zielumgebung angegeben werden. Ohne Umgebungsvariable wird lokal `http://localhost:8080` verwendet.

## Hinweis zur Staging-Bereitstellung

Railway stellt die Anwendung als Staging-Umgebung bereit. Die E2E-Stufe wartet auf einen erfolgreichen Healthcheck und testet anschließend die laufende Webanwendung. Eine Produktionsbereitstellung ist nicht Bestandteil dieser Fallstudienimplementierung.
