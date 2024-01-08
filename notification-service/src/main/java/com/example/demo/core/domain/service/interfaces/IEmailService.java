package com.example.demo.core.domain.service.interfaces;

import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;

public interface IEmailService {

    /**
     * Sends a simple Email without attachments
     * @param to
     * @param subject
     * @param text
     * @throws SendFailedException
     */
    void sendEmail(String to, String subject, String text)throws SendFailedException;

    /**
     * Sends a simple Email with an attachments
     * @param to
     * @param subject
     * @param text
     * @param pathToAttachment
     * @throws SendFailedException
     */
    void sendEmail(String to, String subject, String text, String pathToAttachment) throws MessagingException;
}
