/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.route;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Date;
import java.util.List;
import java.util.Map;

import no.osl.cdms.profile.interfaces.db.Procedure;
import no.osl.cdms.profile.interfaces.db.MultiContext;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.interfaces.EntityFactory;
import no.osl.cdms.profile.log.LogRepository;
import no.osl.cdms.profile.log.ProcedureEntity;
import no.osl.cdms.profile.log.MultiContextEntity;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import no.osl.cdms.profile.utilities.GuavaHelpers;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author nutgaard
 */
@Component
public class EntityFactoryImpl implements EntityFactory {

    @Autowired
    private GuavaHelpers guavaHelpers = new GuavaHelpers(this);
    
    @Autowired
    private LogRepository logRepository;

    public void setLogRepository(LogRepository logrepo) {
        this.logRepository = logrepo;
    }

    public TimeMeasurement createTimeMeasurement(Procedure procedure, MultiContext context, Date timestamp, String duration) {
        return (TimeMeasurement) (new TimeMeasurementEntity((ProcedureEntity) procedure, (MultiContextEntity) context, timestamp, duration));
    }

    public TimeMeasurement createTimeMeasurement(Procedure procedure, Date timestamp, String duration) {
        return (TimeMeasurement) (new TimeMeasurementEntity((ProcedureEntity) procedure, null, timestamp, duration));
    }

    public MultiContext createMultiContext(Date start, Date end) {
        MultiContext mc = new MultiContextEntity(start, end);
        return mc;
    }

    public Procedure createProcedure(String className, String methodName) {
        return createProcedure(null, className, methodName);
    }

    public Procedure createProcedure(String name, String className, String methodName) {
        if (name != null && name.equals("")) {
            name = null;
        }
        if (className != null && className.equals("")) {
            className = null;
        }
        if (methodName != null && methodName.equals("")) {
            methodName = null;
        }
        Procedure p = new ProcedureEntity(name, className, methodName);
        p =logRepository.getEqualPersistedProcedure(p);
        return p;
    }

    public List<TimeMeasurement> createMultiContext(Map<String, String> properties) {
        DateTime start = new DateTime(properties.get("MultiThreadContext.Total.start"));
        DateTime end = new DateTime(properties.get("MultiThreadContext.Total.end"));
        MultiContextEntity mcme = new MultiContextEntity(start.toDate(), end.toDate());

        Map<String, String> measuredFiltered = Maps.filterEntries(properties, guavaHelpers.isDuration());
        List<TimeMeasurement> measured = Lists.newLinkedList(Iterables.transform(measuredFiltered.entrySet(), guavaHelpers.getConverter(properties)));
        for (TimeMeasurement tm : measured) {
            tm.setMultiContext(mcme);
        }
        return measured;
    }

    public List<TimeMeasurement> createLocalContextTimeMeasurement(Map<String, String> properties) {
        String duration = properties.get("LocalThreadContext.duration");
        String[] id = guavaHelpers.parseKey("LocalThreadContext.id", properties);
        String classname = id[0];
        String methodname = id[1];
        Procedure m = createProcedure(classname, methodname);
        TimeMeasurement tm = createTimeMeasurement(m, guavaHelpers.parseDateString(properties.get("timestamp")), duration);
        tm.setProcedure((ProcedureEntity) m);
        List<TimeMeasurement> list = Lists.newLinkedList();
        list.add(tm);
        return list;
    }

    public List<TimeMeasurement> createTimemeasurement(Map<String, String> properties) {
        if (properties.get("LocalThreadContext.id") != null) {
            return createLocalContextTimeMeasurement(properties);
        } else {
            return createMultiContext(properties);
        }
    }

    public List<Object> splitTimeMeasurement(TimeMeasurement timeMeasurement) {
        List<Object> databaseEntities = Lists.newLinkedList();
        Object database_entity;
        if ((database_entity = timeMeasurement.getProcedure()) != null) {
            databaseEntities.add(database_entity);
        }
        if ((database_entity = timeMeasurement.getMultiContext()) != null) {
            databaseEntities.add(database_entity);
        }
        return databaseEntities;
    }

    @Override
    public List<TimeMeasurement> process(Map<String, String> s) {
        return createTimemeasurement(s);
    }
}
