## Solution

## Assumption

- I have assumed that url mapping data is already available in Database. In order to create that scenario, I am running create and insert script of some readymade mapping data while application starting up.

## Tools
To implement the  URL Lookup Service, I have selected following Tools

- JDK 1.8
- Maven
- Spring Boot framework for REST Microservice
- H2 DB
- Embeded Tomcat with SpringBoot framework
- JUnit Test SpringBoot Test - MockMvc, SpringRunner etc

## Development Environment used: macOS 11.4 (Big Sur)

## Database Configuration

- H2 DB Configuration can be found or modified in application.properties under {project} -> src/main/resources
- Once Application Starts Running, H2 DB Console can be found at http://localhost:8090/h2-console/
  - DB Credential :
      - jdbc url: jdbc:h2:~/test_db
      - username: sa
      - password: password
- Mapping insert SQL script named 'data.sql' is under src/main/resources.

Note: We can add more data in this script to test additional mapping.

## Install Eclipse Java EE IDE for Web Developers.

	- Import the the Spring Boot REST Microservice Application as Maven Project
		File -> Import -> Maven Project -> pom.xml

	- Wait for all dependencies to be downloaded and resolved

## Run the following SpringBootApplication as Java Application From Eclipse
	Main Class : com.stylight.urllookupapp.URLLookupApplication

	- Refer application, properties file under src/main/resources folder

		#Server Port
		server.port=8090

	- Embedded tomcat will be running on 8090

## Run the following SpringBootApplication as Maven Project From CLI

  - Run this command from root project

     `mvn clean install`

  - Once the above command execution gets completed, run below mentioned command to run the application:

     `mvn spring-boot:run`

  This will run the application in http://localhost:8090/

## Swagger documentation

  - Swagger documentation of api is available at http://localhost:8090/swagger-ui.html#/

## Download Postman or Google Browser to verify the REST APIS

	Example : GET http://localhost:8090/urllookup/findprettyurls

## REST API GUIDE


## URL lookup - Pretty URL By Parameterized URL
===============================================

  (GET) http://localhost:8090/urllookup/findprettyurls

  1.
  - **Request:**
  [{
    "fromUrl" : "/products?gender=female"
    }
  ]

   - **Response:**
   "urlMappingDetails": [
         {
             "id": 2,
             "fromUrl": "/products?gender=female",
             "toUrl": "/Women/"
         }
     ],
     "erroMappingDetails": []
 }

  2.
  - **Request(Nearest possible match):**
  [{
    "fromUrl" : "/products?gender=female&tag=123&tag=1234&tag=5678&tag=7654"
  }
  ]

  - **Response:**
  {
    "urlMappingDetails": [
        {
            "id": 4,
            "fromUrl": "/products?gender=female&tag=123&tag=1234",
            "toUrl": "/Women/Shoes/?tag=5678&tag=7654"
        }
    ],
    "erroMappingDetails": []
  }

  3.
  - **Request(Nearest possible match and not mapped url):**
  [{
    "fromUrl" : "/products?brand=5567888"
   },{
    "fromUrl" : "/products?gender=female"
    },{
    "fromUrl" : "/products?gender=female&tag=123&tag=1234&tag=5678"
    }
  ]

  - **Response:**
  {
    "urlMappingDetails": [
        {
            "id": 2,
            "fromUrl": "/products?gender=female",
            "toUrl": "/Women/"
        },
        {
            "id": 4,
            "fromUrl": "/products?gender=female&tag=123&tag=1234",
            "toUrl": "/Women/Shoes/?tag=5678"
        }
    ],
    "erroMappingDetails": [
        {
            "inputUrl": "/products?brand=5567888"
        }
    ]
  }

## URL Lookup - Parameterized URL by Pretty URL
===============================================

  (GET) http://localhost:8090/urllookup/findparameterizedurl

   - **Request:**
   [ { "toUrl": "/Women/Shoes/"}]

   - **Response:**
   {
    "urlMappingDetails": [
        {
            "id": 4,
            "fromUrl": "/products?gender=female&tag=123&tag=1234",
            "toUrl": "/Women/Shoes/"
        }
    ],
    "erroMappingDetails": []
}


## Test Cases
====================

	>Under src/test/java/


## Thoughts about the design:
=============================

1. I have assumed a limited number of mapping exist in DB, but, for larger dataset I would not directly call find all from DB every time. Instead will implement some sort of distributed caching mechanism (Redis/Hazelcast).

2. H2 DB is a In memory DB which I have primarily used for testing. For permanent solution, I would go for some other DB which supports huge dataset and retrieval time is also less. May be PostgreSQL or NoSQL (Example: MongoDB).

3. A endpoint to create add mapping will give user a full functionality.

4. Incase of a scenario, where input list containing url that does not have mapping in DB then it will show that list in erroMappingDetails response list. I have implemented this design approach to help user or frontend engineer to design more accurate UI.

5. I have taken into consideration that, My current system design had took around 8ms to serve the request. Which I will definitely take care of for larger dataset.
