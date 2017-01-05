package jp.gr.java_conf.uzresk.springboot.framework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
public class SearchCondition {

    private Pageable pageable;

    public SelectOptions getSelectOptions() {

        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();

        return SelectOptions.get().offset(offset).limit(limit);
    }
}
