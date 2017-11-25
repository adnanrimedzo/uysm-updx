# uysm-updx
## Setup

In order to setup, maven4 and jdk8 are requred.

 1. Built project (at same directory with pom.xml)
    `mvn package`
 2. Run application (at target file)

    2.1. Generate updx
    `java -jar updx-1.0.jar -s generate -key 1q2w3e4r1q2w3e4r -in ./input -out test.updx`

    2.2. Decode updx
    `java -jar updx-1.0.jar -s decode -key 1q2w3e4r1q2w3e4r -in test.updx -out ./output`
    
    2.2. Validate updx
    `java -jar updx-1.0.jar -s validate -key 1q2w3e4r1q2w3e4r -in test.updx`
    
    2.2. HashList updx
    `java -jar updx-1.0.jar -s hashlist -key 1q2w3e4r1q2w3e4r -in ./input -out hashList.txt`

