#DRONE INTERVIEW
##1. Run the application
run the command below in the project root terminal
update maven on your POM file
mvn spring-boot:run


This project has five endpoints 

##1. Register drone endpoint
localhost:8080/drone/register

##2.Load medication to drone
localhost:8080/drone/loadDrone

##3.Check available drones
localhost:8080/drone/availableDrones

##4. check loaded medication items for a given drone
You need to pass the id of the drone you are searching to get  the list of medication loaded to it.
localhost:8080/drone/loadedMedication?id=1

##5.Check drone percentage by passing the id to the endpoint
localhost:8080/drone/checkPercentage?id=1

#To check the h2 database structure
##open h2 console 
http://localhost:8080/h2-console/



