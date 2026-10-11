package ru.yandex.practicum.gym;

import javax.swing.plaf.synth.SynthGraphicsUtils;
import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, TrainingSession> training = new TreeMap<>();

        if(timetable.containsKey(trainingSession.getDayOfWeek())) {
            training = timetable.get(trainingSession.getDayOfWeek());
        }

        training.put(trainingSession.getTimeOfDay(), trainingSession);

        timetable.put(trainingSession.getDayOfWeek(), training);


    }

    public TreeMap<TimeOfDay, TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)

        TreeMap<TimeOfDay, TrainingSession> map = new TreeMap<>();
        map = timetable.get(dayOfWeek);
        return map.get(timeOfDay);
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        Map<Coach, Integer> coaches = new HashMap<>();

        for(TreeMap<TimeOfDay, TrainingSession> e : timetable.values() ){
            int i = 0;
            for(TrainingSession t : e.values()){
                if (coaches.containsKey(t.getCoach())) {
                    i = coaches.get(t.getCoach());
                } else {
                    i = 0;
                }
                i++;
                coaches.put(t.getCoach(), i);
            }
        }
        List<Map.Entry<Coach, Integer>> list = new ArrayList<>(coaches.entrySet());
        list.sort(Map.Entry.<Coach, Integer>comparingByValue().reversed());

        return list;
    }
}
