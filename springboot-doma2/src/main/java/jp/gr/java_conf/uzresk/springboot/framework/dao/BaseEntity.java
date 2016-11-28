package jp.gr.java_conf.uzresk.springboot.framework.dao;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Data;

@Data
@Entity(naming = NamingType.SNAKE_UPPER_CASE,  listener=BaseEntityListener.class)
public class BaseEntity {

	private String registeredPerson;

	private LocalDateTime registrationDate;

	private String updater;

	private LocalDateTime updateDate;
}
