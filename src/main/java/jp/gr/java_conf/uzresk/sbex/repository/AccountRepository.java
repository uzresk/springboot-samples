package jp.gr.java_conf.uzresk.sbex.repository;

import jp.gr.java_conf.uzresk.sbex.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}
