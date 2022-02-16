# Implementation

r3 will implement a function called `Record.recordCall` which will
record a function name and a set of arguments to be persisted.
Example:

```java
Record.recordCall("Drivetrain.tankDrive", xaxis, yaxis);
```

The class in which you want certain methods to be recorded should
implement a method titled `getInstance` which takes no arguments
and returns an instance of the class or `null` if all relevant functions
are static. This is necessary to replay a recording.
