package de.fhms.winfo;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service("generateOrderConfirmationDelegate")
public class GenerateOrderConfirmationDelegate implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger("GENERATE_ORDER_CONFIRMATION_DELEGATE");

    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("START");

        String linsen_type = (((String) execution.getVariable("lens_type")).equals("single")
                ? "Einstärkengläser" : "Mehrstärkengläser" );
        String glas_type = ((String) execution.getVariable("glas_type")).equals("standard")
                ? "Standardgläser" : "Individualgläser";
        String lamination_type = ((String) execution.getVariable("lamination_type")).equals("standard")
                ? "Standardbeschichtung" : "Highend-Beschichtung";

        String orderConfirmation = "AUFTRAGSBESTÄTIGUNG\n\n"
                + "Der Kunde \n"
                + execution.getVariable("first_name") + " "
                + execution.getVariable("last_name") + "\n"
                + execution.getVariable("email") + "\n\n\n"
                + "bestätigt hiermit den Kauf einer Brille.\n\n"
                + "Daten zum Glas\n"
                + "Linsentyp: " + linsen_type + "\n"
                + "Glasart: " + glas_type + "\n"
                + "Glasmaterial: " + execution.getVariable("glas_material") + "\n"
                + "Beschichtung: " + lamination_type + "\n"
                + "Sonnenschutz: " + ((boolean) execution.getVariable("wants_shading")
                    ? "Ja" : "Nein") + "\n\n\n"
                + execution.getVariable("first_name") + " "
                + execution.getVariable("last_name") + "\n";

        execution.setVariable("order_confirmation", orderConfirmation);

        LOGGER.info("ENDE");
    }
}
