package jp.gr.java_conf.uzresk.sbex.web.mfa;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import jp.gr.java_conf.uzresk.sbex.log.Log;
import jp.gr.java_conf.uzresk.sbex.web.service.LoginAccountDetails;

import org.apache.commons.codec.binary.Base32;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

/**
 * MFAコードで認証するための認証プロバイダ
 */
public class MFAAuthenticationProvider extends DaoAuthenticationProvider {

	private static Log logger = new Log(MFAWebAuthenticationDetails.class);

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		// アカウントID、パスワードで認証する。
		super.additionalAuthenticationChecks(userDetails, authentication);

		// MFAコードで認証する。
		if (authentication.getDetails() instanceof MFAWebAuthenticationDetails) {
			String secret = ((LoginAccountDetails) userDetails).getAccount()
					.getSecretKey();

			logger.debug("SecretKey:" + secret);

			// DBにSecretKeyが存在する場合のみMFAコードで認証する。
			if (!StringUtils.isEmpty(secret) && StringUtils.hasText(secret)) {
				Integer totpKey = ((MFAWebAuthenticationDetails) authentication
						.getDetails()).getTotpKey();

				logger.debug("totpKey:" + totpKey);
				if (totpKey != null) {
					try {
						if (!verifyCode(secret, totpKey, 2)) {
							throw new BadCredentialsException(
									"MFA Code is not valid");
						}
					} catch (InvalidKeyException | NoSuchAlgorithmException e) {
						throw new InternalAuthenticationServiceException(
								"MFA Code verify failed", e);
					}

				} else {
					throw new BadCredentialsException("totpKey is null.");
				}
			} else {
				logger.debug("SecretKeyが無いのでMFA認証なし");
			}
		}

	}

	public static boolean verifyCode(String secret, int code, int variance)
			throws InvalidKeyException, NoSuchAlgorithmException {
		long timeIndex = System.currentTimeMillis() / 1000 / 30;
		byte[] secretBytes = new Base32().decode(secret);
		for (int i = -variance; i <= variance; i++) {
			long c = getCode(secretBytes, timeIndex + i);
			logger.debug(new Long(c).toString());
			if (c == code) {
				return true;
			}
		}
		return false;
	}

	public static long getCode(byte[] secret, long timeIndex)
			throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec signKey = new SecretKeySpec(secret, "HmacSHA1");
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(timeIndex);
		byte[] timeBytes = buffer.array();
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signKey);
		byte[] hash = mac.doFinal(timeBytes);
		int offset = hash[19] & 0xf;
		long truncatedHash = hash[offset] & 0x7f;
		for (int i = 1; i < 4; i++) {
			truncatedHash <<= 8;
			truncatedHash |= hash[offset + i] & 0xff;
		}
		return truncatedHash %= 1000000;
	}

}
