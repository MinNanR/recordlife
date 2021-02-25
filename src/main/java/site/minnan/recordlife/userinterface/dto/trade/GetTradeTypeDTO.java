package site.minnan.recordlife.userinterface.dto.trade;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GetTradeTypeDTO {

    @NotEmpty(message = "未指定流水方向")
    private String direction;
}
