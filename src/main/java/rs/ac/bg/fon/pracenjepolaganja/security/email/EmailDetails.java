package rs.ac.bg.fon.pracenjepolaganja.security.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent email that is sent to members.
 *
 * @author Vuk Manojlovic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails {

    /**
     * Represent email of recipient
     * EmailDetails must be in valid form.
     * EmailDetails is also username for member.
     */
    @NotBlank(message = "Polje email je obavezno")
    @Email(message = "Email mora biti u validnom fromatu")
    private String recipient;

    /**
     * Body of message that email contains.
     */
    private String msgBody;

    /**
     * Title of email.
     */
    private String subject;

    /**
     * Attachment of email.
     */
    private String attachment;
}
