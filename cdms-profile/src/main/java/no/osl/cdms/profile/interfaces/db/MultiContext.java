package no.osl.cdms.profile.interfaces.db;

import java.util.Date;


public interface MultiContext {

    int getId();

    void setId(int id);

    Date getStart();

    void setStart(Date start);

    Date getEnd();

    void setEnd(Date end);
}
