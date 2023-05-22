package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.Patients;
import org.bayasik.erik.models.Personal;
import org.bayasik.erik.models.Schedule;

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
    public void add(Patients patientId, Personal personalId, Date date, double price) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = new Schedule();
        office.setPatientId(patientId);
        office.setPersonalId(personalId);
        office.setDate(date);
        office.setPrice(price);
        em.persist(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.ADD_SCHEDULE, office);
    }

    @Command(Commands.DELETE_SCHEDULE)
    public void delete(int id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(Schedule.class, id);
        em.remove(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.DELETE_SCHEDULE, office);
    }

    @Command(Commands.UPDATE_SCHEDULE)
    public void update(int id, Patients patientId, Personal personalId, Date date, double price) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(Schedule.class, id);
        office.setPatientId(patientId);
        office.setPersonalId(personalId);
        office.setDate(date);
        office.setPrice(price);
        em.merge(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.UPDATE_SCHEDULE, office);
    }

    @Command(Commands.GET_ALL_SCHEDULE)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var offices = em.createQuery("SELECT o FROM Schedule o", Schedule.class).getResultList();

        responser.jsonResponse(Commands.GET_ALL_SCHEDULE, offices);
    }
}