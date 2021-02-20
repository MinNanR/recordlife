package site.minnan.recordlife.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 列表查询返回值基类
 * @param <T> 列表内容
 * @author Minnan on 2020/12/16
 */
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListQueryVO<T>{

    /**
     * 列表
     */
    List<T> list;

    /**
     * 总数量
     */
    Long totalCount;
}