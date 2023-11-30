package com.example.springmvcexamples.example01;

import com.example.springmvcexamples.example01.dto.Address;
import com.example.springmvcexamples.vo.ResultVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/example01/")
public class ExampleController01 {
    @GetMapping("index")
    public ResultVO getIndex() {
        return ResultVO.success(Map.of("name", "SUN"));
    }

    @GetMapping("addresses")
    public ResultVO getAddresses() {
        return ResultVO.success(Map.of("addresses", ADDRESSES));
    }

    @PostMapping("addresses")
    public ResultVO postAddress(@RequestBody Address address) {
        log.debug(address.getDetail());
        log.debug(address.getComment());
        return ResultVO.success(Map.of());
    }

    @PostMapping("addresses02")
    public ResultVO postAddress2(@RequestBody Address address) {
        log.debug(address.getDetail());
        log.debug(address.getComment());
        log.debug("{}", address.getUser().getId());
        return ResultVO.success(Map.of());
    }

    @GetMapping("addresses/{aid}")
    public ResultVO getAddress(@PathVariable int aid) {
        Address address = ADDRESSES.stream()
                .filter(a -> a.getId() == aid)
                .findFirst()
                .orElse(null);
        if(address == null) {
            //return ResultVO.error(404, "地址不存在");
            // Map.of()禁止空对象
            return ResultVO.success(Map.of());
        }
        return ResultVO.success(Map.of("address", address));
    }

    @GetMapping("inject")
    public void inject(HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestHeader HttpHeaders headers) {
        log.debug(request.getRequestURI());
        log.debug(String.valueOf(headers));
    }

    private final List<Address> ADDRESSES = create();

    private List<Address> create() {
        Address a1 = Address.builder()
                .id(1).detail("956").inertTime(LocalDateTime.now())
                .build();
        Address a2 = Address.builder()
                .id(2).detail("925").inertTime(LocalDateTime.now())
                .build();
        Address a3 = Address.builder()
                .id(3).detail("121").inertTime(LocalDateTime.now())
                .build();
        return List.of(a1, a2, a3);
    }
}
