import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
//
//public class Schedule {
//    private List<DayOfWeek> days;
//    private List<LocalTime> startTimes;
//    private List<LocalTime> endTimes;
//
//    public Schedule() {
//        this.days = new ArrayList<>();
//        this.startTimes = new ArrayList<>();
//        this.endTimes = new ArrayList<>();
//    }
//
//    public void addSlot(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
//        days.add(day);
//        startTimes.add(startTime);
//        endTimes.add(endTime);
//    }
//
//    public void addSlots(List<DayOfWeek> newDays, List<LocalTime> newStartTimes, List<LocalTime> newEndTimes) {
//        days.addAll(newDays);
//        startTimes.addAll(newStartTimes);
//        endTimes.addAll(newEndTimes);
//    }
//
//    public void removeSlot(int index) {
//        days.remove(index);
//        startTimes.remove(index);
//        endTimes.remove(index);
//    }
//
//    public List<DayOfWeek> getDays() {
//        return days;
//    }
//
//    public List<LocalTime> getStartTimes() {
//        return startTimes;
//    }
//
//    public List<LocalTime> getEndTimes() {
//        return endTimes;
//    }
//
//    public List<String> getAvailableSlots() {
//        List<String> availableSlots = new ArrayList<>();
//        for (int i = 0; i < days.size(); i++) {
//            availableSlots.add(days.get(i) + " " + startTimes.get(i) + "-" + endTimes.get(i));
//        }
//        return availableSlots;
//    }
//}
class Schedule {
    private Map<DayOfWeek, LocalTime[]> scheduleMap;

    public Schedule() {
        this.scheduleMap = new HashMap<>();
    }

    public void addSlot(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.scheduleMap.put(day, new LocalTime[]{startTime, endTime});
    }

    // Метод для проверки наличия расписания для определенного дня
    public boolean hasSlot(DayOfWeek day) {
        return this.scheduleMap.containsKey(day);
    }

    // Метод для получения расписания для определенного дня
    public LocalTime[] getSlot(DayOfWeek day) {
        return this.scheduleMap.get(day);
    }

    // Метод для получения доступных дней расписания
    public Set<DayOfWeek> getDays() {
        return this.scheduleMap.keySet();
    }

    // Метод для получения доступных слотов расписания для определенного дня
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