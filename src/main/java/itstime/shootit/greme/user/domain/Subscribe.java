package itstime.shootit.greme.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(
        uniqueConstraints ={
            @UniqueConstraint(
                    name = "subscribe",
                    columnNames = {"fromUserId", "toUserId"}
            )
        }

)
public class Subscribe{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fromUserId")
    private User fromUser;  // 구독하는 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toUserId")
    private User toUser;  // 구독받는 유저

    @CreatedDate
    private LocalDateTime createdDate;
}
