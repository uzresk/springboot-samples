package jp.gr.java_conf.uzresk.springboot.demo.dao;

import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;
import jp.gr.java_conf.uzresk.springboot.demo.web.service.MemberSearchCondition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MemberSearchDaoTest {

    @Autowired
    MemberDao memberDao;

    @Test
    public void search() {

        Pageable pageable = new PageRequest(0,10);
        MemberSearchCondition condition = new MemberSearchCondition(pageable);
//        condition.setUserId("uzresk");
//        condition.setName("%aaaa%");

        List<Member> results = memberDao.search(condition, condition.getSelectOptions());

        results.stream().forEach(System.out::println);
    }
}
