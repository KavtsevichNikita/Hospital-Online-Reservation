package Schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
    protected Map<DayOfWeek, LocalTime[]> scheduleMap;
    public Schedule() {
        this.scheduleMap = new HashMap<>();
    }

    public void addSlot(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.scheduleMap.put(day, new LocalTime[]{startTime, endTime});
    }
    public boolean hasSlot(DayOfWeek day) {
        return this.scheduleMap.containsKey(day);
    }
    public List<String> getAvailableSlots() {
        List<String> availableSlots = new ArrayList<>();
        for (Map.Entry<DayOfWeek, LocalTime[]> entry : scheduleMap.entrySet()) {
            DayOfWeek day = entry.getKey();
            LocalTime startTime = entry.getValue()[0];
            LocalTime endTime = entry.getValue()[1];
            availableSlots.add(day + " " + startTime + "-" + endTime);
        }
        return availableSlots;
    }
}

