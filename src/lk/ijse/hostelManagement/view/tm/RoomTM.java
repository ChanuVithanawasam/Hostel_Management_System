package lk.ijse.hostelManagement.view.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTM {
    private String room_type_id;
    private String type;
    private String key_money;
    private int qty;
}
