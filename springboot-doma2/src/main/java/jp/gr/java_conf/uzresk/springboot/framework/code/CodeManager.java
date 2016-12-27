package jp.gr.java_conf.uzresk.springboot.framework.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class CodeManager {

    private static ConcurrentMap<String, List<Code>> cache;

    @Autowired
    CodeDao codeDao;

    @PostConstruct
    private void load() {

//        cache = codes.stream().collect(Collectors.groupingByConcurrent(s -> s.getId()));
//        cache = codeDao.selectAll(Collectors.groupingByConcurrent(s -> s.getId()));
        cache = codeDao.selectAll(Collectors.groupingByConcurrent(Code::getId));

        // TODO unmodifiedMap
    }

    public ConcurrentMap<String, List<Code>> getCodeCache() {
        return cache;
    }

    public List<Code> getCodes(String id) {
        return cache.get(id);
    }

    public boolean contains(String id, String value) {
        return getCodes(id).stream().anyMatch(s -> s.getValue().equals(value));
    }
}
