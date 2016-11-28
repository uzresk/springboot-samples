package jp.gr.java_conf.uzresk.springboot.framework.dao;

import java.time.LocalDateTime;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;
import org.springframework.stereotype.Component;

import jp.gr.java_conf.uzresk.springboot.framework.aop.AccessContext;

/**
 * https://github.com/domaframework/doma-spring-boot/tree/master/doma-spring-boot-samples/doma-spring-boot-sample-entity-listener
 *
 * @author uzresk
 *
 */
@Component
public class BaseEntityListener<T extends BaseEntity> implements EntityListener<T> {

	@Override
	public void preInsert(T baseEntity, PreInsertContext<T> context) {

		String userName = AccessContext.getUserName();
		baseEntity.setRegisteredPerson(userName);
		baseEntity.setUpdater(userName);

		// LocalDateTime now = DateUtils.getSysdate();
		LocalDateTime now = AccessContext.getSystemDate();
		baseEntity.setRegistrationDate(now);
		baseEntity.setUpdateDate(now);
	}

	@Override
	public void preUpdate(T baseEntity, PreUpdateContext<T> context) {
		baseEntity.setUpdater(AccessContext.getUserName());
		baseEntity.setUpdateDate(AccessContext.getSystemDate());
	}

}