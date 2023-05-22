package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.BudgetHistory;

public class BudgetHistoryCH implements CommandHandler {

    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.ADD_BUDGET_HISTORY)
    public void add(double action, String description, BranchOffice id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = new BudgetHistory();
        office.setAction(action);
        office.setDescription(description);
        office.setBranchOfficeId(id);
        em.persist(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.GET_ALL_OFFICES, office);
    }

    @Command(Commands.PRINT_ALL_BUDGET_HISTORY)
    public void print() {
        var em = DependencyLoader.getEntityManager();
        var offices = em.createQuery("SELECT o FROM BudgetHistory o", BudgetHistory.class).getResultList();
        for(var office : offices)
        {
            System.out.println(office., office.getName());
        }
    }

    @Command(Commands.DELETE_BUDGET_HISTORY)
    public void delete(int id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BranchOffice.class, id);
        em.remove(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.GET_ALL_OFFICES, office);
    }

    @Command(Commands.UPDATE_BUDGET_HISTORY)
    public  void update(int id, double action, String description, BranchOffice BranchOfficeId) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BudgetHistory.class, id);
        office.setAction(action);
        office.setDescription(description);
        office.setBranchOfficeId(BranchOfficeId);
        em.merge(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.GET_ALL_OFFICES, office);
    }

    @Command(Commands.GET_ALL_BUDGET_HISTORY)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var offices = em.createQuery("SELECT o FROM BudgetHistory o", BudgetHistory.class).getResultList();

        responser.jsonResponse(Commands.GET_ALL_BUDGET_HISTORY, offices);
    }
}
