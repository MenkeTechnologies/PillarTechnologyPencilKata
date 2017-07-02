# Pillar Technology Kata

Kata description @ https://github.com/PillarTechnology/kata-pencil-durability 

Dependencies are Git, Maven and Java

To run all tests
```
git clone https://github.com/MenkeTechnologies/PillarTechnologyPencilKata.git 
cd PillarTechnologyPencilKata
mvn compile
mvn test
```
To run just the InvalidHoursStarting test (replace InvalidHoursStarting with another method name to run any other single test)
```
git clone https://github.com/MenkeTechnologies/PillarTechnologyPencilKata.git
cd PillarTechnologyPencilKata
mvn compile
mvn '-Dtest=PencilWriteTest#whenPencilWritesToPaperThePaperReturnsContents' test
```
Javadoc HTML pages are in the doc directory

# created by Jacob Menke