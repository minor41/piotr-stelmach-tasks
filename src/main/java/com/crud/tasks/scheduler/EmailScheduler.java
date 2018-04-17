package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private static final String SUBJECT = "Tasks: Once a day email";

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    //@Scheduled(cron = "0 0 10 * * *")
    @Scheduled(fixedDelay = 10000)
    public void sendInformationEmail() {

        long size = taskRepository.count();
        simpleEmailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                createMessage(size),
                ""
        ));
    }

    private String createMessage(long datebaseSize) {
        String message;
        if (datebaseSize == 0L){
            message = "Currently in database you got: " + datebaseSize + " tasks" + "\n" +
                    "Have a nice day!";
        } if(datebaseSize == 1L){
            message = "Currently in database you got: " + datebaseSize + " task" + "\n" +
                    "Have a nice day!";
        }else {
            message = "Currently in database you got: " + datebaseSize  + " tasks" + "\n" +
            "Have a nice day!";
        }
        return message;
    }
}
