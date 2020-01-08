# findDuplicates
## Description
findDuplicates it program for search duplicate of files. This web application use checksum algorithm for compare files, it show result in web browser window grouped files list by size of files. 
Program use double search for increase performance: first find by size, and after it use checksum algorithm search, becouse search by size more fast and don't claim make checksum of file.

## Technologies
- Java 11:
  - Spring Framework:
    - Spring MVC:
      - application-level on the basis on design pattern: model-view-controller
    - Spring Boot:
      - automatic configuration and launching application 
  - Java 11 SE:
- HTML:
  - Thymeleaf
- Apache commons-io library and commons-codec for checksum


