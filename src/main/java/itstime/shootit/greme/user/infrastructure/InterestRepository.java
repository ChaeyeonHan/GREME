package itstime.shootit.greme.user.infrastructure;

import itstime.shootit.greme.user.domain.Interest;
import itstime.shootit.greme.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    void deleteInterestsByUser(User user);

    List<Interest> findAllByUser(User user);

}