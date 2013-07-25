package no.osl.cdms.profile.interfaces.db;

public interface Procedure {

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    String getClassName();

    void setClassName(String className);

    String getMethod();

    void setMethod(String method);

    public boolean equals(Object obj);

    @Override
    public int hashCode();
}
