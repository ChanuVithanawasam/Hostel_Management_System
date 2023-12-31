package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelManagement.bo.BOFactory;
import lk.ijse.hostelManagement.bo.custom.StudentBO;
import lk.ijse.hostelManagement.dto.StudentDTO;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;
import lk.ijse.hostelManagement.view.tm.StudentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class ManageStudentFormController implements Initializable {
    private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);

    public AnchorPane MainAnchorePane;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtContactNo;
    public JFXTextField txtAddress;
    public JFXButton btnSave;
    public TableView<StudentTM> tblStudent;
    public JFXButton btnDelete;
    public JFXButton btnAddNew;
    public JFXComboBox<String> cmbGender;
    public JFXDatePicker datePickerDOB;
    public JFXTextField txtSearch;

    //------Save-----//
    public void btnSave_OnAction(ActionEvent actionEvent) throws ParseException {
        String id = txtId.getText();
        String name = txtName.getText();
        String cNO = txtContactNo.getText();
        String address = txtAddress.getText();
        LocalDate dob = datePickerDOB.getValue();
        String gender = cmbGender.getValue();


        if (!id.matches("^(ST-[0-9]{3,4})$")) {
            NotificationController.Warring("Student Id", "Invalid Student Id.Check STU-000 type in your entered value.");
            txtId.requestFocus();
            return;
        } else if (!name.matches("^([A-Z a-z]{5,40})$")) {
            NotificationController.Warring("Student Name", "Student Name.");
            txtName.requestFocus();
            return;
        } else if (!cNO.matches("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$")) {
            NotificationController.Warring("Contact Number", "Invalid Student Contact Number.");
            txtContactNo.requestFocus();
            return;
        } else if (!address.matches("^([A-Za-z]{4,10})$")) {
            NotificationController.Warring("Address", "Invalid Student Address.");
            txtAddress.requestFocus();
            return;
        } else if (!gender.matches("^([A-Z a-z]{4,20})$")) {
            NotificationController.Warring("Gender", "Invalid Student Gender.");
            cmbGender.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Student*/
            try {
                if (exitStudent(id)) {
                    NotificationController.WarringError("Save Student Warning", id, "Already exists ");
                }
                studentBO.saveStudent(new StudentDTO(id, name, address, cNO, dob, gender));
                tblStudent.getItems().add(new StudentTM(id, name, address, cNO, dob, gender));
                NotificationController.SuccessfulTableNotification("Save", "Student");
            } catch (SQLException e) {
                NotificationController.WarringError("Save Student Warning", id + e.getMessage(), "Failed to save the Student ");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*Update Rooms*/
            try {
                if (!exitStudent(id)) {
                    NotificationController.WarringError("Update Student Warning", id, "There is no such Student associated with the ");
                }
                //Rooms update
                studentBO.updateStudent(new StudentDTO(id, name, address, cNO, dob, gender));
                NotificationController.SuccessfulTableNotification("Update", "Student");
            } catch (SQLException e) {
                NotificationController.WarringError("Update Student Warning", id + e.getMessage(), "Failed to update the Student ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            StudentTM selectedItem = tblStudent.getSelectionModel().getSelectedItem();
            selectedItem.setStudent_id(id);
            selectedItem.setName(name);
            selectedItem.setAddress(address);
            selectedItem.setContact_no(cNO);
            selectedItem.setDob(dob);
            selectedItem.setGender(gender);
            tblStudent.refresh();
        }
        btnAddNew.fire();
    }

    //------Delete-----//
    public void btnDelete_OnAction(ActionEvent actionEvent) {
        /*Delete Student*/
        String code = tblStudent.getSelectionModel().getSelectedItem().getStudent_id();
        try {
            if (!exitStudent(code)) {
                NotificationController.WarringError("Delete Student Warning", code, "There is no such Student associated with the ");
            }
            studentBO.deleteStudent(code);
            tblStudent.getItems().remove(tblStudent.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Student");
            tblStudent.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            NotificationController.WarringError("Delete Student Warning", code, "Failed to delete the Student ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //------ADD NEW-----//
    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtId.setDisable(false);
        txtName.setDisable(false);
        txtContactNo.setDisable(false);
        txtAddress.setDisable(false);
        datePickerDOB.setDisable(false);
        cmbGender.setDisable(false);

        txtId.clear();
        txtName.clear();
        txtContactNo.clear();
        txtAddress.clear();
        datePickerDOB.getEditor().clear();
        cmbGender.getSelectionModel().clearSelection();
        txtId.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblStudent.getSelectionModel().clearSelection();
    }

    //------Navigate To Home-----//
    public void navigateToHome(MouseEvent mouseEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(MainAnchorePane, "ReserveForm");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //------Combo Box-----//
        cmbGender.getItems().addAll("Male", "Female", "Other");

        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("student_id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("dob"));
        tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));

        initUI();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                //------------------------Text Filed Load----------------------//
                txtId.setText(newValue.getStudent_id());
                txtName.setText(newValue.getName());
                txtContactNo.setText(newValue.getContact_no());
                txtAddress.setText(newValue.getAddress());
                datePickerDOB.setValue(newValue.getDob());
                cmbGender.setValue(newValue.getGender() + "");

                txtId.setDisable(false);
                txtName.setDisable(false);
                txtContactNo.setDisable(false);
                txtAddress.setDisable(false);
                datePickerDOB.setDisable(false);
                cmbGender.setDisable(false);
            }
        });

        txtAddress.setOnAction(event -> btnSave.fire());
        loadAllStudent();
    }

    //------Load Student-----//
    private void loadAllStudent() {
        tblStudent.getItems().clear();
        /*Get all Student*/
        try {
            ArrayList<StudentDTO> allStudent = studentBO.getAllStudent();
            for (StudentDTO studentDTO : allStudent) {
                tblStudent.getItems().add(new StudentTM(studentDTO.getStudent_id(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact_no(), studentDTO.getDob(), studentDTO.getGender()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        txtId.clear();
        txtName.clear();
        txtContactNo.clear();
        txtAddress.clear();
        datePickerDOB.getEditor().clear();
        cmbGender.getSelectionModel().clearSelection();

        txtId.setDisable(true);
        txtName.setDisable(true);
        txtContactNo.setDisable(true);
        txtAddress.setDisable(true);
        datePickerDOB.setDisable(true);
        cmbGender.setDisable(true);

        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    //------Exit Student-----//
    private boolean exitStudent(String id) throws SQLException, ClassNotFoundException {
        return studentBO.existStudentID(id);
    }

    //------Search Student ID-----//
    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtSearch.getText().trim().isEmpty()) {
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllStudent();
        } else {
            if (exitStudent(txtSearch.getText())) {
                tblStudent.getItems().clear();
                ArrayList<StudentDTO> arrayList = studentBO.searchAllStudent(txtSearch.getText());
                if (arrayList != null) {
                    for (StudentDTO studentDTO : arrayList) {
                        tblStudent.getItems().add(new StudentTM(studentDTO.getStudent_id(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact_no(), studentDTO.getDob(), studentDTO.getGender()));
                    }
                }
            } else {
                tblStudent.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }
}
