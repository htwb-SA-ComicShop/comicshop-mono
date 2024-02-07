package com.example.notification.core.domain.service.interfaces;

import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;

/**
 * Service interface for managing emails.
 */
public interface IEmailService {

    /**
     * Sends a simple email without attachments.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param text    The content of the email.
     * @throws SendFailedException If there is a failure while sending the email.
     */
    void sendEmail(String to, String subject, String text) throws SendFailedException;

    /**
     * Sends a simple email with an attachment.
     *
     * @param to               The recipient's email address.
     * @param subject          The subject of the email.
     * @param text             The content of the email.
     * @param pathToAttachment The file path to the attachment.
     * @throws MessagingException If there is a messaging-related issue, such as attachment handling.
     */
    void sendEmail(String to, String subject, String text, String pathToAttachment) throws MessagingException;
}
