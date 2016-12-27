package jp.gr.java_conf.uzresk.springboot.framework.dao;

import jp.gr.java_conf.uzresk.springboot.framework.aop.AccessContext;
import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * https://github.com/domaframework/doma-spring-boot/tree/master/doma-spring-boot-samples/doma-spring-boot-sample-entity-listener
 *
 * @author uzresk
 */
@Component
public class BaseEntityListener<T extends BaseEntity> implements EntityListener<T> {

    @Override
    public void preInsert(T baseEntity, PreInsertContext<T> context) {

        String userName = AccessContext.getUserName();
        baseEntity.setRegistUser(userName);

        // LocalDateTime now = DateUtils.getSysdate();
        LocalDateTime now = AccessContext.getSystemDate();
        baseEntity.setRegistDate(now);
    }
}