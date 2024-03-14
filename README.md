# BookStoreApp

# Run
## 1. Preparing

- **Install Java**. Application is running on Java JDK 17.
  To make sure that you are using the correct version - `java -version`. Install for [Windows][1]. For Linux is
  convenient to use [sdkman][2].
- **Install Docker**. For [Windows][3]. For [Linux][4].
- _You can also install [maven][6] ([how to install][7]), but it isn't necessary as project
  supports maven wrapper.
- After you must go root file of project and follow the path: src->main->resources. 
There you will see the apl.yaml file and in it you need the url line: jdbc:mysql://${DB_HOST:localhost}:3306/${DB_NAME:BookstoreDB}
  change 3306 to 3307
## 2. Preparing AWS
- Sign in to the AWS console: Open a browser and go to the AWS console sign in page (https://aws.amazon.com/). 
Sign in to your account using your credentials. You need to have all the necessary access rights for further work
- Create an access key: In the "Security credentials" section, click on the "Create access key" button. 
This will create a new access key for the selected user. Download or save this data in a safe place.
- At the top of the search bar, type DynamoDB and click on "DynamoDB" in the drop-down list to go to the DynamoDB console.
- On the right, you will see the "Create table" button and click on it
- For the Table name, enter: "BookStoreDBTable"
- For the Partition key , enter: "uuid" with String type

## 3. Build project

Run `./mvnw clean install`

...or, if you have installed maven:

Run `mvn clean install`

## 4. Run locally

Before running, you have to up the database:
``` sh
docker-compose up -d
```

After that, you should go to the target folder and execute the following command using the console:
``` sh

java -jar -DAWS_ACCESS_KEY_ID="value" -DAWS_SECRET_ACCESS_KEY="value" BookStore-0.0.1-SNAPSHOT.jar
```

# Other documentation
## Swagger
To access to swagger page go to `http://localhost:8082/swagger-ui/index.html`


[1]: https://download.oracle.com/java/17/archive/jdk-17.0.4.1_windows-x64_bin.exe
[2]: https://sdkman.io/install
[3]: https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe
[4]: https://docs.docker.com/engine/install/ubuntu/#installation-methods
[5]: https://docs.microsoft.com/uk-ua/windows/wsl/install
[6]: https://maven.apache.org/download.cgi
[7]: https://maven.apache.org/install.html
