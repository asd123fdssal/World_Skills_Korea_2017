package main;

public class Config {
	public static String DB_NAME = "sw3_102";
	public static String USER = "user";
	public static String PWD = "1234";
	public static String ADMIN = "root";
	public static String TABLE_NAME[] = { "TBL_Customer", "TBL_Bus", "TBL_Ticket" };
	public static String URL = "jdbc:mysql://localhost:3306/";

	public static String CREATE_DATABASE[] = { 
			"drop database if exists " + DB_NAME, 
			"create database " + DB_NAME,
			"use " + DB_NAME };
	
	public static String CREATE_TABLE [] = {
			"create table TBL_Customer(cID varchar(6) primary key not null, cPW varchar(4),cName varchar(10),cHP varchar(13))",
			"create table TBL_Bus(bNumber varchar(4) primary key not null,bDeparture varchar(5),bArrival varchar(5),bTime time,bElapse varchar(10),bCount varchar(1),bPrice int(6))",
			"create table TBL_Ticket(bDate date,bNumber varchar(4),bNumber2 varchar(5),bSeat int(2),cId varchar(6), bPrice int(6),bState varchar(1))"
	};
	
	public static String IMPORT = "load data local infile '%s' into table %s character set 'UTF8' fields terminated by '\t' ignore 1 lines";
	
	public static String GRANT = "grant select, delete, update, insert on "+ DB_NAME+".* to '"+USER+"' identified by '"+PWD+"'";
}
