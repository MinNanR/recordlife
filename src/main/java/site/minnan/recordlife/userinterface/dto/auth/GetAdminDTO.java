package site.minnan.recordlife.userinterface.dto.auth;

import lombok.Data;
import site.minnan.recordlife.userinterface.dto.ListQueryDTO;

@Data
public class GetAdminDTO extends ListQueryDTO {

    private String username;
}
