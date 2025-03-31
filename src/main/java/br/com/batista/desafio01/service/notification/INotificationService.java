package br.com.batista.desafio01.service.notification;

import br.com.batista.desafio01.model.entities.Transaction;

public interface INotificationService {
    void notify(Transaction transaction);
}
