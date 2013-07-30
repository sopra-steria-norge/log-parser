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





-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Lagre ICW','FlightServiceImpl','process');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'WS.getFlights','FlightWebservice','getFlights');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'getIataAirport','FlightRepository','getIataAirport');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Cache.get','CacheUpdateRouteBean','getCdmFlightEntitiesFor');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Cache.apply','CacheUpdateRouteBean','applyRules');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Cache.populate','CacheUpdateRouteBean','populateCache');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'findFlightLeg','FlightRepository','findFlightLeg');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'findOrCreateCdmFlight','FlightRepository','findOrCreateCdmFlight');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Scheduled-in','ScheduledInboundMilestone','execute');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Scheduled-out','ScheduledOutboundMilestone','execute');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'FPL-in','FlightPlanActivationInbound','execute');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'PA.shouldPunish','PunishmentAlgorithm','shouldPunishFlightLeg');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'DTAA.assignNonRegFlight','DynamicTableAssignmentAlgorithm','assignNonRegulatedFlight');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Tsat.calc','TsatCalculatorServiceImpl','calculateTtotAndTsatFor');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'FPL-out','FlightPlanActivationOutbound','execute');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Publiser','UpdateMessageFactory','createUpdatesForPublishing');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Vent','Wait',null);
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Total','Total',null);
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Mottak ICW','IcwMessageProcessorBean','process');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Landed','Landed','execute');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Airborne','Airborne','execute');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Inblock','InBlock','execute');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Kjør milestone','Milestone','execute');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'STAA.assignFlight','StaticTableAssignmentAlgorithm','assignFlight');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'transitionToEm2','FlightServiceImpl','processScheduledTransitionToEobtMinusTwoHours');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'Em2','EobtMinusTwoHours','execute');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'transitionToFCT','FlightServiceImpl','processScheduledTransitionToFinalConfirmationOfTobt');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'transitionToTsatIssued','FlightServiceImpl','processScheduledTransitionToTsatIssued');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'FCT','FinalConfirmationOfTobt','execute');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'TsatIssued','TsatIssued','execute');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'PA.punish','PunishmentAlgorithm','punish');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'OffBlock','OffBlock','execute');
-- Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'TakeOff','TakeOff','execute');

Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'FlightServiceImpl','process');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'FlightWebservice','getFlights');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'FlightRepository','getIataAirport');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'CacheUpdateRouteBean','getCdmFlightEntitiesFor');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'CacheUpdateRouteBean','applyRules');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'CacheUpdateRouteBean','populateCache');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'FlightRepository','findFlightLeg');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'FlightRepository','findOrCreateCdmFlight');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'ScheduledInboundMilestone','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'ScheduledOutboundMilestone','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'FlightPlanActivationInbound','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'PunishmentAlgorithm','shouldPunishFlightLeg');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'DynamicTableAssignmentAlgorithm','assignNonRegulatedFlight');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, 'TSAT','TsatCalculatorServiceImpl','calculateTtotAndTsatFor');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'FlightPlanActivationOutbound','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'UpdateMessageFactory','createUpdatesForPublishing');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'Wait',null);
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval,'ICW','Total',null);
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'IcwMessageProcessorBean','process');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'Landed','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'Airborne','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'InBlock','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'Milestone','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'StaticTableAssignmentAlgorithm','assignFlight');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'FlightServiceImpl','processScheduledTransitionToEobtMinusTwoHours');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'EobtMinusTwoHours','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'FlightServiceImpl','processScheduledTransitionToFinalConfirmationOfTobt');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'FlightServiceImpl','processScheduledTransitionToTsatIssued');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'FinalConfirmationOfTobt','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'TsatIssued','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'PunishmentAlgorithm','punish');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'OffBlock','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values (PROCEDURE_SEQ.nextval, NULL,'TakeOff','execute');

