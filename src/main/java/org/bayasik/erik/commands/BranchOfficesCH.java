package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.BranchOffice;

public class BranchOfficesCH implements CommandHandler {

    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.ADD_BRANCH_OFFICE)
    public void add(String name, String address, double budget) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = new BranchOffice();
        office.setName(name);
        office.setAddress(address);
        office.setBudget(budget);
        em.persist(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.ADD_BRANCH_OFFICE, office);
    }

    @Command(Commands.DELETE_BRANCH_OFFICE)
    public void delete(int id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BranchOffice.class, id);
        em.remove(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.DELETE_BRANCH_OFFICE, office);
    }

    @Command(Commands.UPDATE_BRANCH_OFFICE)
    public void update(int id, String name, String address, double budget) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BranchOffice.class, id);
        office.setName(name);
        office.setName(address);
        office.setBudget(budget);
        em.merge(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.UPDATE_BRANCH_OFFICE, office);
    }

    @Command(Commands.GET_ALL_OFFICES)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var offices = em.createQuery("SELECT o FROM BranchOffice o", BranchOffice.class).getResultList();

        responser.jsonResponse(Commands.GET_ALL_OFFICES, offices);
    }
}
