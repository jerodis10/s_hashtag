package com.s_hashtag.scheduler.api;

import com.s_hashtag.common.response.CustomResponse;
import com.s_hashtag.common.domain.schedule.dto.external.ScheduleDto;
import com.s_hashtag.common.domain.schedule.repository.ScheduleRepository;
import com.s_hashtag.scheduler.service.KakaoScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Slf4j
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
        List<ScheduleDto> scheduleDtoList = scheduleRepository.findAll();
        model.addAttribute("scheduleList", scheduleDtoList);
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

    @ResponseBody
    @PostMapping("/change")
//    @ResponseStatus(HttpStatus.OK)
    public String changeSchedule(@RequestBody List<ScheduleDto> scheduleDtoList
    ) {
        for(ScheduleDto scheduleDto : scheduleDtoList) {
            kakaoScheduleService.changeSchedule(
                    scheduleDto.getScheduleDocumentId(),
                    scheduleDto.getCronPeriod(),
                    scheduleDto.getMinLatitude(),
                    scheduleDto.getMaxLatitude(),
                    scheduleDto.getMinLongitude(),
                    scheduleDto.getMaxLongitude()
            );
        }

//        for(String scheduleId : scheduleDto.getCheckedList()) {
//            kakaoScheduleService.changeSchedule(
//                    scheduleId,
//                    scheduleDto.getCronPeriod(),
//                    scheduleDto.getMinLatitude(),
//                    scheduleDto.getMaxLatitude(),
//                    scheduleDto.getMinLongitude(),
//                    scheduleDto.getMaxLongitude());
//        }

//        List<ScheduleDto> scheduleDtoList = scheduleRepository.findAll();
//        model.addAttribute("scheduleList", scheduleDtoList);
//        return "admin/scheduleEdit";

//        return "redirect:/";
//        return "redirect:/api/admin/schedule/list";
//        return "admin/scheduleEdit";
//        return "members/addMemberForm";
        return "12";
//        return "redirect:api/admin/schedule/list";
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
