package jp.gr.java_conf.uzresk.springboot.framework.code;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class CodeManager {

    private static ConcurrentMap<String, List<Code>> cache;

    private final CodeDao codeDao;
    
    public CodeManager(CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    @PostConstruct
    public void load() {

//        cache = codes.stream().collect(Collectors.groupingByConcurrent(s -> s.getId()));
//        cache = codeDao.selectAll(Collectors.groupingByConcurrent(s -> s.getId()));
        cache = codeDao.selectAll(Collectors.groupingByConcurrent(Code::getId));
    }

    public List<Code> getCodes(String id) {
        return cache.get(id);
    }

    public boolean contains(String id, String value) {
        return getCodes(id).stream().anyMatch(s -> s.getValue().equals(value));
    }
}
