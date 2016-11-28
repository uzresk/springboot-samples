package jp.gr.java_conf.uzresk.springboot.demo.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MemberDaoTest {

	@Autowired
	MemberDao memberDao;

	@Test
	@Transactional()
	@Rollback(false)
	public void insert() {

		Member member = new Member();
		member.setUserId("test_userid");
		member.setPassword("passwd");
		member.setName("testtest_name");
		member.setAuthority("1");

		// Insert Test
		int insertdRecordSize = memberDao.insert(member);

		assertThat(insertdRecordSize, is(1));

		// Insertした結果を取得する
		Optional<Member> insertdMember = memberDao.selectByUserId("test_userid");

		if (insertdMember.isPresent()) {
			assertThat(insertdMember.get(), is(member));
		} else {
			assertFalse(true);
		}
	}

	@Test
	@Transactional
	public void findAll() {
		Member member1 = new Member();
		member1.setUserId("test1");
		member1.setPassword("passwd1");
		member1.setName("testtest1");
		member1.setAuthority("general1");
		memberDao.insert(member1);

		Member member2 = new Member();
		member2.setUserId("test2");
		member2.setPassword("passwd2");
		member2.setName("testtest2");
		member2.setAuthority("general2");
		memberDao.insert(member2);

		List<Member> members = new LinkedList<>();
		members.add(member1);
		members.add(member2);

		List<Member> findAllMember = memberDao.selectAll();
		findAllMember.stream().forEachOrdered(s -> System.out.println(s));
		assertThat(findAllMember, is(members));

	}

	@Test
	@Transactional
	public void update() {

		Member member = new Member();
		member.setUserId("test");
		member.setPassword("passwd");
		member.setName("testtest");
		member.setAuthority("general");
		memberDao.insert(member);

		member.setPassword("changepassword");
		member.setName("changename");
		member.setAuthority("changeauthority");
		int num = memberDao.update(member);
		assertThat(num, is(1));

		Optional<Member> insertdMember = memberDao.selectByUserId("test");

		assertThat(insertdMember.get(), is(member));
	}

	@Test
	@Transactional
	public void update2() {

		Member member = new Member();
		member.setUserId("test");
		member.setPassword("passwd");
		member.setName("testtest");
		member.setAuthority("general");
		memberDao.update(member);

		member.setPassword("changepassword");
		member.setName("changename");
		member.setAuthority("changeauthority");
		int num = memberDao.update(member);
		assertThat(num, is(1));

		Optional<Member> insertdMember = memberDao.selectByUserId("test");

		assertThat(insertdMember.get(), is(member));
	}

	@Test
	@Transactional
	public void updateForUpdate() {

		Member member = new Member();
		member.setUserId("test");
		member.setPassword("passwd");
		member.setName("testtest");
		member.setAuthority("general");
		memberDao.insert(member);

		// forUpdate
		SelectOptions options = SelectOptions.get().forUpdate();
		memberDao.selectByUserId("test", options);

		// update
		member.setPassword("changepassword");
		member.setName("changename");
		member.setAuthority("changeauthority");
		int num = memberDao.update(member);
		assertThat(num, is(1));

		Optional<Member> insertdMember = memberDao.selectByUserId("test");

		assertThat(insertdMember.get(), is(member));
	}

	@Transactional
	public void delete() {

		Member member = new Member();
		member.setUserId("test");
		member.setPassword("passwd");
		member.setName("testtest");
		member.setAuthority("general");
		memberDao.insert(member);

		int num = memberDao.delete(member);
		assertThat(num, is(1));

		assertNull(memberDao.selectByUserId("test").get());

	}
}
