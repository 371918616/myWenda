package com.nowcoder;

import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.Question;
import com.nowcoder.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")

public class InitDatabaseTests {

	@Autowired
	UserDAO userDAO;

	@Autowired
	QuestionDAO questionDAO;

	@Test
	public void initDatabase() {
		Random random = new Random();
		for(int i = 0 ; i < 11 ; ++ i){
			User user = new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png" , random.nextInt(1000)));
			user.setName(String.format("USER%d" , i+20));
			user.setPassword("");
			user.setSalt("");
			userDAO.addUser(user);

			Question question = new Question();
			question.setCommentCount(i);
			Date date = new Date();
			date.setTime(date.getTime() + 1000 * 3600 * i);
			question.setUserId(i+1);
			question.setCreatedDate(date);
			question.setTitle(String.format("TITLE%d" , i));
			question.setContent(String.format("aaaaaa+content%d" , i));

			questionDAO.addQuestion(question);
		}

		System.out.println(questionDAO.selectLatestQuestions(1 , 0 , 5));
	}

}
