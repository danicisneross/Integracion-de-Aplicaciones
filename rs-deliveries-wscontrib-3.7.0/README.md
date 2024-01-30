# rs-deliveries-wscontrib
--------------------------------------------------------------------'

## Building the examples

    mvn install

## Running the examples

`rs-deliveries-wscontrib` provides several web service applications.

### Running the rs-deliveries-wscontrib services with Maven/Jetty.

    mvn jetty:run

### Running the rs-deliveries-wscontrib services with Tomcat.

- Copy the `.war` file (e.g. `target/rs-deliveries-wscontrib.war`) to Tomcat's `webapp` directory.

- Start Tomcat:

    cd $TOMCAT_HOME/bin
    startup.sh
      
- Shutdown Tomcat:
  
    shutdown.sh

      
## WSDL files for the rs-deliveries-wscontrib-service services:
- [http://localhost:7070/rs-deliveries-wscontrib/services/CrmService?wsdl](http://localhost:7070/rs-deliveries-wscontrib/services/CrmService?wsdl)
- [http://localhost:7070/rs-deliveries-wscontrib/services/RewardService?wsdl](http://localhost:7070/rs-deliveries-wscontrib/services/RewardService?wsdl)
- [http://localhost:7070/rs-deliveries-wscontrib/services/DeliveryService?wsdl](http://localhost:7070/rs-deliveries-wscontrib/services/DeliveryService?wsdl)