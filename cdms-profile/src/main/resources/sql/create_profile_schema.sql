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

CREATE TABLE CDM_PROFILE_LAYOUT
(
  NAME VARCHAR (100) PRIMARY KEY NOT NULL,
  JSON CLOB NOT NULL
);

Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('50','Lagre ICW','FlightServiceImpl','process');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('51','WS.getFlights','FlightWebservice','getFlights');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('52','getIataAirport','FlightRepository','getIataAirport');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('53','Cache.get','CacheUpdateRouteBean','getCdmFlightEntitiesFor');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('54','Cache.apply','CacheUpdateRouteBean','applyRules');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('55','Cache.populate','CacheUpdateRouteBean','populateCache');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('56','findFlightLeg','FlightRepository','findFlightLeg');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('57','findOrCreateCdmFlight','FlightRepository','findOrCreateCdmFlight');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('58','Scheduled-in','ScheduledInboundMilestone','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('59','Scheduled-out','ScheduledOutboundMilestone','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('60','FPL-in','FlightPlanActivationInbound','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('61','PA.shouldPunish','PunishmentAlgorithm','shouldPunishFlightLeg');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('62','DTAA.assignNonRegFlight','DynamicTableAssignmentAlgorithm','assignNonRegulatedFlight');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('63','Tsat.calc','TsatCalculatorServiceImpl','calculateTtotAndTsatFor');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('64','FPL-out','FlightPlanActivationOutbound','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('65','Publiser','UpdateMessageFactory','createUpdatesForPublishing');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('66','Vent','Wait',null);
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('67','Total','Total',null);
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('68','Mottak ICW','IcwMessageProcessorBean','process');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('69','Landed','Landed','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('70','Airborne','Airborne','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('71','Inblock','InBlock','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('72','Kjør milestone','Milestone','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('73','STAA.assignFlight','StaticTableAssignmentAlgorithm','assignFlight');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('74','transitionToEm2','FlightServiceImpl','processScheduledTransitionToEobtMinusTwoHours');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('75','Em2','EobtMinusTwoHours','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('76','transitionToFCT','FlightServiceImpl','processScheduledTransitionToFinalConfirmationOfTobt');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('77','transitionToTsatIssued','FlightServiceImpl','processScheduledTransitionToTsatIssued');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('78','FCT','FinalConfirmationOfTobt','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('79','TsatIssued','TsatIssued','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('80','PA.punish','PunishmentAlgorithm','punish');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('81','OffBlock','OffBlock','execute');
Insert into CDM_PROFILE_PROCEDURE (PROCEDURE_ID,NAME,CLASS,METHOD) values ('82','TakeOff','TakeOff','execute');
