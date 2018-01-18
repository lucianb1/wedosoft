package eu.wedosoft.contact;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Async
@Service
@ConfigurationProperties(prefix = "emails")
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class);

    private List<String> adminAddresses = new ArrayList<>();

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Send email with the info from the contact message.
     *
     * @param contactMessage the class holding all the info from the contact page
     */
    @Override
    public void sendContactMail(ContactMessage contactMessage) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            LOGGER.info(String.format("Sending contact message to %d emails", adminAddresses.size()));
            if (adminAddresses != null && !adminAddresses.isEmpty()) {
                for (String adminEmail : adminAddresses) {
                    configureMessage(message, adminEmail, contactMessage);
                    mailSender.send(mimeMessage);
                }
            } else {
                LOGGER.error("No emails present in the yml file");
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred while trying to send contact message email", e);
        }
    }

    private void configureMessage(MimeMessageHelper message, String toEmail, ContactMessage contactMessage)
            throws MessagingException, UnsupportedEncodingException {
        message.setFrom(new InternetAddress("contact@wedodoft.eu", "Wedosoft"));
        message.setTo(toEmail);
        message.setSubject(contactMessage.getSubject());
        String fullContent = "Message: " +contactMessage.getMessage() + "\nEmail: " + contactMessage.getEmail()
                + "\nName: " + contactMessage.getName();
        message.setText(fullContent, false /* is html */);
    }

    public void setAdminAddresses(List<String> adminAddresses) {
        this.adminAddresses = adminAddresses;
    }

    public List<String> getAdminAddresses() {
        return adminAddresses;
    }
}
