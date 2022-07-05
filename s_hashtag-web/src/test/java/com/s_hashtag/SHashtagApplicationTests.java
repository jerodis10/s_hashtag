//package com.s_hashtag;
//
//import com.s_hashtag.domain.member.Member;
//import com.s_hashtag.repository.MemberRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
////@ExtendWith(SpringExtension.class)
////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
////@WebAppConfiguration
////@AutoConfigureMockMvc
//@SpringBootTest
//class SHashtagApplicationTests {
//
//	private MockMvc mockMvc;
//	private MemberRepository memberRepository;
//	private PasswordEncoder passwordEncoder;
//
//	SHashtagApplicationTests(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
//		this.memberRepository = memberRepository;
//		this.passwordEncoder = passwordEncoder;
//	}
//
//	@BeforeEach
//	public void setUp(WebApplicationContext webApplicationContext) {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//				.build();
//	}
//
//	@Test
////	@WithMockUser(roles = "ROLE_MEMBER")
//	@WithMockUser(username = "c")
//	void SessionTest() throws Exception {
////		mockMvc.perform(post("/login"))
////		mockMvc.perform(get("/login"))
//		mockMvc.perform(get("/"))
//				.andDo(print());
//	}
//
//	@Test
//	@DisplayName("패스워드 암호화 테스트")
//	void passwordEncode() {
//		// given
//		String id = "123";
//		String rawPassword = "12345678";
//		String name = "test";
//
////		Member member = new Member();
////		member.setLoginId(id);
////		member.setName("test");
////		member.setPassword(rawPassword);
//
//		// when
//		memberRepository.save(Member.builder()
//				.loginId(id)
//				.password(rawPassword)
//				.name(name)
//				.role("ROLE_MEMBER")
//				.build());
//
//		Optional<Member> member = memberRepository.findById(id);
//		String encodedPassword = member.get().getPassword();
//
//		// then
//		assertAll(
//				() -> assertNotEquals(rawPassword, encodedPassword),
//				() -> assertTrue(passwordEncoder.matches(rawPassword, encodedPassword))
//		);
//	}
//
//}
