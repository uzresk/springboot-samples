package jp.gr.java_conf.uzresk.sbex.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Media {

	MOVIE("Movie", "movie"),
	MUSIC("Music", "music"),
	MUSIC_VIDEO("Music Video", "musicVideo");

	private final String name;

	private final String value;

	private Media(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public static Map<String, String> nameValueMap() {

		return Arrays.asList(Media.values()).stream()
				.collect(Collectors.toMap(s -> s.getValue(), s -> s.getName()));
	}

	public static Media fromValue(String value) {

		return Arrays.asList(Media.values()).stream().filter(s -> s.value.equals(value)).findFirst().get();
	}

}
