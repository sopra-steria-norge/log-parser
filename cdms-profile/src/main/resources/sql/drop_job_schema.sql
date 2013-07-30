DECLARE jobno NUMBER;

BEGIN
  SELECT SUM(JOB) INTO jobno FROM SYS.DBA_JOBS WHERE WHAT='DELETE FROM SYSTEM.CDM_PROFILE_MULTICONTEXT WHERE MULTICONTEXT_ID = (SELECT MULTICONTEXT_ID FROM SYSTEM.CDM_PROFILE_TIMEMEASUREMENT WHERE TIMESTAMP < SYSDATE - 15); DELETE FROM SYSTEM.CDM_PROFILE_TIMEMEASUREMENT WHERE TIMESTAMP < SYSDATE - 15;' AND ROWNUM = 1;
  IF jobno != 0 THEN
  dbms_job.remove(jobno);
  END IF;
END;