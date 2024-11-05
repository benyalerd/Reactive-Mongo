package com.example.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import freemarker.template.Configuration;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration configuration;

    public void sendLatePaymentNotification(String mailTo, Map<String, String> model) {


        MimeMessage message = mailSender.createMimeMessage();
        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Template template = configuration.getTemplate("latePayment.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setTo(mailTo);
            helper.setFrom("benya.lerd@gmail.com");
            helper.setSubject("เกินกำหนดชำระเงิน กรุณาทำการชำระตามรายละเอียดที่แนบมา");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            log.info("Email send failure to :" + mailTo);
        }
    }
}
