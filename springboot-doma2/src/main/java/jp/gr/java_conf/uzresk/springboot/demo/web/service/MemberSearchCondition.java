package jp.gr.java_conf.uzresk.springboot.demo.web.service;

import jp.gr.java_conf.uzresk.springboot.framework.model.SearchCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Pageable;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberSearchCondition extends SearchCondition {

    public MemberSearchCondition(Pageable pageable) {
        super(pageable);
    }

    private String userId;

    private String name;
}
