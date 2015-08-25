package jp.gr.java_conf.uzresk.sbex.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Goods implements Serializable {

	private static final long serialVersionUID = 1L;

	private String trackId;

	private String artistName;

	private String trackName;

	private String trackCensoredName;

	private String trackViewUrl;

	private String previewUrl;

	private String primaryGenreName;

	private String longDescription;

	private Date releaseDate;

	private int collectionPrice;

	private String trackTimeMillis;

	private String artworkUrl30;

	private String artworkUrl60;

	private String artworkUrl100;

}
