package com.std.sbb;

import com.std.sbb.answer.Answer;
import com.std.sbb.answer.AnswerRepository;
import com.std.sbb.question.Question;
import com.std.sbb.question.QuestionRepository;
import com.std.sbb.question.QuestionService;
import com.std.sbb.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private UserService userService;

	@Test
	@DisplayName("단건 조회")
	void testJpa() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if(oq.isPresent()) {
			Question q  = oq.get();
		}

	}

	@Test
	@DisplayName("데이터 수정하기")
	void test007 () {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정 제목");
		this.questionRepository.save(q);
	}

	@Test
	@DisplayName("데이터 삭제하기")
	void test008 () {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1,  this.questionRepository.count());
	}

	@Test
	void test009() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	@Test
	void test010() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}

	@Test
	@Transactional
	@DisplayName("질문 기준 답변가져오기")
	void test011() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}

	@Test
	@DisplayName("데이터 밀어넣기")
	void testInsertJpa() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content);
		}
	}

	@Test
	@DisplayName("스트림 버전 데이터 밀어넣기")
	void t012() {
		IntStream.rangeClosed(3, 300)
				.forEach(no -> questionService.create("테스트 제목입니다. %d".formatted(no), "테스트내용입니다. %d".formatted(no)));
	}

//	@BeforeEach
	@Test
	@DisplayName("회원데이터 넣기")
	void beforeEachUserData() {
		userService.create("user1", "user1@test.com", "1234");
		userService.create("user2", "user2@test.com", "1234");
	}

}
