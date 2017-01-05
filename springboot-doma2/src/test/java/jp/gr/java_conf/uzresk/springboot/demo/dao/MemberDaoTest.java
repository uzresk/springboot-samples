package jp.gr.java_conf.uzresk.springboot.demo.dao;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import jp.gr.java_conf.uzresk.springboot.demo.DemoApplicationTestCase;
import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;
import jp.gr.java_conf.uzresk.springboot.framework.aop.AccessContext;
import jp.gr.java_conf.uzresk.springboot.framework.utils.DateUtils;
import org.assertj.db.type.Changes;
import org.assertj.db.type.DateTimeValue;
import org.assertj.db.type.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.db.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class MemberDaoTest extends DemoApplicationTestCase {

    @Autowired
    private MemberDao memberDao;

    @Test
    @Transactional()
    public void insert() {

        Member member = new Member();
        member.setUserId("test_userid");
        member.setName("testtest_name");
        member.setPassword("passwd");
        member.setGender("MALE");
        member.setAuthority("1");
        AccessContext.setUserName("DUMMY_USERNAME");
        LocalDateTime now = DateUtils.getSysdate();
        AccessContext.setSystemDate(now);

        // Insert Test
        int insertdRecordSize = memberDao.insert(member);

        assertThat(insertdRecordSize).isEqualTo(1);

        Request request = new Request(dataSource, "SELECT * FROM member WHERE user_id='test_userid'");
        assertThat(request).hasNumberOfRows(1)
                .row()
                .hasValues(
                        "test_userid",
                        "passwd",
                        "testtest_name",
                        "1",
                        "MALE",
                        "DUMMY_USERNAME",
                        DateTimeValue.from(Timestamp.valueOf(now)));
    }

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
    @Transactional
    public void findAll() {

        List<Member> findAllMember = memberDao.selectAll();
        assertThat(findAllMember).isNotNull().isNotEmpty()
                .extracting("userId", "password", "name", "authority", "gender")
                .contains(tuple("test1", "test1_password", "test1_name", "1", "MALE"), atIndex(0))
                .contains(tuple("test2", "test2_password", "test2_name", "2", "FEMALE"), atIndex(1));
    }


    @Test
    @Transactional
    public void delete() {
        
        Changes changes = new Changes(dataSource);
        changes.setStartPointNow();

        Member member = new Member();
        member.setUserId("test1");
        int num = memberDao.delete(member);
        
        changes.setEndPointNow();
        
        // 変更が1件であること、削除されたレコードのPKがtest1であること
        assertThat(changes)
                .hasNumberOfChanges(1)
                .change()
                  .isDeletion()
                  .hasPksValues("test1");
    }
}
