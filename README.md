# cookit
### This is a Sample repository for the Assessment test

Rest Api sample shows how to deploy [SpringBoot](http://projects.spring.io/spring-boot/) RESTful web service application with [Docker](https://www.docker.com/)

#### Prerequisite 

Installed:   
[Docker](https://www.docker.com/)   
[git](https://www.digitalocean.com/community/tutorials/how-to-contribute-to-open-source-getting-started-with-git)   

Optional:   
[Docker-Compose](https://docs.docker.com/compose/install/)   
[Java 1.8 or](https://www.oracle.com/technetwork/java/javase/overview/index.html)   
[Maven 3.x](https://maven.apache.org/install.html)

#### Steps

##### Clone source code from git
```
$ git clone https://github.com/akash1202/cookit .
```

##### Create container with below command.
```
$ docker build -f Dockerfile -t docker-cookit .
```
Maven build will be executed during creation of the docker image.

>Note:if you run this command for first time it will take some time in order to download base image from [DockerHub](https://hub.docker.com/)

##### Run created docker container
```
$ docker run -p 8080:8080 docker-cookit
```

##### Test application

```
1.$ curl localhost:8080/items
2.$ curl localhost:8080/proteins
3.$ curl localhost:8080/createbox //Actual Test
```

The respone for 1st request should be:
```
[...{
id: 126,
name: "Guédilles aux crevettes nordiques",
displayName: "2P-S-123",
volume: 1000,
deliveryWeek: "2021-08-09",
station: "A4",
category: "RECETTE",
inStock: true,
meatCode: "S"
}...]
```


