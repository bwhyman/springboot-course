package com.example.springmvcexamples.example01;

import com.example.springmvcexamples.example01.dox.Address;
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
import java.util.Optional;

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
        return ResultVO.success(ADDRESSES);
    }

    @PostMapping("addresses")
    public ResultVO postAddress(@RequestBody Address address) {
        log.debug(address.getDetail());
        log.debug(address.getComment());
        return ResultVO.ok();
    }

    @PostMapping("addresses02")
    public ResultVO postAddress2(@RequestBody Address address) {
        log.debug(address.getDetail());
        log.debug(address.getComment());
        log.debug("{}", address.getUser().getId());
        return ResultVO.ok();
    }

    @GetMapping("addresses/{aid}")
    public ResultVO getAddress(@PathVariable String aid) {
        Address address = ADDRESSES.stream()
                .filter(a -> a.getId().equals(aid))
                .findFirst()
                .orElse(null);
        return ResultVO.success(address);
    }

    // 无参的默认请求为第一页
    // 因为路径与以上冲突，单独命名
    @GetMapping({"addresses-pages", "addresses-pages/{number}"})
    public ResultVO getAddressesPage(@PathVariable Optional<Integer> number) {
        // 如果不存在，默认为第一页
        int pateNumber = number.orElse(1);
        log.debug("page number: {}", pateNumber);
        // 可基于RequestPage封装分页信息
        return ResultVO.success(ADDRESSES);
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
                .id("1").detail("956").inertTime(LocalDateTime.now())
                .build();
        Address a2 = Address.builder()
                .id("2").detail("925").inertTime(LocalDateTime.now())
                .build();
        Address a3 = Address.builder()
                .id("3").detail("121").inertTime(LocalDateTime.now())
                .build();
        return List.of(a1, a2, a3);
    }
}
