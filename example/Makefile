VERSION = 1.3.0

run: build
	java -cp r3-$(VERSION).jar:`pwd` Main

script: resources
	java -cp r3-$(VERSION).jar Main.java

build: resources
	javac -cp r3-$(VERSION).jar Main.java

resources:
	cp ../build/libs/r3-$(VERSION).jar .

clean:
	rm *.class -f
	rm r3-*.jar -f
	rm recording.bin -f
