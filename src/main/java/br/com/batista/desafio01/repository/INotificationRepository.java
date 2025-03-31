package br.com.batista.desafio01.repository;

import br.com.batista.desafio01.model.entities.Notification;
import br.com.batista.desafio01.model.entities.Transaction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificationRepository extends CrudRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    @Query("SELECT n FROM Notification AS n INNER JOIN Transation t WHERE t.idTransation = :idTransation")
    public List<Notification> findListByTransationId(@Param("idTransation") long idTransation);

    @Query("SELECT n FROM Notification AS n WHERE n.isSent = :isSent")
    public List<Notification> findListBySent(@Param("isSent") boolean isSent);
}
