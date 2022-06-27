# KrakenFlex Back End Test

### Getting Started
All Maven plugins and dependencies are available from [Maven Central](https://search.maven.org/). Please have installed

* JDK 1.8 (tested with both Oracle and OpenJDK)
* Maven 3.6+ 
* Docker 2.1.0.4

### Building

`mvn clean install`

### Running

### SpringBoot Style

Typical java jar created

`java -jar target/tech-test-0.0.1-SNAPSHOT.jar`

Should start the application on the port 8080

#### Docker Style

First, build image (don't miss the dot(.) in the end of below command)

```docker build -t tech-test . ```

Then run the below command
```
docker run -d -p 8080:8080 -it tech-test:latest
```

#### Test the Endpoint
This application (client) has exposed the `/report-outages/{siteId}` REST endpoint that accepts SiteID and post outages based on the requirement.

Endpoint should honor the below requirements:
1. Retrieves all outages from the `GET /outages` endpoint
2. Retrieves information from the `GET /site-info/{siteId}` endpoint for the site with the ID `norwich-pear-tree`
3. Filters out any outages that began before `2022-01-01T00:00:00.000Z` or don't have an ID that is in the list of
   devices in the site information
4. For the remaining outages, it should attach the display name of the device in the site information to each appropriate outage
5. Sends this list of outages to `POST /site-outages/{siteId}` for the site with the ID `norwich-pear-tree`

Examples:

1. Happy path, should post the outage for `norwich-pear-tree`

Request:
```
# curl -X POST http://localhost:8080/report-outages/norwich-pear-tree
``` 

Expected response:
```
{
    "noOfOutageReported": 10,
    "reportedAt": "2022-06-26T23:12:29.371"
}
``` 

2. Sad path, should not post the outage due to bad data
Request:
```
# curl -X POST http://localhost:8080/report-outages/no-kingfisher
``` 

Expected response:
```
{
    "noOfOutageReported": 10,
    "reportedAt": "2022-06-26T23:12:29.371"
}
``` 

