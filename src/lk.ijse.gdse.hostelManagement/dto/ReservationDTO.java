package lk.hostelManagement.pos.dto;

import lk.hostelManagement.pos.entity.Room;
import lk.hostelManagement.pos.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private String res_id;
    private LocalDate date;
    private String student_id;
    private String room_type_id;
    private String key_money;
    private Double advance;
    private String status;

}
