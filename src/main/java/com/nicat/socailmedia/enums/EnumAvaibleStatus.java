package com.nicat.socailmedia.enums;
import java.util.HashMap;
import java.util.Map;

public enum EnumAvaibleStatus {
    ACTIVE(1),DEACTIVE(0);
    private int value;

    private static final Map<Integer,EnumAvaibleStatus> VALUES = new HashMap<Integer,EnumAvaibleStatus>();

    static {
        for (EnumAvaibleStatus type : EnumAvaibleStatus.values()){
            VALUES.put(type.value,type);
        }
    }

    private EnumAvaibleStatus(int enumValue){
        this.value=enumValue;
    }

    public Integer getValue(){
        return value;
    }

    public static EnumAvaibleStatus getEnum(Integer value){
        if (value==null)
            return null;

        return VALUES.get(value.intValue());
    }
}
