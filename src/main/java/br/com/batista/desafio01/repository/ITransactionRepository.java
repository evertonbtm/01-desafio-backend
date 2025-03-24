package br.com.batista.desafio01.repository;

import br.com.batista.desafio01.model.entities.Transaction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransactionRepository extends CrudRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {


    @Query("SELECT t FROM Transaction AS t INNER JOIN User u WHERE u.document = :user or u.email = :user")
    public List<Transaction> findListByUser(@Param("user") String user);

}