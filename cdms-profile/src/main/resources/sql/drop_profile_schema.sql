drop table CDM_PROFILE_TIMEMEASUREMENT cascade constraints;
drop table CDM_PROFILE_PROCEDURE cascade constraints;
drop table CDM_PROFILE_MULTICONTEXT cascade constraints;

drop sequence TIMEMEASUREMENT_SEQ;
drop sequence PROCEDURE_SEQ;
drop sequence MULTICONTEXT_SEQ;

DROP INDEX timeindex;
DROP INDEX procedureindex;