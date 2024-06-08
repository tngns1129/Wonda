package com.semo.wonda;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.repository.GoalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WondaApplicationTests {

	@Autowired
	private GoalRepository goalRepository;

	@Test
	void testJpa(){

	}


}
