package lk.ijse.hostelManagement.view.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LoginTM {
    @Id
    private String userID;
    @Column
    private String name;
    private String address;
    private String contact_no;
    private String Password;
    private String gender;

}
