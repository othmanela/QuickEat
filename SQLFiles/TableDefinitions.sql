# Do NOT modify this file without consulting with me (Ralph). You should not have to interact
# with the database in any other way other than through the provided interface.

drop view Restaurants;
drop table FoodCategories;
drop table OwnerWaitTimes;
drop table WaitTimes;
drop table Reviews;
drop table BusinessNeighborhoods;
drop table BusinessTypes;
drop table SchoolProximities;
drop table Businesses;
drop table Users;

create table Users(
	userID varchar(50),
	userName varchar(100),
	url varchar(200),
	averageStars real,
	reviewCount integer,
	funnyVotes integer,
	usefulVotes integer,
	coolVotes integer,
	primary key(userID)
);


create table Businesses(
	businessID varchar(50),
	address varchar(200),
	businessName varchar(100),
	photoURL varchar(200),
	city varchar(50),
	state varchar(10),
	url varchar(200),
	reviewCount integer,
	stars real,
	longitude real,
	latitude real,
	primary key(businessID)
);

create table SchoolProximities(
	businessID varchar(50),
	schoolName varchar(100),
	primary key(businessID, schoolName),
	foreign key(businessID) references Businesses(businessID) on delete cascade
);

create table BusinessTypes(
	businessID varchar(50),
	categoryName varchar(50),
	primary key(businessID, categoryName),
	foreign key(businessID) references Businesses(businessID) on delete cascade
);

create table BusinessNeighborhoods(
	businessID varchar(50),
	neighborhoodName varchar(50),
	primary key(businessID, neighborhoodName),
	foreign key(businessID) references Businesses(businessID) on delete cascade
);

create table Reviews(
	reviewID varchar(50),
	userID varchar(50),
	businessID varchar(50),
	stars real,
	reviewDate date,
	reviewText varchar(10000),
	funnyVotes integer,
	usefulVotes integer,
	coolVotes integer,
	primary key(reviewID),
	foreign key(userID) references Users(userID) on delete cascade,
	foreign key(businessID) references Businesses(businessID) on delete cascade
);

create table WaitTimes(
	entryID integer,
	businessID varchar(50),
	timeLogged datetime not null,
	waitTime real not null,
	primary key(entryID, businessID),
	foreign key(businessID) references Businesses(businessID) on delete cascade
);

create table OwnerWaitTimes(
	entryID integer,
	businessID varchar(50),
	timeLogged datetime not null,
	waitTime real not null,
	primary key(entryID, businessID),
	foreign key(businessID) references Businesses(businessID) on delete cascade
);

create table FoodCategories(
	categoryName varchar(50),
	primary key (categoryName)
);

create view Restaurants as
select distinct b.*
from Businesses b, BusinessTypes bt
where b.businessID = bt.businessID and bt.categoryName in 
	(select distinct fc.categoryName from FoodCategories fc);


create table OwnerRegistry(
	username varchar(100),
	pass varchar(100),
	businessID varchar(50),
	primary key (username),
	foreign key(businessID) references Businesses(businessID)
);
