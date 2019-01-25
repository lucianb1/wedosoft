package eu.wedosoft.contact;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static eu.wedosoft.contact.AppConstants.CLOSE_TD;
import static eu.wedosoft.contact.AppConstants.CLOSE_TR;
import static eu.wedosoft.contact.AppConstants.CONTACT_MESSAGE_PATH;
import static eu.wedosoft.contact.AppConstants.DATETIME_FORMAT;
import static eu.wedosoft.contact.AppConstants.HTML_FOOTER;
import static eu.wedosoft.contact.AppConstants.HTML_HEADER;
import static eu.wedosoft.contact.AppConstants.KEY_FOR_MESSAGES;
import static eu.wedosoft.contact.AppConstants.TD;
import static eu.wedosoft.contact.AppConstants.TR;

/**
 * Controller class for the incoming contact messages.
 *
 */
@RestController
@RequestMapping(CONTACT_MESSAGE_PATH)
public class ContactMessageController {
    private static final Logger LOGGER = Logger.getLogger(ContactMessageController.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private ContactMessageRepository contactMessageRepository;


    /**
     * Method used to persist the contact message and also to send an email to the configured email address with it.
     *
     * @param contactMessage the message which we received from the contact page
     * @return just a HTTP Status code
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<Void> sendMessage(@RequestBody ContactMessage contactMessage) {
        LOGGER.info("Incoming contact message from " + contactMessage.getName()
                + " with email address " + contactMessage.getEmail());

        //all fields are mandatory, hence the simple validation
        if (StringUtils.isEmpty(contactMessage.getName())
            || StringUtils.isEmpty(contactMessage.getEmail())
            || StringUtils.isEmpty(contactMessage.getSubject())
            || StringUtils.isEmpty(contactMessage.getMessage())) {

            LOGGER.warn("Validation problem for contact message from " + contactMessage.getName() + "!");

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            contactMessage.setSentOn(LocalDateTime.now());
            contactMessageRepository.save(contactMessage);

            LOGGER.info("Incoming contact message from " + contactMessage.getName() + " saved in DB.");

            mailService.sendContactMail(contactMessage);

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * Method to get all the existing contact mesages from the DB. Useful if for some reason we don't get emails.
     * Also, in the path there is a secret key/string hardcoded so that we can see all of 'em messages with a simple call
     * from any browser.
     *
     * @return all the messages we received through the contact page
     */
    @RequestMapping(value = "/get/" + KEY_FOR_MESSAGES, method = RequestMethod.GET)
    public ResponseEntity<String> getMessages() {
        LOGGER.info("Requested to show all existing contact messages.");

        StringBuilder htmlMessage = new StringBuilder(HTML_HEADER);
        List<ContactMessage> messages = contactMessageRepository.findAll();

        for (ContactMessage contactMessage: messages) {
            htmlMessage.append(addRowInTable(contactMessage));
        }

        htmlMessage.append(HTML_FOOTER);

        return new ResponseEntity<>(htmlMessage.toString(), HttpStatus.OK);
    }

    /**
     * Constructs a HTML Table row for each contact message.
     *
     * @param contactMessage the contact message
     * @return a HTML Table row
     */
    private String addRowInTable(ContactMessage contactMessage) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

        return TR
                + TD + contactMessage.getSentOn().format(formatter) + CLOSE_TD
                + TD + contactMessage.getName() + CLOSE_TD
                + TD + contactMessage.getEmail() + CLOSE_TD
                + TD + contactMessage.getSubject() + CLOSE_TD
                + TD + contactMessage.getMessage() + CLOSE_TD
             + CLOSE_TR;
    }
}
