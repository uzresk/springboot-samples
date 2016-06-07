package jp.gr.java_conf.uzresk.sbex.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

	private static final Logger DEBUG_LOG = LoggerFactory
			.getLogger(Log.class);

	/** 出力クラス名 */
	private String className;

	public Log(Class<?> outputClass) {
		this.className = outputClass.getName();
	}

	public static enum EventType {

		RESERVE("RESERVE"), CANCEL("CANCEL");

		private String typeName;

		private EventType(String typeName) {
			this.typeName = typeName;
		}

		public String typeName() {
			return typeName;
		}
	}

	public void debug(Object message) {
		if (DEBUG_LOG.isDebugEnabled()) {
			DEBUG_LOG.debug(className + ":" + message.toString());
		}
	}

	public void info(Object message) {
		if (DEBUG_LOG.isInfoEnabled()) {
			DEBUG_LOG.info(className + ":" + message.toString());
		}
	}
}
