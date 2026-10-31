package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    // В один день и в одно время может проходить несколько тренировок.
    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay =
                timetable.computeIfAbsent(trainingSession.getDayOfWeek(), day -> new TreeMap<>());

        List<TrainingSession> sessionsAtTime =
                trainingsForDay.computeIfAbsent(trainingSession.getTimeOfDay(), time -> new ArrayList<>());

        sessionsAtTime.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay = timetable.get(dayOfWeek);
        if (trainingsForDay == null) {
            return null;
        }
        return trainingsForDay.get(timeOfDay);
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        Map<Coach, Integer> coaches = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay : timetable.values()) {
            for (List<TrainingSession> sessionsAtTime : trainingsForDay.values()) {
                for (TrainingSession session : sessionsAtTime) {
                    coaches.merge(session.getCoach(), 1, Integer::sum);
                }
            }
        }

        List<Map.Entry<Coach, Integer>> result = new ArrayList<>(coaches.entrySet());
        result.sort(Map.Entry.<Coach, Integer>comparingByValue().reversed());
        return result;
    }
}
