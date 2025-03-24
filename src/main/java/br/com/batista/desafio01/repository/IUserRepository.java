package br.com.batista.desafio01.repository;

import br.com.batista.desafio01.model.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User AS u WHERE u.document = :document")
    public List<User> findListByDocument(@Param("document") String document);

    @Query("SELECT u FROM User AS u WHERE u.email = :email")
    public List<User> findListByEmail(@Param("email") String email);

    @Query("SELECT u FROM User AS u WHERE u.document = :document or u.email = :email")
    public List<User> findListByDocumentOrEmail(@Param("document") String document, @Param("email") String email);

}