run: build
	java -cp r3.jar:`pwd` Main

script: resources
	java -cp r3.jar Main.java

build: resources
	javac -cp r3.jar Main.java

resources:
	cp ../build/libs/r3.jar .

clean:
	rm *.class -f
	rm r3.jar -f
	rm recording.bin -f
