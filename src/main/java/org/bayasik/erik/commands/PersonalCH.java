package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.BranchOffice;
import org.bayasik.erik.models.Personal;

public class PersonalCH implements CommandHandler {

    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.ADD_PERSONAL)
    public void add(String name, String surname, String phoneNumber, String address, String email,
                    BranchOffice branchOfficeId, String post, int salaryType, int salaryPercent, double salaryAmount) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = new Personal();

        office.setName(name);
        office.setSurname(surname);
        office.setPhoneNumber(phoneNumber);
        office.setAddress(address);
        office.setEmail(email);
        office.setBranchOfficeId(branchOfficeId);
        office.setPost(post);
        office.setSalaryType(salaryType);
        office.setSalaryPercent(salaryPercent);
        office.setSalaryAmount(salaryAmount);

        em.persist(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.ADD_PERSONAL, office);
    }

    @Command(Commands.DELETE_PERSONAL)
    public void delete(int id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(Personal.class, id);
        em.remove(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.DELETE_PERSONAL, office);
    }

    @Command(Commands.UPDATE_PERSONAL)
    public void update(int id, String name, String surname, String phoneNumber, String address, String email,
                       BranchOffice branchOfficeId, String post, int salaryType, int salaryPercent, double salaryAmount) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(Personal.class, id);

        office.setName(name);
        office.setSurname(surname);
        office.setPhoneNumber(phoneNumber);
        office.setAddress(address);
        office.setEmail(email);
        office.setBranchOfficeId(branchOfficeId);
        office.setPost(post);
        office.setSalaryType(salaryType);
        office.setSalaryPercent(salaryPercent);
        office.setSalaryAmount(salaryAmount);

        em.merge(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.UPDATE_PERSONAL, office);
    }

    @Command(Commands.GET_ALL_PERSONAL)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var offices = em.createQuery("SELECT o FROM BranchOffice o", Personal.class).getResultList();

        responser.jsonResponse(Commands.GET_ALL_PERSONAL, offices);
    }
}