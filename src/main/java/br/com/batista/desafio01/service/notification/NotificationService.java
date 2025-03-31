package br.com.batista.desafio01.service.notification;

import br.com.batista.desafio01.exception.FieldDuplicatedException;
import br.com.batista.desafio01.model.dto.NotifyDTO;
import br.com.batista.desafio01.model.entities.Notification;
import br.com.batista.desafio01.model.entities.Transaction;
import br.com.batista.desafio01.model.enums.ENotification;
import br.com.batista.desafio01.repository.INotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class NotificationService implements INotificationService {

    private final WebClient webClient;

    @Autowired
    private INotificationRepository notificationRepository;

    public NotificationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://util.devi.tools").build();
    }

    @Transactional
    public Notification save(Notification notification){
        return notificationRepository.save(notification);
    }

    private Mono<NotifyDTO> call(String param) {
        return webClient.post()
                .uri("/api/v1/notify", param)
                .retrieve()
                .bodyToMono(NotifyDTO.class);
    }

    public Notification findByTransaction(long idTransation) throws Exception {
        List<Notification> notificationList = notificationRepository.findListByTransationId(idTransation);

        if(notificationList == null || notificationList.isEmpty()){
            return null;
        }

        if(notificationList.size() > 1){
            throw new FieldDuplicatedException(Notification.class, "transaction", "");
        }

        return notificationList.get(0);
    }

    @Override
    public void notify(Transaction transaction) {
        call("something")
                .doOnError(error -> {
                    try {
                        notifyQueue(transaction);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe(response -> System.out.println("Success notification sended"));
    }

    private void notifyQueue(Transaction transaction) throws Exception {

        Notification notification = findByTransaction(transaction.getIdTransaction());

        if(notification == null){
            notification = new Notification();
            notification.setUserEmail(transaction.getPayee().getEmail());
            notification.setUserPhone(transaction.getPayee().getPhone());
            notification.setSent(false);
        }

        notification.setTitle(ENotification.TITLE.get());
        notification.setMessage(ENotification.MESSAGE.get()
                    .replace("{0}",String.valueOf(transaction.getValue()))
                    .replace("{1}", transaction.getPayer().getDocument()));

        notification.setTransaction(transaction);

        save(notification);
    }

    @Scheduled(fixedRate = 320000)
    public void notificationSchedule() throws Exception {
        List<Notification> notificationList = notificationRepository.findListBySent(false);

        if(notificationList == null || notificationList.isEmpty()){
            return;
        }

        for(Notification notification : notificationList){
            //  TODO
        }
    }
}
