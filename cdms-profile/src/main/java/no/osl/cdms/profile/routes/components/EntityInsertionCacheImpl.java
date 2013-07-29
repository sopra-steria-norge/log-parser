/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.routes.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import no.osl.cdms.profile.interfaces.EntityInsertionCache;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author nutgaard
 */
@Component
public class EntityInsertionCacheImpl implements EntityInsertionCache {

    private Collection<TimeMeasurement> cache;
    private long lastCacheFlush = System.currentTimeMillis();
    private static final int cacheLimit = 10000;
    private static final long cacheTimeoutLimit = 100000;

    public EntityInsertionCacheImpl() {
        this.cache = new ArrayList<TimeMeasurement>(110);
    }

    @Override
    public TimeMeasurement[] process(List<TimeMeasurement> s) {
        cache.addAll(s);
        if (isCacheLimit() || isTimeout()) {
            return flushCache();
        }
        return null;
    }

    private boolean isCacheLimit() {
        return cache.size() > cacheLimit;
    }

    private boolean isTimeout() {
        return (System.currentTimeMillis() - lastCacheFlush) > cacheTimeoutLimit;
    }

    private TimeMeasurement[] flushCache() {
        Logger.getRootLogger().error("Flushing cache: "+cache.size());
        TimeMeasurement[] copy = new TimeMeasurement[cache.size()];
        copy = cache.toArray(copy);
        cache.clear();
        lastCacheFlush = System.currentTimeMillis();
        return copy;
    }   
}
