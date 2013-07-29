CREATE TABLE CDM_PROFILE_TIMEMEASUREMENT
(
  TIMEMEASUREMENT_ID INT PRIMARY KEY NOT NULL,
  PROCEDURE_ID INT NOT NULL ,
  MULTICONTEXT_ID INT,
  TIMESTAMP timestamp with time zone NOT NULL,
  DURATION VARCHAR(50)  DEFAULT 0
);
CREATE SEQUENCE TIMEMEASUREMENT_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE CDM_PROFILE_PROCEDURE
(
  PROCEDURE_ID INT PRIMARY KEY NOT NULL,
  NAME VARCHAR(100) ,
  CLASS VARCHAR(100) NOT NULL,
  METHOD VARCHAR(100),
  CONSTRAINT uniqueness UNIQUE (CLASS,METHOD)
);
CREATE SEQUENCE PROCEDURE_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE CDM_PROFILE_MULTICONTEXT
(
  MULTICONTEXT_ID INT PRIMARY KEY NOT NULL,
  START_TIME timestamp with time zone NOT NULL,
  END_TIME timestamp with time zone NOT NULL
);
CREATE SEQUENCE MULTICONTEXT_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE INDEX timeindex ON SYSTEM.CDM_PROFILE_TIMEMEASUREMENT(TIMESTAMP);
CREATE INDEX procedureindex ON SYSTEM.CDM_PROFILE_TIMEMEASUREMENT(PROCEDURE_ID);

DECLARE
  jobno NUMBER;
BEGIN
  dbms_job.submit(
      jobno,
      'DELETE FROM SYSTEM.CDM_PROFILE_MULTICONTEXT WHERE MULTICONTEXT_ID = (SELECT MULTICONTEXT_ID FROM SYSTEM.CDM_PROFILE_TIMEMEASUREMENT WHERE TIMESTAMP < SYSDATE - 15); DELETE FROM SYSTEM.CDM_PROFILE_TIMEMEASUREMENT WHERE TIMESTAMP < SYSDATE - 15;',
      sysdate,
      'trunc(sysdate)+1+4/24');
END;



Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Lagre ICW','FlightServiceImpl','process');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'WS.getFlights','FlightWebservice','getFlights');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'getIataAirport','FlightRepository','getIataAirport');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Cache.get','CacheUpdateRouteBean','getCdmFlightEntitiesFor');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Cache.apply','CacheUpdateRouteBean','applyRules');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Cache.populate','CacheUpdateRouteBean','populateCache');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'findFlightLeg','FlightRepository','findFlightLeg');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'findOrCreateCdmFlight','FlightRepository','findOrCreateCdmFlight');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Scheduled-in','ScheduledInboundMilestone','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Scheduled-out','ScheduledOutboundMilestone','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'FPL-in','FlightPlanActivationInbound','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'PA.shouldPunish','PunishmentAlgorithm','shouldPunishFlightLeg');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'DTAA.assignNonRegFlight','DynamicTableAssignmentAlgorithm','assignNonRegulatedFlight');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Tsat.calc','TsatCalculatorServiceImpl','calculateTtotAndTsatFor');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'FPL-out','FlightPlanActivationOutbound','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Publiser','UpdateMessageFactory','createUpdatesForPublishing');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Vent','Wait',null);
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Total','Total',null);
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Mottak ICW','IcwMessageProcessorBean','process');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Landed','Landed','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Airborne','Airborne','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Inblock','InBlock','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Kjør milestone','Milestone','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'STAA.assignFlight','StaticTableAssignmentAlgorithm','assignFlight');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'transitionToEm2','FlightServiceImpl','processScheduledTransitionToEobtMinusTwoHours');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Em2','EobtMinusTwoHours','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'transitionToFCT','FlightServiceImpl','processScheduledTransitionToFinalConfirmationOfTobt');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'transitionToTsatIssued','FlightServiceImpl','processScheduledTransitionToTsatIssued');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'FCT','FinalConfirmationOfTobt','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'TsatIssued','TsatIssued','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'PA.punish','PunishmentAlgorithm','punish');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'OffBlock','OffBlock','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'TakeOff','TakeOff','execute');
