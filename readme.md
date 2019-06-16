1.Clone git repo

2.Go to project root directory

3.Run using simple java command:

java -jar -Dspring.profiles.active=dev target/smsapp-0.0.1-SNAPSHOT.jar

Go to local host http://localhost:8080/swagger-ui.html and test api

	OR

3.Run app on docker:

docker build -t smsapp:latest .
docker run -d -v /tmp:/tmp -p 9090:8080  --name cpassapi smsapp



