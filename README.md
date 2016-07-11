# SensFloor Client Java

### SensFloor Client Java
This is a library written in Java that encapsulates the programmatic connection to a [SensFloor by FutureShape](http://www.future-shape.com/index.php/en/technologies/23/sensfloor-large-area-sensor-system).

It has been developed in the context of the module **Smart Home Lab** in the summer semester 2016 at **Stuttgart Media University**. The module was part of the degree program **Computer Science and Media**.

The goal of the module was to design and develop interactive smart home solutions to assist elderly persons with certain disabilities.

The **SensFloor** system is an innovative technology that can be laid under a room's floor and that detects people's movements. 
In certain intervals, the state of the SensFloor is send via sockets. This Java client connects itself to the **SensFloor**, receives the  events, parses the given raw data into objects and calculates further information such as the direction of the movement. 

### Installation
In order to use the Java client library you need to:
1. Clone the repository
2. Configure your settings in the file **sensfloor-config.properties**

### Configuration
You can configure different properties for the connection to the **SensFloor** in the properties file **sensfloor-config.properties**. 

**Necessary properties are:**

-  The protocol, host and port to be used for the connection.
		
		protocol=http
		host=localhost
		port=8000
- The SensFloor sends the events every 50ms. If you want all events to be handled, set this value to 0. Otherwise you can define an interval here, e.g. only every 40th event is handled. 

		socketDelay=40

**Optional properties are:** 

- You can already set a reference point here that is used for the direction calculations. If you want to define it later, you can set the X and Y values to 0.
		
		referenceX=0
		referenceY=0

- If nobody is standing on the SensFloor, but the carpet is measuring capacities anyway, you can define a threshold as the minimum value to be considered. 

		capacityThreshold=90


### Usage
You can either extend the Java client with your own additional features or just use it as a library. The project also contains an example project that shows the usage of the library. 
To use it as a library, you need to:

1. Initialize a SensFloor object with the defined properties file
2. Start the connection to the real **SensFloor**
3. Write your own EventHandler that extends the abstract class ClusterEventHandler.
4. Put your custom business logic in this handler.
4. Add the handler to the SensFloor object. 


### Team
The concept and implementation have been prepared by:

- Thomas Derleth
- Jörg Einfeldt
- Merle Hiort
- Marc Stauffer

### License
This project is licensed under the terms of the MIT license