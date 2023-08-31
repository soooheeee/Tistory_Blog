package com.example.blogproject.account.controller;

import com.example.blogproject.account.dto.AccountReqDto;
import com.example.blogproject.account.dto.LoginReqDto;
import com.example.blogproject.account.entity.Account;
import com.example.blogproject.account.entity.Authority;
import com.example.blogproject.account.service.AccountService;
import com.example.blogproject.global.dto.GlobalResDto;
import com.example.blogproject.security.user.CurrentUser;
import com.example.blogproject.security.user.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<GlobalResDto> register(@RequestBody @Valid AccountReqDto accountReqDto) {
        GlobalResDto response = accountService.signup(accountReqDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResDto> login(@RequestBody @Valid LoginReqDto loginReqDto, HttpServletResponse response) {
        GlobalResDto loginResponse = accountService.login(loginReqDto, response);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public ResponseEntity<GlobalResDto> adminOnlyMethod() {
        // ROLE_ADMIN 권한이 있는 사용자만 실행 가능한 로직
        return new ResponseEntity<>(new GlobalResDto("success!!",200),HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public ResponseEntity<Account> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(userDetails.getAccount()); // 로그인되지 않은 경우에 대한 처리
    }

    @GetMapping("/getAuth")
    public List<String> getAuth(@CurrentUser UserDetailsImpl userDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        }

        return List.of("No authorities found");
    }
}
