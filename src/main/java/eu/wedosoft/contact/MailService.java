package eu.wedosoft.contact;

import org.springframework.stereotype.Service;

/**
 * Mail Sender service.
 *
 */
@Service
public interface MailService {

    /**
     * Send email with the info from the contact message.
     *
     * @param contactMessage the class holding all the info from the contact page
     */
    void sendContactMail(ContactMessage contactMessage);

}
