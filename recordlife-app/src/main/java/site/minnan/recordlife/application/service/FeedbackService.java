package site.minnan.recordlife.application.service;

import site.minnan.recordlife.userinterface.dto.AddFeedBackDTO;

/**
 * 反馈相关操作
 *
 * @author Minnan on 2021/2/22
 */
public interface FeedbackService {

    /**
     * 添加反馈
     *
     * @param dto
     */
    void addFeedback(AddFeedBackDTO dto);
}
