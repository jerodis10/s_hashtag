package com.s_hashtag.api;

import com.s_hashtag.admin.schedule.model.Schedule;
import com.s_hashtag.admin.schedule.repository.ScheduleRepository;
import com.s_hashtag.admin.schedule.service.KakaoScheduleService;
import com.s_hashtag.common.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin/schedule")
public class KakaoSchedulerApiController {

    private final KakaoScheduleService kakaoScheduleService;
    private final ScheduleRepository scheduleRepository;

    @GetMapping("/history")
    public String scheduleHistory() {
        return "admin/scheduleHistory";
    }

    @GetMapping("/list")
    public String scheduleEditList(Model model) {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        model.addAttribute("scheduleList", scheduleList);
        return "admin/scheduleEdit";
    }

    @ResponseBody
    @PostMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse<Void> startScheduler(@RequestParam("checkedList[]") List<String> checkedList) {
        for(String scheduleId : checkedList) {
            kakaoScheduleService.startSchedule(scheduleId);
        }
        return CustomResponse.empty();
    }

    @ResponseBody
    @PostMapping("/stop")
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse<Void> stopScheduler(@RequestParam("checkedList[]") List<String> checkedList) {
        for(String scheduleId : checkedList) {
            kakaoScheduleService.stopSchedule(scheduleId);
        }
        return CustomResponse.empty();
    }

//    @GetMapping("/start")
//    @ResponseStatus(HttpStatus.OK)
//    public CustomResponse<Void> startScheduler() {
//        kakaoScheduleService.startSchedule("KakaoScheduler");
//        return CustomResponse.empty();
//    }

//    @PostMapping("/stop")
//    @ResponseStatus(HttpStatus.OK)
//    public CustomResponse<Void> stopScheduler(@RequestParam String scheduleName) {
//        kakaoScheduleService.stopSchedule(scheduleName);
//        return CustomResponse.empty();
//    }
//
//    @PutMapping("/period")
//    @ResponseStatus(HttpStatus.OK)
//    public CustomResponse<Void> changeSchedulePeriod(@RequestParam String scheduleName, @RequestParam String expression) {
//        kakaoScheduleService.changeSchedulePeriod(scheduleName, expression);
//        return CustomResponse.empty();
//    }
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public CustomResponse<Boolean> getActiveStatus(@RequestParam String scheduleName) {
//        return CustomResponse.of(kakaoScheduleService.getKakaoScheduleActiveStatus(scheduleName));
//    }

}
