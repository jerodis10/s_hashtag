package com.s_hashtag.scheduler.api;

import com.s_hashtag.common.response.CustomResponse;
import com.s_hashtag.common.schedule.model.vo.Schedule;
import com.s_hashtag.common.schedule.repository.ScheduleRepository;
import com.s_hashtag.scheduler.service.KakaoScheduleService;
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
public class SchedulerApiController {

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

    @PostMapping("/change")
//    @ResponseStatus(HttpStatus.OK)
    public String changeSchedule(@RequestParam("checkedList[]") List<String> checkedList,
                                 @RequestParam String expression,
                                 @RequestParam String min_latitude,
                                 @RequestParam String max_latitude,
                                 @RequestParam String min_longitude,
                                 @RequestParam String max_longitude
    ) {
        for(String scheduleId : checkedList) {
            kakaoScheduleService.changeSchedule(scheduleId, expression, min_latitude, max_latitude, min_longitude, max_longitude);
        }

//        return "redirect:/";
        return "redirect:/api/admin/schedule/list";
    }

//    @PostMapping("/period")
////    @ResponseStatus(HttpStatus.OK)
//    public String changeSchedulePeriod(@RequestParam("checkedList[]") List<String> checkedList,
//                                                     @RequestParam String expression) {
//        for(String scheduleId : checkedList) {
//            kakaoScheduleService.changeSchedulePeriod(scheduleId, expression);
//        }
//
////        return "redirect:/";
//        return "redirect:/api/admin/schedule/list";
//    }

//    @ResponseBody
//    @PostMapping("/period")
//    @ResponseStatus(HttpStatus.OK)
//    public CustomResponse<Void> changeSchedulePeriod(@RequestParam("checkedList[]") List<String> checkedList,
//                                                     @RequestParam String expression) {
//        for(String scheduleId : checkedList) {
//            kakaoScheduleService.changeSchedulePeriod(scheduleId, expression);
//        }
//        return CustomResponse.empty();
//    }



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
