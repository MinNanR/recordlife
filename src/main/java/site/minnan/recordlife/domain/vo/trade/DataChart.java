package site.minnan.recordlife.domain.vo.trade;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 图表对象
 * @author Minnan on 2021/2/22
 */
@Data
public class DataChart {

    private List<Integer> lastSixMonths;

    private List<BigDecimal>  balanceVolume;

    private List<BigDecimal> incomeVolume;

    private List<BigDecimal> expendVolume;

    public DataChart(Integer length){
        lastSixMonths = new ArrayList<>(length);
        balanceVolume = new ArrayList<>(length);
        incomeVolume = new ArrayList<>(length);
        expendVolume = new ArrayList<>(length);
    }

    public void add(BigDecimal income, BigDecimal expand, Integer month){
        this.lastSixMonths.add(month);
        this.incomeVolume.add(income);
        this.expendVolume.add(expand);
        this.balanceVolume.add(income.subtract(expand));
    }
}
