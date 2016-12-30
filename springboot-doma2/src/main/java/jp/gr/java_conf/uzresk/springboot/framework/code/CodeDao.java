package jp.gr.java_conf.uzresk.springboot.framework.code;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.SelectType;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.stream.Collector;

@ConfigAutowireable
@Dao
public interface CodeDao {

    @Select(strategy = SelectType.COLLECT)
    <R> R selectAll(Collector<Code, ?, R> collector);


}
