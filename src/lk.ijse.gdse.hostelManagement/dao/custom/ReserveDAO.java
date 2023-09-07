package lk.hostelManagement.pos.dao.custom;

import lk.hostelManagement.pos.dao.CrudDAO;
import lk.hostelManagement.pos.entity.Reservation;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReserveDAO extends CrudDAO<Reservation, String> {
    String generateNewId() throws SQLException, ClassNotFoundException;

    boolean existStudent(String id) throws SQLException, ClassNotFoundException;
}
