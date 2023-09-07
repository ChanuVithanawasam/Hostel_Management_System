package lk.ijse.hostelManagement.bo.custom;

import lk.ijse.hostelManagement.dto.StudentDTO;
import lk.ijse.hostelManagement.bo.SuperBO;

import java.sql.SQLException;
import java.util.ArrayList;
public interface StudentBO extends SuperBO {

    ArrayList<StudentDTO> getAllStudent() throws SQLException, ClassNotFoundException;

    ArrayList<StudentDTO> searchAllStudent(String id) throws SQLException, ClassNotFoundException;

    boolean saveStudent(StudentDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateStudent(StudentDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteStudent(String id) throws SQLException, ClassNotFoundException;

    boolean existStudentID(String id) throws SQLException, ClassNotFoundException;
}
