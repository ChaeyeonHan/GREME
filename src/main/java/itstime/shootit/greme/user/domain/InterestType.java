package itstime.shootit.greme.user.domain;

public enum InterestType {
    에너지,
    업사이클링,
    친환경제품,
    채식,
    화장품;

    public static InterestType fromValue(Integer type){
        switch(type){
            case 0:
                return 에너지;
            case 1:
                return 업사이클링;
            case 2:
                return 친환경제품;
            case 3:
                return 채식;
            case 4:
                return 화장품;
        }
        return null;
    }
}
