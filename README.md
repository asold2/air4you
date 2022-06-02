# air4you

**About air4you**
The air4you application is one of the two applications used by the Data Engineering team from group 1 for SEP 4. 
This application is responsible for storing data received from the Interactive Media and from the Gateway application (the second application).This is the link for it: https://github.com/dorinpascal/SEP4_GatewayApp

The air4you application has endpoints for the Interactive Media to interact with the data storage. Mainly, the Interactive Media group wants to see the information abouut rooms, measurements and thresholds. The air4you application receives the measurements from the Gateway Application through REST APIs, the same communication interface that is used for Gateway Application. 
The air4you application is deployed to the Amazon Web Services, which means that it is not necessary to always run it locally for the IoT and Interactive Media teams to interact with the APIs. 
In the Stage_ETL, the ETL process for the Data Warehousing is implemented. 
The ETL can only be run from the Stage_ETL branch, and only locally, as the Amazon Web Services can only support one instance of the database, and for the ETL process a new connection to the databse needs to be established.

**Contributors** 
- [Andrei Soldan] (https://github.com/asold2)
- [Esben Fogh] (https://github.com/Fogh1206)
- [Kyra Tolnai] (https://github.com/kyratolnai11)
- [Peter Blaško] (https://github.com/peeterblahaa)
- [Chenben Tong ] (https://github.com/Roalson40)
