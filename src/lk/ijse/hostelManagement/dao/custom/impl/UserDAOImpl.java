package lk.ijse.hostelManagement.dao.custom.impl;

import lk.ijse.hostelManagement.dao.custom.UserDAO;
import lk.ijse.hostelManagement.entity.Login;
import lk.ijse.hostelManagement.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
public class UserDAOImpl implements UserDAO {

    @Override
    public ArrayList<Login> getAllUsers() throws SQLException, ClassNotFoundException {
/**
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Login");
        ResultSet rst = stm.executeQuery();
        ArrayList<Login> users = new ArrayList<>();
        while (rst.next()) {
            users.add(new Login(
                    rst.getString(1),
                    rst.getString(2)
            ));
        }
        return users;
*/

        ArrayList<Login> login;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Login");
        login = (ArrayList<Login>) query.list();
        transaction.commit();
        session.close();
        return login;
    }

    @Override
    public ArrayList<Login> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Login> allUsers;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Login ");
        allUsers = (ArrayList<Login>) query.list();
        transaction.commit();
        session.close();
        return allUsers;
    }

    @Override
    public boolean save(Login login) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(login);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Login login) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(login);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Login login = session.get(Login.class, id);
        session.delete(login);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Login search(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Login login = session.get(Login.class, id);
        transaction.commit();
        session.close();
        return login;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("SELECT userID FROM Login WHERE userID=:id");
        String id1 = (String) query.setParameter("id", id).uniqueResult();
        if (id1 != null) {
            return true;
        }
        transaction.commit();
        session.close();
        return false;
    }
}
