create view CurrentWaitTimes as
select businessID, avg(waitTime) as currentWait
from WaitTimes
where time(timeLogged) between time(DATE_ADD(NOW(), INTERVAL -60 MINUTE)) and 
	time(DATE_ADD(NOW(), INTERVAL 60 MINUTE))
group by businessID;

create view CurrentOwnerWaitTime as
select owt.businessID, owt.waitTime as ownerWaitTime
from OwnerWaitTimes owt
where owt.timeLogged >= all(select owt2.timeLogged 
							from OwnerWaitTimes owt2
							where owt2.businessID = owt.businessID) and
	owt.timeLogged between DATE_ADD(NOW(), INTERVAL -120 MINUTE) and NOW();

create view BusinessDistances as
select businessID, sqrt(pow(longitude - 0, 2) + pow(latitude - 0, 2)) as distance
from Restaurants;

select b.businessID, cwt.currentWait + 10 * bd.distance as timeToEat, owt.waitTime
from Restaurants b left outer join OwnerWaitTimes owt on b.businessID = owt.businessID,
	CurrentWaitTimes cwt,
	BusinessDistances bd
where b.businessID = cwt.businessID and cwt.businessID = bd.businessID
order by timeToEat;

drop view CurrentWaitTimes;
drop view CurrentOwnerWaitTime;
drop view BusinessDistances;
