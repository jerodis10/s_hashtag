package com.s_hashtag.admin.schedule.api;

import com.s_hashtag.admin.schedule.service.KakaoScheduleService;
import com.s_hashtag.common.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scheduler")
public class KakaoSchedulerApiController {

    private final KakaoScheduleService kakaoScheduleService;

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse<Void> startScheduler() {
        kakaoScheduleService.startSchedule();
        return CustomResponse.empty();
    }

    @PostMapping("/stop")
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse<Void> stopScheduler() {
        kakaoScheduleService.stopSchedule();
        return CustomResponse.empty();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse<Boolean> getActiveStatus() {
        return CustomResponse.of(kakaoScheduleService.getKakaoScheduleActiveStatus());
    }

    @PutMapping("/period")
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse<Void> changeSchedulePeriod(@RequestBody String scheduleName, @RequestBody String expression) {
        kakaoScheduleService.changeSchedulePeriod(scheduleName, expression);
        return CustomResponse.empty();
    }

}
