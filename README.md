[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/qLz2mrby)
# Running the project example
---------------------------------------------------------------------

## Running the deliveries service with Maven/Jetty.

    cd rs-deliveries/rs-deliveries-service
    mvn jetty:run


## Running the deliveries client application

- Configure `rs-deliveries/rs-deliveries-client/src/main/resources/ConfigurationParameters.properties`
  for specifying the client project service implementation (XML or JSON) and the port number 
  of the web server in the endpoint address (10000 for Jetty)
  
- Change to `rs-deliveries-client` folder

    cd rs-deliveries/rs-deliveries-client


- AddCustomer

    mvn exec:java -Dexec.mainClass="es.udc.rs.deliveries.client.ui.DeliveryServiceClient" -Dexec.args="-addCustomer 'Universidade da Coruña' 12345678J 'Elviña s/n'"

- DeleteCustomer

    mvn exec:java -Dexec.mainClass="es.udc.rs.deliveries.client.ui.DeliveryServiceClient" -Dexec.args="-deleteCustomer 1"

- FindByCustomer

    mvn exec:java -Dexec.mainClass="es.udc.rs.deliveries.client.ui.DeliveryServiceClient" -Dexec.args="-findByCustomer 1 ANY"
    
- ChangeState

mvn exec:java -Dexec.mainClass="es.udc.rs.deliveries.client.ui.DeliveryServiceClient" -Dexec.args="-changeState 1 SENT"       

## Get Swagger information
- http://localhost:10000/rs-deliveries-service/openapi/openapi.json