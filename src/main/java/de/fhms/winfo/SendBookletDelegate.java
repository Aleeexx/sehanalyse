package de.fhms.winfo;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service("sendBookletDelegate")
public class SendBookletDelegate implements JavaDelegate {

    @Autowired
    private EmailService emailService;
    private final static Logger LOGGER = Logger.getLogger("SEND_BOOKLET_DELEGATE");

    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info(
                "Sende Analyse E-Booklet an "
                + execution.getVariable("first_name") + " "
                + execution.getVariable("last_name"));

        String to = (String) execution.getVariable("email");
        String subject = "Analyse E-Booklet für "
                + execution.getVariable("first_name") + " "
                + execution.getVariable("last_name");
        String message = (String) execution.getVariable("booklet");

        boolean isSent = emailService.sendEmail(to, subject, message);

        if(isSent) LOGGER.info("Analyse E-Booklet erfolgreich gesendet.");
        else LOGGER.info("Analyse E-Booklet konnte nicht gesendet werden.");
    }
}
