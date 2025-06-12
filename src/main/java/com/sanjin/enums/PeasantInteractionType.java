package com.sanjin.enums;

public enum PeasantInteractionType {

    GIFT_GIVING(5, 5, "赠送礼物"),
    TRADING(3, 2,"交易"),
    QUEST_COMPLETION(10, 8, "完成任务"),
    HOSTILE_ACTION(-10, -15, "恶意行为");

    private final int baseFavorability;
    private final int baseYield;
    private final String displayName;

    PeasantInteractionType(int baseFavorability,int baseYield, String displayName) {
        this.baseFavorability = baseFavorability;
        this.baseYield = baseYield;
        this.displayName = displayName;
    }

    public int getBaseFavorability(){
        return baseFavorability;
    }
    public int  getBaseYield(){
        return baseYield;
    }
    public String getDisplayName(){
        return displayName;
    }
}
