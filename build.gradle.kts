plugins {
    kotlin("jvm") version "1.3.21"
    java
}

repositories {
    jcenter()
}

dependencies {
    compile( kotlin("stdlib") )
    compile( "org.apache.commons:commons-lang3:3.3.2")
    compile("org.apache.commons:commons-csv:1.7")
    compile("com.google.guava:guava:28.0-jre")
    testCompile("junit:junit:4.+")
    
}

tasks.test {
    testLogging {
        events("PASSED", "FAILED", "SKIPPED", "STANDARD_ERROR", "STANDARD_OUT")
    }
}