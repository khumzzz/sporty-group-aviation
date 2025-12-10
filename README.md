# Sporty-Group Aviation
# Candidate: Mandlenkosi Khumalo
## Home Assessment 

## Setup and run instructions

- [ ] Install Java 17 
- [ ] Install Maven 
- [ ] Clone the project from
  - https://github.com/khumzzz/sporty-group-aviation.git
- [ ] Replace [custom_internal_user] with any string you wish to use as username for authenticating calls to your application
- [ ] Replace [custom_internal_password] with any string you wish to use as password for authenticating calls to your application
- [ ] Replace [custom_external_user] with a string needed (if any) as username for authenticating calls to the aviation external api
- [ ] Replace [custom_external_password] with any string needed (if any) as password for authenticating calls to the aviation external api
- [ ] Run the application as a spring boot application(using the main class)

## Instructions for running tests

- [ ] By default your application runs on port 8080
- [ ] Using your preferred tool (e.g Postman) invoke rest api on end point 
  - http://localhost:8080/aviation/v1/service/airport-details
- [ ] Populate BasicAuth details as defined above 

- Request:
  - {
    "icaoCode": "12345"
    }
- Response:
  - {
    "detailsStatus": {
    "errorCode": "999",
    "errorDescription": "Error calling service"
    }
    }

## Assumptions

- [ ] Assumed the external call requires at header basic auth to call : 
  - set up an external property as this can be different per environment
- [ ] Assumed the volumes are high : 
  - used apache camel to handle volumes and simplify extendability
- [ ] Assumed we could integrate with multiple other external apis : 
  - created a BaseRestConnector class that can be extended to connect to multiple other endpoints overriding the specific details

## Architecture Decisions

- [ ] Consumer API must provide a form of transaction(call id) to help us tie together the requests and the response.
- [ ] Apache camel for simplified integrations :
- [ ] resilience4j for all the retries(configurable)
- [ ] Docker for container deployments and simplified scaling in kubernetes by scaling pods up and down as needed
- [ ] API Call Status :
    - all responses have a status attached to it so that the consumer can have a detailed reason why they got a given response
- [ ] Caching :
    - assuming the airport details do mot change regularly we will cache successful responses using ehcache 
    - Only call the external API when you haven't called it in a given cache period

## Error Handling

- [ ] resilience4j :
    - Retry mechanism that allows the application to retry the call in the event of a no successful response from the external API
- [ ] API Call Status :
    - all responses have a status attached to it
    - errors are logged transparently using sl4j
- [ ] Assumed we could integrate with multiple other external apis :
    - created a BaseRestConnector class that can be extended to connect to multiple other endpoints overriding the specific details
- [ ] Assumed we could integrate with multiple other external apis :
    - created a BaseRestConnector class that can be extended to connect to multiple other endpoints overriding the specific details
