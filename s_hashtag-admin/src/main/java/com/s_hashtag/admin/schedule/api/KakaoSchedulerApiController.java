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

//    @GetMapping("/start")
//    @ResponseStatus(HttpStatus.OK)
//    public CustomResponse<Void> startScheduler(@RequestParam String scheduleName) {
//        kakaoScheduleService.startSchedule(scheduleName);
//        return CustomResponse.empty();
//    }

    @GetMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse<Void> startScheduler() {
        kakaoScheduleService.startSchedule("KakaoScheduler");
        return CustomResponse.empty();
    }

    @PostMapping("/stop")
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse<Void> stopScheduler(@RequestParam String scheduleName) {
        kakaoScheduleService.stopSchedule(scheduleName);
        return CustomResponse.empty();
    }

    @PutMapping("/period")
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse<Void> changeSchedulePeriod(@RequestParam String scheduleName, @RequestParam String expression) {
        kakaoScheduleService.changeSchedulePeriod(scheduleName, expression);
        return CustomResponse.empty();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse<Boolean> getActiveStatus(@RequestParam String scheduleName) {
        return CustomResponse.of(kakaoScheduleService.getKakaoScheduleActiveStatus(scheduleName));
    }

}
