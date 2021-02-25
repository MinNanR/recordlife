package site.minnan.recordlife.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.service.FeedbackService;
import site.minnan.recordlife.domain.aggregate.Feedback;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.mapper.FeedbackMapper;
import site.minnan.recordlife.userinterface.dto.AddFeedbackDTO;

import java.sql.Timestamp;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    /**
     * 添加反馈
     *
     * @param dto
     */
    @Override
    public void addFeedback(AddFeedbackDTO dto) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Feedback feedback = Feedback.builder()
                .userId(jwtUser.getId())
                .title(dto.getTitle())
                .reason(dto.getReason())
                .imgUrl(dto.getImgUrl())
                .score(dto.getScore())
                .opinion(dto.getOpinion())
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        feedbackMapper.insert(feedback);
    }
}
