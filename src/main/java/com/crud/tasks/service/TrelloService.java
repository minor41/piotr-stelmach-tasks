package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TrelloService {

    private static final String SUBJECT = "Task, New Trello card";
    private static final String SUBJECT2 = "Task, Daily Reminder";

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private TrelloClient trelloClient;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SimpleEmailService emailService;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCardDto createdTrelloCard(final TrelloCardDto trelloCardDto) {
        long size = taskRepository.count();
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card -> emailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT2,
                "New card: " + card.getName() + " has been created on you Trello account",
                //createMessage(size),
                ""
                )));
        return newCard;
    }

    private String createMessage(long databaseSize) {
        String message;
        if (databaseSize == 1L) {
            message = "You have: " + databaseSize + " task in your DataBase.";
        } else {
            message = "You have: " + databaseSize + " tasks in your DataBase.";
        }
        return message;
    }
}