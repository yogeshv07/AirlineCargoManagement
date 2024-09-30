-- Create the database
CREATE DATABASE airlinecargo;

-- Use the database
USE airlinecargo;

-- Create the Employee table
CREATE TABLE Employee (
  EmployeeID INT AUTO_INCREMENT,
  Username VARCHAR(255) NOT NULL,
  Password VARCHAR(255) NOT NULL,
  PRIMARY KEY (EmployeeID)
);

-- Create the AirCargo table
CREATE TABLE AirCargo (
  ID INT AUTO_INCREMENT,
  TrackingID VARCHAR(255) NOT NULL,
  SenderName VARCHAR(255) NOT NULL,
  SenderAddress VARCHAR(255) NOT NULL,
  ReceiverName VARCHAR(255) NOT NULL,
  ReceiverAddress VARCHAR(255) NOT NULL,
  CargoType VARCHAR(255) NOT NULL,
  Weight FLOAT NOT NULL,
  Charge FLOAT NOT NULL,
  Status VARCHAR(255) NOT NULL,
  PRIMARY KEY (ID)
);

-- Create the Flight table
CREATE TABLE Flight (
  FlightID INT AUTO_INCREMENT,
  DepartureAirport VARCHAR(255) NOT NULL,
  ArrivalAirport VARCHAR(255) NOT NULL,
  DepartureTime DATETIME NOT NULL,
  ArrivalTime DATETIME NOT NULL,
  PRIMARY KEY (FlightID)
);

-- Create the CargoBooking table
CREATE TABLE CargoBooking (
  BookingID INT AUTO_INCREMENT,
  CargoID INT NOT NULL,
  FlightID INT NOT NULL,
  PRIMARY KEY (BookingID),
  FOREIGN KEY (CargoID) REFERENCES AirCargo(ID),
  FOREIGN KEY (FlightID) REFERENCES Flight(FlightID)
);

-- Create the Customer table
CREATE TABLE Customer (
  CustomerID INT AUTO_INCREMENT,
  Name VARCHAR(255) NOT NULL,
  ContactInfo VARCHAR(255) NOT NULL,
  PRIMARY KEY (CustomerID)
);
