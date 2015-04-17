*************************************************************
*      Nicola Ferrante - nicola.ferrante@gmail.com          *
*       Resource Scheduler JPmorgan test exercise           *
*************************************************************

1. Resource Scheduler Overview and build instructions

Resource scheduler exercise has been developed mostly with java.util.concurrent libraries without external messaging
framework in order to be compliant with the guidelines in the requirements paper
So this is a multi-thread application builded applying some design pattern like Low Coupling, High Cohesion,
Polimorphism, Controller, Strategy, Pure Fabrication Factory and Singleton  
This is a first implementation so it could be improved (using for example a Messaging system) in order to put it 
in a production environnment 
The project has builded with Maven and the only external libraries added are Junit and Log4j.
In order to build and run unit tests you can launch the following instruction from a command line:
     >mvn install
in order to build eclipse project files you can launch the following intruction from a command line:
     >mvn eclipse:eclipse
and then import the project into Eclipse as an "Existing Project".

2. Implemented points

All points of the requirement paper have been implemented unless the "Termination Messages" one 


3. Main Classes and interactions among them

The main class is ResourceScheduler.java that mainly has:
	- an inbound LinkedBlockingQueue in which threads can put/poll concurrently items without having interference
	- a semaphore that models the available resources
	- a priority startegy interface in order to implement the strategy for messages to be delivered to Gateway
	- a set of groupID that are in blacklist (filled when a cancellation is received) used to filter inbound messages 
When that class is created you can choose the number of gateway resources available (and also the message priority stategy)
The ResourceScheduler works as a thread that dequeue a message basing on the strategy previously applied and send it
to the Gateway (that has a reference to the same semaphore) according with available resources (in fact it acquires 
the semaphore and when send the message and then the Gateway releases the semaphore when has processed the message and become
newly available)

All the interfaces of this projects has been decoupled from the implementation using pure fabrication factories in order
to have the creation logic all in a class.
Implementations of interfaces has been made as IMMUTABLE objects. MessageImpl.java has also equals and hashcode in order to be 
used into the Set without having collateral effects

The two priority strategies I have implemented are:
	- GroupIDPriorityStrategyImpl: the strategy suggested in the requirement paper
	- OddAndEvenMessageIDPriorityStrategyImpl: this strategy select first messages that have the same ID parity of the 
												current messagfe processed (odd or even)
Of course the strategies implementation are Singleton object in order to avoid useless object creations, and they
can be accessed through the StrategyFactory.





