package co.com.reportes.model.reportelistener.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserModel {

    Long id;
    String firstName;
    String middleName;
    String lastName;
    String secondLastName;
    String address;
    String phoneNumber;
    String email;
}
