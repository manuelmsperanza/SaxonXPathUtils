# FEATURE

## Java 25 baseline
The project now targets **Java 25** via Maven compiler `release` configuration.

## Build stability improvements
- Removed outdated/enforcer/jarsigner build steps that could break `mvn install` in standard local environments.
- Switched logging dependencies to canonical Log4j artifacts (`log4j-api`, `log4j-core`).

## Test coverage
Added a JUnit 5 test that validates namespace-aware XPath extraction through `XmlExtractor`.

## Javadoc quality
Improved public API documentation in core namespace/binding helper classes to keep javadocs clean under modern JDK doclint.
