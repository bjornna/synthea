package org.mitre.synthea.export;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.mitre.synthea.world.agents.Person;
import org.mitre.synthea.world.concepts.HealthRecord.Code;
import org.mitre.synthea.world.concepts.HealthRecord.Encounter;
import org.mitre.synthea.world.concepts.HealthRecord.Entry;
import org.mitre.synthea.world.concepts.HealthRecord.Observation;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OpenEhrFlatExporter {

    private static String SYSTEM_ID = "synthea_generator";
    private static Map<String, List<Code>> multiObservations = new ConcurrentHashMap<>();
    private static final Configuration TEMPLATES = templateConfiguration();
    private static String createOpenEhrUID() {
        return UUID.randomUUID().toString() + "::" + SYSTEM_ID + "::1";
    }
    /**
     * This is a dummy object for FreeMarker, because the library cannot access static class methods
     * such as UUID.randomUUID()
     */
    private static final Object UUID_GEN = new Object() {
        public String toString() {
            return UUID.randomUUID().toString();
        }
    };
    private static final Object OPENEHR_UID_GEN = new Object() {
        public String toString() {
      return OpenEhrFlatExporter.createOpenEhrUID();
        }
    };

    private static Configuration templateConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setLogTemplateExceptions(false);
        try {
            configuration.setSetting("object_wrapper",
                    "DefaultObjectWrapper(2.3.26, forceLegacyNonListCollections=false, "
                            + "iterableSupport=true, exposeFields=true)");
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        configuration.setAPIBuiltinEnabled(true);
        configuration.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(),
                "templates/openehr");
        return configuration;
    }

    /**
     * Produce and export a person's record in the text format.
     *
     * @param person  Person to export
     * @param fileTag Tag to add to the filename
     * @param time    Time the simulation ended
     * @throws IOException if any error occurs writing to the standard export location
     */
    public static void exportAll(Person person, String fileTag, long time) throws IOException {
        boolean debug = false;
        if (debug) {
            System.out.println("Heisann - jeg skal eksportere openEHR jeg");
            System.out.println("|- Person: " + person);
            System.out.println("|- Filetag: " + fileTag);
            System.out.println("|- Stoptime: " + time);
        }

        List<Encounter> encounters = person.record.encounters;
        for (Encounter encounter : encounters) {
            String encounterId = createOpenEhrUID();
            StringBuffer report = new StringBuffer();
            report.append("Encounter: ");
            report.append("\n|- #period: " + new Date(encounter.start) + "-" + new Date(encounter.stop));
            report.append("\n|- #type: " + encounter.type);
            report.append("\n|- #procedures: " + encounter.procedures.size());
            report.append("\n|- #observations: " + encounter.observations.size());
            report.append("\n|- #conditions: " + encounter.conditions.size());
            report.append("\n|- #uid: "+ encounterId);
            System.out.println(report.toString());
            encounter.procedures.stream().forEach(p -> {

            });
            encounter.conditions.stream().forEach(c -> {

            });
            encounter.observations.stream().forEach(o -> {

            });
            encounter.allergies.stream().forEach(a -> {

            });
            encounter.medications.stream().forEach(m -> {

            });
            encounter.careplans.stream().forEach(cp -> {

            });

        }


    }
}
