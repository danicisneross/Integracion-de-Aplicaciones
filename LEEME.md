# Memoria justificativa del proyecto
---------------------------------------------------------------------

## Iteración 1
---------------------------------------------------------------------

### Pruebas WS-BPEL
URL a los documentos WSDL que es necesario utilizar:
 - http://localhost:5050/CA02/ClientService?wsdl
 - http://localhost:7070/rs-deliveries-wscontrib/services/RewardService?wsdl
 - http://localhost:7070/rs-deliveries-wscontrib/services/CrmService?wsdl
- http://localhost:7070/rs-deliveries-wscontrib/services/DeliveryService?wsdl
### Justificaciones de diseño
- Nada que añadir.

### Problemas conocidos en el diseño / implementación de la práctica
- Al intentar importar el archivo zip del proyecto BPEL salta el siguiente error: The first step has to be "ns:createShipmentResponse", if an absolute path is used for query. Expression: /ns:createShipmentResponse/return/shipmentId del WizardCorrelationProperties.wsdl

### Resumen de contribución de cada miembro del grupo (consistente con commits en repositorio GIT)
- Omar Montenegro Macía: 

    - Capa modelo: Implementación de las funcionalidades ADD-SHI, CNL-SHI y FND-SHI (por keyword). Añadidos los test de las respectivas funcionalidades.
      
    - BPEL: Diseño del flujo correspondiente a la parte de RewardService.

- David Pérez Martinez: 

    - Capa modelo: Implementación de las funcinalidades ADD-CUST, DLT-CUST y FND-CUST (por keyword). Añadidos los test de las respectivas funcionalidades.

    - BPEL: Diseño del flujo correspondiente a la parte de CrmService.

- Daniela Cisneros Sande: 

    - Capa modelo: Implementación de las funcionalidades UPT-SHIP, UPT-CUS, FND-SHIP (por id) y FND-CUS (por id). Añadidos los test de las respectivas funcionalidades.

    - BPEL: Diseño del flujo correspondiente a la parte de DeliveryService.

 

## Iteración 2
---------------------------------------------------------------------

### Partes opcionales incluidas y miembros del grupo que han participado
- Parte opcional: Documentación del servicio.
  - Realización completa de la tarea: David Pérez Martínez.
    
- Parte opcional: Integración de nuestro servicio REST con el flujo BPEL.
  - Realización completa de la tarea: Omar Montenegro Macía.
    
- Parte opcional: Hipermedia
  - Puntos 1, 2, 4 y 6: Omar Montenegro Macía.
  - Puntos 3 y 5: Daniela A. Cisneros Sande.

### Pruebas WS REST
- Nombre del fichero SoapUI/colección Postman con las peticiones a probar: 
La colección de peticiones de Postman se encuentra en el fichero PRUEBAS.txt en el directorio raíz del proyecto .
- Comandos maven necesarios para ejecutar las pruebas
Los comandos están guardados en el fichero PRUEBAS.txt en el directorio raíz del proyecto.

### Pruebas WS-BPEL
- URL a los documentos WSDL que es necesario utilizar:
Cliente:
http://localhost:5050/ClientServiceService/ClientServicePort?wsdl
Respuestas de la empresa de mensajería:
http://localhost:5050/CA02RESTService1/casaPort1?wsdl
RewardService:
http://localhost:7070/rs-deliveries-wscontrib/services/RewardService?wsdl

- Nombre del fichero SoapUI con las peticiones:
Ficheros (en el directorio BPEL/pruebas del proyecto):
 - ClientServicePort-soapui-project.xml
 - NoticeShipment-soapui-project.xml
 - RewardService-soapui-project.xml
Para cambiar el estado del envío hacemos la siguiente petición en postman:
POST http://localhost:10000/rs-deliveries-service/shipment/{id}
En body->x-www-form-urlencoded:
Key: status; Value:SENT/DELIVERED/REJECTED

### Justificaciones de diseño
- Nada que comentar.

### Problemas conocidos en el diseño / implementación de la práctica
- Al utilizar el cliente de JSON los enumerados no se procesan como es debido. Cambiamos el atributo status de ShipmentStatus a String tanto en la capa servicio como en la capa cliente.
- Si las operaciones del cliente son ejecutadas desde Intel en lugar de por terminal deben usarse comillas dobles o fallará.

### Resumen de contribución de cada miembro del grupo (consistente con commits en repositorio GIT)
- David Pérez Martinez:
  - Servicio: Creacion y modificación de los casos de uso addCustomer, findByKeyword y deleteCustomer además de las clases necesarias para su correcta funcionalidad.
  - Cliente: Creacion y modificación de los casos de uso addCustomer, deleteCustomer además de las clases necesarias para su correcta funcionalidad.

- Omar Montenegro Macía:
  - Servicio: Creacion y modificación de los casos de uso addShipment además de las clases necesarias para su correcta funcionalidad.
  - Cliente: Creacion y modificación del caso de uso findByCustomer además de las clases necesarias para su correcta funcionalidad.

-Daniela A. Cisneros Sande: 

  - Servicio: Creacion y modificación de los casos de uso updateCustomer, findCustomerById, updateStatusShipment y findShipmentById además de las clases necesarias para su correcta funcionalidad.
  - Cliente: Creacion y modificación del caso de uso changeState además de las clases necesarias para su correcta funcionalidad.      


