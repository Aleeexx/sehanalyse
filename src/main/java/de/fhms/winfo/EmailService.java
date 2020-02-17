package de.fhms.winfo;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EmailService {

    private final static Logger LOGGER = Logger.getLogger("EMAIL_SERVICE");

    @Value("${email.host}")    private String host;
    @Value("${email.sender}")  private String sender;

    public boolean sendEmail(String to, String subject, String message) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(host);
            email.setFrom(sender);
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(to);
            email.send();
            LOGGER.info("Email \'" + subject + "\' gesendet an  " + to + ".");

        } catch  (Exception e) {
            LOGGER.warning("Email konnte nicht gesendet werden:" + e.getMessage());
            return false;
        }
        return true;
    }

}
