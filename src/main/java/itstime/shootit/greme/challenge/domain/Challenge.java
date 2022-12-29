package itstime.shootit.greme.challenge.domain;

import itstime.shootit.greme.user.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Challenge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String info;

    private int cur_num;

    private Date deadline;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<ChallengeUser> challengeUsers;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<ChallengePost> challengePosts;

//    @Transient
//    private int res;
//
//    public int calRes(Date date) throws ParseException {
//        String todayFm = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));  // 현재 날짜
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date Dday = new Date(String.valueOf(dateFormat.parse(String.valueOf(getDeadline().getTime()))));
//        Date today = new Date(String.valueOf(dateFormat.parse(todayFm)));
//
//        long calculate = Dday.getTime() - today.getTime();
//        int Days = (int) (calculate / (24*60*60*1000));
//        System.out.println(Days);
//        return Days;
//    }

}
