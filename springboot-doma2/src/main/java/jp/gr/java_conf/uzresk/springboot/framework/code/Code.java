package jp.gr.java_conf.uzresk.springboot.framework.code;

import lombok.Data;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;

import java.lang.annotation.Annotation;

@Data
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class Code {

    @Id
    String id;

    @Id
    String value;

    String sortOrder;

    String name;

    String shortName;

    String formName;
}
