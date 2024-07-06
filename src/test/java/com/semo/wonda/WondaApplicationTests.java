package com.semo.wonda;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.SharedGoal;
import com.semo.wonda.entity.UserEntity;
import com.semo.wonda.repository.GoalRepository;
import com.semo.wonda.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class WondaApplicationTests {

	@Autowired
	private GoalRepository goalRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void testShareGoal() {
		// GoalEntity 및 UserEntity 초기화
		Optional<GoalEntity> optionalGoal = goalRepository.findById(1L);
		GoalEntity goal = optionalGoal.orElseThrow(() -> new RuntimeException("Goal not found"));

		UserEntity user1 = userRepository.findByUserName("test");
		UserEntity user2 = userRepository.findByUserName("test1");

		if (user1 == null || user2 == null) {
			throw new RuntimeException("User not found");
		}

		// SharedGoal 생성 및 설정
		SharedGoal sharedGoal1 = new SharedGoal(goal, user1);
		SharedGoal sharedGoal2 = new SharedGoal(goal, user2);

		goal.addSharedGoal(sharedGoal1);
		goal.addSharedGoal(sharedGoal2);

		// 저장
		goalRepository.save(goal);
	}


}
