package org.zstack.sdk.zwatch.alarm;

import org.zstack.sdk.zwatch.alarm.AlarmLabelInventory;

public class AddLabelToAlarmResult {
    public AlarmLabelInventory inventory;
    public void setInventory(AlarmLabelInventory inventory) {
        this.inventory = inventory;
    }
    public AlarmLabelInventory getInventory() {
        return this.inventory;
    }

}
