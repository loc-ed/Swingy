mvn clean package
find . -name "*.java" > sources.txt
javac -sourcepath . @sources.txt
java swingy.Simulator
