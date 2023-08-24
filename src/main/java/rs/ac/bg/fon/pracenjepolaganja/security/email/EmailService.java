package rs.ac.bg.fon.pracenjepolaganja.security.email;

/**
 * Represent interface of email operations.
 * Contains sendSimpleEmail and sendEmailWithAttachment.
 *
 * @author Vuk Manojlovic
 */
public interface EmailService {

    /**
     * Sends email for registration of members.
     *
     * @param email object that contains details of email.
     * @return String if email is sent successfully.
     */
    String sendRegistrationEmail(EmailDetails email);

    /**
     * Sends simple email with details inside EmailDetails object.
     *
     * @param email object that contains details of email.
     * @return String if email is sent successfully.
     */
    String sendSimpleEmail(EmailDetails email);

    /**
     * Sends email with attachment.
     *
     * @param email object that contains details of email
     * @return String if email is sent successfully.
     */
    String sendEmailWithAttachment(EmailDetails email);

    /**
     * Sends email for changing the password.
     *
     * @param email of member
     * @return String if email is sent successfully
     */
    String sendEmailChangePassword(EmailDetails email);

    /**
     * Sends email for user who forgot password
     *
     * @param email of member
     * @return if email is sent
     */
    String sendEmailForgottenPassword(EmailDetails email);
}
