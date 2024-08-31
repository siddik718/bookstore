package co.library.users.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Update {
    private String email;
    private String password;
    private String name;
    private String address;
    private Long bookBought;
    private Long bookSold;
    private Integer isLoggedId;
    private Long loggingAttempt;
}
