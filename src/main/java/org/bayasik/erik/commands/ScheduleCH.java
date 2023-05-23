package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.Patient;
import org.bayasik.erik.models.Personal;
import org.bayasik.erik.models.Schedule;
import org.bayasik.erik.viewmodels.ScheduleVM;

import java.util.Date;

public class ScheduleCH implements CommandHandler {

    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.ADD_SCHEDULE)
    public void add(int patientId, int personId, Date date, double price) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var patient = em.find(Patient.class, patientId);
        var person = em.find(Personal.class, personId);
        var schedule = new Schedule();
        schedule.setPatientId(patient);
        schedule.setPersonalId(person);
        schedule.setDate(date);
        schedule.setPrice(price);
        System.out.println("AddScheduleSucc");
        em.persist(schedule);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.ADD_SCHEDULE, schedule);
    }

    @Command(Commands.DELETE_SCHEDULE)
    public void delete(int id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var schedule = em.find(Schedule.class, id);
        em.remove(schedule);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.DELETE_SCHEDULE, schedule);
    }

    @Command(Commands.UPDATE_SCHEDULE)
    public void update(int id, int patientId, int personId, Date date, double price) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var patient = em.find(Patient.class, patientId);
        var person = em.find(Personal.class, personId);
        var schedule = em.find(Schedule.class, id);
        schedule.setPatientId(patient);
        schedule.setPersonalId(person);
        schedule.setDate(date);
        schedule.setPrice(price);
        em.merge(schedule);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.UPDATE_SCHEDULE, schedule);
    }

    @Command(Commands.GET_ALL_SCHEDULE)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var schedules = em.createQuery("SELECT o FROM Schedule o", Schedule.class).getResultList();

        responser.notifyResponse(Commands.GET_ALL_SCHEDULE, ScheduleVM.fromCollection(schedules));
    }

    @Command(Commands.GET_SCHEDULE_BY_ID)
    public void getScheduleById(int id) {
        var em = DependencyLoader.getEntityManager();
        var schedules = em.createQuery("SELECT o FROM Schedule o WHERE o.id = :scheduleId", Schedule.class).setParameter("scheduleId", id).getResultList();
        System.out.println("GetScheduleByIdSucc");
        System.out.println(schedules);
        responser.jsonResponse(Commands.GET_SCHEDULE_BY_ID, schedules);
    }

    @Command(Commands.GET_SCHEDULE_BY_BRANCH_OFFICE)
    public void getScheduleByBranchOffice(int branchOfficeId) {
        var em = DependencyLoader.getEntityManager();
        var schedules = em.createQuery("SELECT o FROM Schedule o WHERE o.branchOfficeId = :branchOfficeId", Schedule.class).setParameter("branchOfficeId", branchOfficeId).getResultList();
        System.out.println("GetScheduleByIdSucc");
        System.out.println(schedules);
        responser.jsonResponse(Commands.GET_SCHEDULE_BY_BRANCH_OFFICE, schedules);
    }
}