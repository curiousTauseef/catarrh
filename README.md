# Catarrh: Undertow zero transfer example

Quickie example for zero transfer with embedded HTTP Undertow service

## How to
`mvn clean compile`

`java -jar target/catarrh-jar-with-dependencies.jar 8080 localhost`

`curl -d "@<your_file>" -X POST http://localhost:8080/?file=tmp-file`

Look for tmp-file look like file in your `tmp` os directory.