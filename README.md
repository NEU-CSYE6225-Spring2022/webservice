# webservice
This repo is used for Network Structures and Cloud Computing assignment Spring2022.
## project info
Basic spring-boot project with maven and swagger docs setup. Added Basic auth settings to the project.
## steps to run this project
1. git clone git@github.com:amireddym/webservice.git
2. Install maven in your system and java 1.8 version JDK.
3. change to project directory in terminal and run the below commands.
4. mvn clean install
5. mvn spring-boot:run
6. check the logs for the application url.
7. Default port is 8080 and runs in localhost.
8. press command+c to stop the server.
9. if you wish not to install maven.You can run following commands on respective OS.
10. ./mvnw spring-boot:run  (unix)
11. ./mvnw.cmd spring-boot:run  (windows)
## running test cases
1. change to the repo directory i.e, springdemo.
2. run the below command.
3. mvn test
## urls 
1. http://localhost:8080/healthz (health check api)
2. http://localhost:8080/swagger-ui/index.html  (swagger ui)
3. http://localhost:8080/v2/api-docs  (api docs)
4. http://localhost:8080/v1/user (POST -- basic auth user creation)
5. http://localhost:8080/v1/user/self (GET -- get current logged user information)
6. http://localhost:8080/v1/user/self (PUT -- update user information)
7. http://localhost:8080/v1/user/self/pic (POST --- to add user profile pic)
8. http://localhost:8080/v1/user/self/pic (GET --- get users profile pic)
9. http://localhost:8080/v1/user/self/pic (DELETE --- delete users pic)
## Packer template file included
Adding packer template to create custom AWS ami with Mysql and Spring boot app.
1. Github actions runs the commands to install dependencies for Packer, Aws cli.
2. After that we test the application for unit tests.
3. We also run the maven build to generate the Jar file.
4. Finally we run the packer validate and then the packer build to generate the Custom AMI and push it to the dev Organization along with demo.
5. Use this packer build AMI for cloudformation template.
