# Pillar Technology Pencil Kata


![a generic pencil photo](/pencil.jpg?raw=true)

Kata description @ https://github.com/PillarTechnology/kata-pencil-durability 

Dependencies are Git, Maven and Java

To run all tests
```
git clone https://github.com/MenkeTechnologies/PillarTechnologyPencilKata.git 
cd PillarTechnologyPencilKata
mvn test
```
![git clone and maven test](/run.png?raw=true)
To run just the whenPencilWritesToPaperThePaperReturnsContents test (replace whenPencilWritesToPaperThePaperReturnsContents with another method name to run any other single test)
```
git clone https://github.com/MenkeTechnologies/PillarTechnologyPencilKata.git
cd PillarTechnologyPencilKata
mvn '-Dtest=PencilDurabilityTest#whenPencilWritesToPaperThePaperReturnsContents' test
```
Javadoc HTML pages are in the doc directory

# created by Jacob Menke
