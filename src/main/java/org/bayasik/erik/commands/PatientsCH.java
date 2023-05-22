package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.Patients;

public class PatientsCH implements CommandHandler {

    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.ADD_PATIENT)
    public void add(String name, String surname, String phoneNumber, String address, String email) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = new Patients();
        office.setName(name);
        office.setSurname(surname);
        office.setPhoneNumber(phoneNumber);
        office.setAddress(address);
        office.setEmail(email);
        em.persist(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.ADD_PATIENT, office);
    }

    @Command(Commands.DELETE_PATIENT)
    public void delete(int id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(Patients.class, id);
        em.remove(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.DELETE_PATIENT, office);
    }

    @Command(Commands.UPDATE_PATIENT)
    public void update(int id, String name, String surname, String phoneNumber, String address, String email) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(Patients.class, id);
        office.setName(name);
        office.setSurname(surname);
        office.setPhoneNumber(phoneNumber);
        office.setAddress(address);
        office.setEmail(email);
        em.merge(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.UPDATE_PATIENT, office);
    }

    @Command(Commands.GET_ALL_PATIENT)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var offices = em.createQuery("SELECT o FROM Patients o", Patients.class).getResultList();

        responser.jsonResponse(Commands.GET_ALL_PATIENT, offices);
    }
}