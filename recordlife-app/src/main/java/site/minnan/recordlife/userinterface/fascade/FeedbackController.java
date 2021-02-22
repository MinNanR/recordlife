package site.minnan.recordlife.userinterface.fascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.application.service.FeedbackService;
import site.minnan.recordlife.userinterface.dto.AddFeedBackDTO;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("/recordApplets/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addFeedback")
    public ResponseEntity<?> addFeedBack(@RequestBody @Valid AddFeedBackDTO dto) {
        feedbackService.addFeedback(dto);
        return ResponseEntity.success();
    }
}
