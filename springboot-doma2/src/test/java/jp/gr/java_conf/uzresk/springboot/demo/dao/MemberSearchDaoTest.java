package jp.gr.java_conf.uzresk.springboot.demo.dao;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import jp.gr.java_conf.uzresk.springboot.demo.DemoApplicationTestCase;
import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;
import jp.gr.java_conf.uzresk.springboot.demo.web.service.MemberSearchCondition;
import jp.gr.java_conf.uzresk.springboot.framework.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.data.Index.atIndex;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class MemberSearchDaoTest extends DemoApplicationTestCase {

    @Autowired
    MemberDao memberDao;

    private static final Operation DELETE_ALL_PERSON = Operations.deleteAllFrom("member");
    private static final Operation INSERT_MEMBER = Operations.insertInto("member")
            .columns("user_id", "password", "name", "authority", "gender", "regist_user", "regist_date")
            .values("test1", "test1_password", "test1_name", "1", "MALE", "DUMMY_USERNAME", DateUtils.getSysdate())
            .values("test2", "test2_password", "test2_name", "2", "FEMALE", "DUMMY_USERNAME", DateUtils.getSysdate())
            .build();

    @Before
    public void setupMember() {
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), Operations.sequenceOf(DELETE_ALL_PERSON, INSERT_MEMBER));
        new DbSetupTracker().launchIfNecessary(dbSetup);
    }

    @Test
    public void searchNothing() {

        Pageable pageable = new PageRequest(0,10);
        MemberSearchCondition condition = new MemberSearchCondition(pageable);
        condition.setUserId("hogehoge");

        List<Member> searchResults = memberDao.search(condition, condition.getSelectOptions());

        assertThat(searchResults).isEmpty();
    }

    @Test
    public void searchUserId() {

        Pageable pageable = new PageRequest(0,10);
        MemberSearchCondition condition = new MemberSearchCondition(pageable);
        condition.setUserId("test1");

        List<Member> searchResults = memberDao.search(condition, condition.getSelectOptions());

        assertThat(searchResults).isNotNull().isNotEmpty()
                .hasSize(1)
                .extracting("userId", "password", "name", "authority", "gender")
                .contains(tuple("test1", "test1_password", "test1_name", "1", "MALE"), atIndex(0));
    }

    @Test
    public void searchName() {

        Pageable pageable = new PageRequest(0,10);
        MemberSearchCondition condition = new MemberSearchCondition(pageable);
        condition.setName("test");

        List<Member> searchResults = memberDao.search(condition, condition.getSelectOptions());

        assertThat(searchResults).isNotNull().isNotEmpty()
                .hasSize(2)
                .extracting("userId", "password", "name", "authority", "gender")
                .contains(tuple("test1", "test1_password", "test1_name", "1", "MALE"), atIndex(0))
                .contains(tuple("test2", "test2_password", "test2_name", "2", "FEMALE"), atIndex(1));
    }

    @Test
    public void searchUserIdName() {

        Pageable pageable = new PageRequest(0,10);
        MemberSearchCondition condition = new MemberSearchCondition(pageable);
        condition.setUserId("test1");
        condition.setName("test");

        List<Member> searchResults = memberDao.search(condition, condition.getSelectOptions());

        assertThat(searchResults).isNotNull().isNotEmpty()
                .hasSize(1)
                .extracting("userId", "password", "name", "authority", "gender")
                .contains(tuple("test1", "test1_password", "test1_name", "1", "MALE"), atIndex(0));
    }

    @Test
    public void paging() {

        Pageable pageable = new PageRequest(1,1);
        MemberSearchCondition condition = new MemberSearchCondition(pageable);
        condition.setName("test");

        List<Member> searchResults = memberDao.search(condition, condition.getSelectOptions());

        assertThat(searchResults).isNotNull().isNotEmpty()
                .hasSize(1)
                .extracting("userId", "password", "name", "authority", "gender")
                .contains(tuple("test2", "test2_password", "test2_name", "2", "FEMALE"), atIndex(0));
    }
}
