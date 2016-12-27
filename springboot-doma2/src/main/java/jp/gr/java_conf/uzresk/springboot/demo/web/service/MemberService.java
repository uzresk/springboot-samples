package jp.gr.java_conf.uzresk.springboot.demo.web.service;

import jp.gr.java_conf.uzresk.springboot.demo.dao.MemberDao;
import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    @Autowired
    private MemberDao memberDao;

    public Optional<Member> findMemberByUserId(String userId) {
        return memberDao.selectByUserId(userId);
    }

    @Transactional(readOnly = false)
    public void updateMember(Member member) {

        memberDao.delete(member);

        memberDao.insert(member);
    }

    public Page<Member> search(MemberSearchCondition condition) {

        SelectOptions options = condition.getSelectOptions();

        // 検索
        List<Member> members = memberDao.search(condition, options.count());
        // 総件数の取得
        long count = options.getCount();

        Page<Member> page = new PageImpl<>(members, condition.getPageable(), count);

        return page;

    }
}
