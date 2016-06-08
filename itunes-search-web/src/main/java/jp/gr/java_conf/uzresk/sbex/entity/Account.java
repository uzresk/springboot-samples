package jp.gr.java_conf.uzresk.sbex.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	// use oauth
	public Account(String accountId,String password) {
		this.accountId = accountId;
		this.password = password;
	}

	/** アカウントID */
	@Id
	@Column(name = "account_id")
	private String accountId;

	/** アカウント付属情報 */
	@Column(name = "password")
	private String password;

	/** メールアドレス */
	@Column(name = "mail")
	private String mail;

	/** シークレットキー */
	@Column(name = "secret_key")
	private String secretKey;

}
