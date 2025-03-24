package br.com.batista.desafio01.repository;

import br.com.batista.desafio01.model.entities.UserType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserTypeRepository extends CrudRepository<UserType, Long>, JpaSpecificationExecutor<UserType> {


    @Query("SELECT u FROM UserType AS u WHERE u.type = :type")
    public List<UserType> findListByType(@Param("type") String type);

    @Query("SELECT u FROM UserType AS u WHERE u.type = :type")
    public UserType findByType(@Param("type") String type);

}