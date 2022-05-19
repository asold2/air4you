package SEP4Data.air4you.Notification.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository <UserToken,Integer>{
    @Modifying
    @Transactional
    @Query(value = "update UserToken ut set ut.token = :token where ut.uid = :uid ")
    void updateUserToken(@Param(value = "token") String token, @Param(value = "uid") String uid);

    Optional<UserToken> findUserTokenByUid(String uid);
}

