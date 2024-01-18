package com.adultery_project.repository;

import com.adultery_project.models.Cskh;
import com.adultery_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CskhRepository extends JpaRepository<Cskh,Integer> {
    @Query("SELECT c1 FROM Cskh c1 " +
            "WHERE (c1.fromUser.id = :userId OR c1.toUser.id = :userId) " +
            "AND c1.createAt = (" +
            "    SELECT MAX(c2.createAt) FROM Cskh c2 " +
            "    WHERE (c2.fromUser = c1.fromUser AND c2.toUser = c1.toUser) " +
            "    OR (c2.fromUser = c1.toUser AND c2.toUser = c1.fromUser) " +
            "    AND (c2.fromUser.id = :userId OR c2.toUser.id = :userId)" +
            ")")
    List<Cskh> initialStateAllChatFriends(@Param("userId") Long loggedInUserId);
    @Query(value = "SELECT c FROM Cskh AS c " +
            "WHERE ((c.fromUser.id = :firstUserId AND c.toUser.id = :secondUserId) " +
            "OR  (c.fromUser.id = :secondUserId AND c.toUser.id = :firstUserId)) " +
            "ORDER BY c.createAt")
    List<Cskh> findAllMessagesBetweenTwoUsers(@Param("firstUserId") Long firstUserId, @Param("secondUserId") Long secondUserId);
    List<Cskh> findByFromUserOrToUser(User fromUser ,User toUser);
}
