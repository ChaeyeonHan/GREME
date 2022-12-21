package itstime.shootit.greme.challenge.dto;

import itstime.shootit.greme.challenge.domain.Challenge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetChallengeRes {
    private String title;
    private String info;
    private int cur_num;
    private Date deadline;

    public GetChallengeRes(Challenge entity){
        this.title = entity.getTitle();
        this.info = entity.getInfo();
        this.cur_num = entity.getCur_num();
        this.deadline = entity.getDeadline();
    }
}
