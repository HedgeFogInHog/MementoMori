package org.bayasik.erik.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bayasik.erik.models.Schedule;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleVM {
    private int id;
    private int patientId;
    private int budgetHistory;
    private int personalId;
    private String date;
    private double price;

    public ScheduleVM(Schedule schedule) {
        this(schedule.getId(),
                schedule.getPatientId().getId(),
                schedule.getBudgetHistoryId().getId(),
                schedule.getPersonalId().getId(),
                schedule.getDate().toString(),
                schedule.getPrice());
    }

    public static ArrayList<ScheduleVM> fromCollection(Collection<Schedule> schedules){
        var list = new ArrayList<ScheduleVM>();
        for(var schedule : schedules) list.add(new ScheduleVM(schedule));

        return list;
    }
}
