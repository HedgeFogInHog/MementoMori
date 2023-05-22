package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.BranchOffice;
import org.bayasik.erik.models.Inventory;

public class InventoryCH implements CommandHandler {

    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.ADD_INVENTORY)
    public void add(String name, int amount, BranchOffice BranchOfficeId) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = new Inventory();
        office.setName(name);
        office.setAmount(amount);
        office.setBranchOfficeId(BranchOfficeId);
        em.persist(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.ADD_INVENTORY, office);
    }

    @Command(Commands.DELETE_INVENTORY)
    public void delete(int id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(Inventory.class, id);
        em.remove(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.DELETE_INVENTORY, office);
    }

    @Command(Commands.UPDATE_INVENTORY)
    public void update(int id, String name, int amount, BranchOffice BranchOfficeId) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(Inventory.class, id);
        office.setName(name);
        office.setAmount(amount);
        office.setBranchOfficeId(BranchOfficeId);
        em.merge(office);
        em.getTransaction().commit();

        responser.jsonResponse(Commands.UPDATE_INVENTORY, office);
    }

    @Command(Commands.GET_ALL_INVENTORY)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var offices = em.createQuery("SELECT o FROM Inventory o", Inventory.class).getResultList();

        responser.jsonResponse(Commands.GET_ALL_INVENTORY, offices);
    }
}
