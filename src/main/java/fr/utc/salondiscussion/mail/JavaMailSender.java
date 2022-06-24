package fr.utc.salondiscussion.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


public class JavaMailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/sendmail")
    @ResponseBody
    void sendMail(){


    }
}
