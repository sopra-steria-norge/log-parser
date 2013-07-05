package no.osl.cdms.profile.web;

import no.osl.cdms.profile.api.MultiContext;
import no.osl.cdms.profile.log.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataRetriever {

    @Autowired
    private LogRepository logRepository;

    public DataRetriever() {

    }

    public List<MultiContext> getMultiContextsAfterTimestamp(String timestamp) {
        if (!isQueryInCache("getMultiContextsAfterTimestamp:" + timestamp)) {
            return logRepository.getMultiContextsAfterTimestamp(timestamp);
        }
        return null;

    }

    private boolean isQueryInCache(String query) {
        return false;
    }


}
