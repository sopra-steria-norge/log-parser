/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.factories;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.MultiContext;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.log.LogRepository;
import no.osl.cdms.profile.log.ProcedureEntity;
import no.osl.cdms.profile.log.MultiContextEntity;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import no.osl.cdms.profile.utilities.GuavaHelpers;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nutgaard
 */
public class EntityFactory {

    private static EntityFactory entityFactory;

    @Autowired
    private LogRepository logRepository;

    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public static EntityFactory getInstance() {
        if (entityFactory == null) {
            entityFactory = new EntityFactory();
        }
        return entityFactory;
    }

    public TimeMeasurement createTimeMeasurement(Procedure procedure, MultiContext context, String timestamp, String duration) {
        return (TimeMeasurement) (new TimeMeasurementEntity((ProcedureEntity) procedure, (MultiContextEntity) context, timestamp, duration));
    }

    public TimeMeasurement createTimeMeasurement(Procedure procedure, String timestamp, String duration) {
        return (TimeMeasurement) (new TimeMeasurementEntity((ProcedureEntity) procedure, null, timestamp, duration));
    }

    public MultiContext createMultiContext(String start, String end) {
        MultiContext mc = new MultiContextEntity(start, end);
        return mc;
    }

    public Procedure createProcedure(String className, String methodName) {
        return createProcedure("", className, methodName);
    }

    public Procedure createProcedure(String name, String className, String methodName) {
        Procedure newProcedure = new ProcedureEntity(name, className, methodName);
        Procedure existingProcedure = logRepository.getEqualProcedure(newProcedure);

        if (existingProcedure != null) return existingProcedure;
        return newProcedure;
    }

    public List<TimeMeasurement> createMultiContext(Map<String, String> properties) {
        String start = properties.get("MultiThreadContext.Total.start");
        String end = properties.get("MultiThreadContext.Total.end");
        MultiContextEntity mcme = new MultiContextEntity(start, end);

        Map<String, String> measuredFiltered = Maps.filterEntries(properties, GuavaHelpers.isDuration());
        List<TimeMeasurement> measured = Lists.newLinkedList(Iterables.transform(measuredFiltered.entrySet(), GuavaHelpers.getConverter(properties)));
        for (TimeMeasurement tm : measured) {
            tm.setMultiContext(mcme);
        }
        return measured;
    }

    public List<TimeMeasurement> createLocalContextTimemeasurement(Map<String, String> properties) {
        String timestamp = properties.get("timestamp");
        String duration = properties.get("LocalThreadContext.duration");
        String[] id = GuavaHelpers.parseKey("LocalThreadContext.id", properties);
        String classname = id[0];
        String methodname = id[1];
        Procedure m = createProcedure(classname, methodname);
        //Procedure m = new ProcedureEntity("",classname, methodname);
        TimeMeasurement tm = createTimeMeasurement(m, timestamp, duration);
        tm.setProcedure((ProcedureEntity) m);
        List<TimeMeasurement> list = Lists.newLinkedList();
        list.add(tm);
        return list;
    }

    public List<TimeMeasurement> createTimemeasurement(Map<String, String> properties) {
        if (properties.get("LocalThreadContext.id") != null) {
            return createLocalContextTimemeasurement(properties);
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
}

