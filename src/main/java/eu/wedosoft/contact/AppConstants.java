package eu.wedosoft.contact;

public class AppConstants {
    public static final String CONTACT_MESSAGE_PATH = "/contact/message";

    static final String KEY_FOR_MESSAGES = "y3bWYA7m1HjJ1NfmLyaJ";

    static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static final String TR = "<tr>";
    static final String TD = "<td>";
    static final String CLOSE_TR = "</tr>";
    static final String CLOSE_TD = "</td>";

    static final String HTML_HEADER =
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<title>Contact Messages</title>\n" +
            "</head>\n" +
            "\n" +
            "<body>" +
            "<table border=\"1\" style=\"width:100%\">" +
            "<tr><th>Sent on</th><th>Name</th><th>Email</th><th>Subject</th><th>Message</th></tr>";

    static final String HTML_FOOTER =
            "</table>\n" +
            "</body>\n" +
            "</html>";
}
