package site.minnan.recordlife.userinterface.dto;

import lombok.Data;

/**
 * 获取流水记录参数
 * @author Minnan on 2021/2/22
 */
@Data
public class GetTradeRecordDTO {

    /**
     * 开始时间（年，月）
     */
    private Integer[] initialTime;

    /**
     * 结束时间（年，月）
     */
    private Integer[] byTime;
}
