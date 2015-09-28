# BlueServicesTest

Description:

Implementation of recruitment task for Blue Services company. Program domain concerns 'application' business artifact and its possible state changes according to state diagram included in task description. Each state change also has to be remembered and added to application state change history.

Additionally applications need to be searchable by name and state. By default one page should be consisted from max 10 elements when no exact number is specified.

To actually implement described features the following techniques were used: 
- State pattern for state changes - implementation of this pattern uses JPA single table inheritance
- indexed state changes list with added projection accessor method for reading purposes
- Strategy pattern for injecting number generator when publishing application
- buildable search query using JpaSpecificationExecutor mechanism
- exception thrown when some state change operation is not allowed on application

It was also assumed that application's name is not a part of its state so the field representing it was allocated in root entity itself.


Libraries used:
- Spring Boot
- Spring DI
- Spring Data JPA (repositories and entity auditing)
- Hibernate
- H2 database (in-memory mode)
- Lombok
- Guava
- JUnit
- Spring Test

Requirements:
- JDK 8
- Lombok plugin, if opened in IDE

While development EclEmma plugin for Eclipse IDE was being used to check actual code coverage.