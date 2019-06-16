1.Clone git repo

2.Go to project root directory

3.Run using simple java command:

java -jar -Dspring.profiles.active=dev target/smsapp-0.0.1-SNAPSHOT.jar

Go to local host http://localhost:8080/swagger-ui.html and test api

Or one can use Postman or Advanced Rest Client

Now app is using in memory security

Send post request to authenticate end point to obtain jwt bearer

http://localhost:8080/api/authenticate

Request
{
  "password": "password",
  "username": "smsappuser"
}

Sample Response
{
"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzbXNhcHB1c2VyIiwiZXhwIjoxNTYwNzE5MzAzLCJpYXQiOjE1NjA3MDEzMDN9.OnYNCo0GpQycXoFN1l1jJf639YVdHenq5E4E5DdXyDXX_EjIVK-l02S4mk0iU5cdH3frVWecrF7DfPngAQF1mg"
}

Now to send an sms or call an api specify Authorization Header on Swagger,Postman or Advanced Rest Client

Example: Authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzbXNhcHB1c2VyIiwiZXhwIjoxNTYwNzE5MzAzLCJpYXQiOjE1NjA3MDEzMDN9.OnYNCo0GpQycXoFN1l1jJf639YVdHenq5E4E5DdXyDXX_EjIVK-l02S4mk0iU5cdH3frVWecrF7DfPngAQF1mg


	OR

3.Run app on docker:

docker build -t smsapp:latest .
docker run -d -v /tmp:/tmp -p 9090:8080  --name cpassapi smsapp



