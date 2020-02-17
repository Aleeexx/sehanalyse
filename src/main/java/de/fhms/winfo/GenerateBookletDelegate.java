package de.fhms.winfo;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service("generateBookletDelegate")
public class GenerateBookletDelegate implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger("GENERATE_BOOKLET_DELEGATE");

    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("START");

        String linsen_type = (((String) execution.getVariable("lens_type")).equals("single")
                ? "Einstärkengläser" : "Mehrstärkengläser" );
        String glas_type = ((String) execution.getVariable("glas_type")).equals("standard")
                ? "Standardgläser" : "Individualgläser";
        String lamination_type = ((String) execution.getVariable("lamination_type")).equals("standard")
                ? "Standardbeschichtung" : "Highend-Beschichtung";


        String welcome = "Sehr geehrte(r) Herr/Frau "
                + execution.getVariable("first_name") + " "
                + execution.getVariable("last_name")
                + ",\n\n" + "herzlich möchten wir uns für Ihr Vertrauen bedanken. Wir hoffen, dass Sie zufrieden mit "
                + "Ihrer neu erworbenen Brille sind. Damit Sie auch in Zukunft nachsehen können, was Ihre Sehanalyse "
                + "ergeben hat, senden wir Ihnen Ihr Analyse E-Booklet. \n\n\n";

        String yourEyes = "Ihre Augen\n"
                + "\tDioptrien: " + execution.getVariable("dioptrien") + "\n\n";

        String visionProblems = "Sehprobleme\n\t"
                + (execution.getVariable("vision_problems") != null
                    ? execution.getVariable("vision_problems") + "\n" : "")
                + "\tNachtsehprobleme: " + ((boolean)execution.getVariable("night_vision_problems")
                    ? "Ja" : "Nein") + "\n\n";

        String yourEnvironment = "Lebensstil :"
                + this.switchOnLebensstil(((String)execution.getVariable("living_environment"))) + "\n\n";

        String yourGlasses = "Ihr Glas\n"
                + "\tLinsentyp: " + linsen_type + "\n"
                + "\tGlasart: " + glas_type + "\n"
                + "\tGlasmaterial: " + execution.getVariable("glas_material") + "\n"
                + "\tBeschichtung: " + lamination_type + "\n"
                + "\tSonnenschutz: " + ((boolean) execution.getVariable("wants_shading")
                    ? "Ja" : "Nein") + "\n\n\n";

        String footer = "Mit freundlichen Grüßen\nDie Firma brillentick";

        String booklet = welcome + "ANALYSE\n\n" + yourEyes + visionProblems + yourEnvironment + yourGlasses + footer;
        execution.setVariable("booklet", booklet);

        LOGGER.info("ENDE");
    }

    private String switchOnLebensstil(String key) {
        switch(key) {
            case "work":
                return "Arbeitsleben";
            case "vehicle":
                return "Auto/Motorrad";
            case "digital_life":
                return "Digitales Leben";
            case "sports":
                return "Sport";
            case "sun":
                return "Sonne";
            default:
                return "";
        }
    }
}
