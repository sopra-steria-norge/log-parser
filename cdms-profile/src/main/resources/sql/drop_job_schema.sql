DECLARE jobno NUMBER;

BEGIN
  SELECT JOB INTO jobno FROM SYS.DBA_JOBS WHERE WHAT='DELETE FROM SYSTEM.CDM_PROFILE_MULTICONTEXT WHERE MULTICONTEXT_ID = (SELECT MULTICONTEXT_ID FROM SYSTEM.CDM_PROFILE_TIMEMEASUREMENT WHERE TIMESTAMP < SYSDATE - 15); DELETE FROM SYSTEM.CDM_PROFILE_TIMEMEASUREMENT WHERE TIMESTAMP < SYSDATE - 15;';
  dbms_job.remove(jobno);
END;
