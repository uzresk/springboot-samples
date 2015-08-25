package jp.gr.java_conf.uzresk.sbex.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItunesSearchResult implements Serializable{

	private static final long serialVersionUID = 1L;

	private int resultCount;

	private List<Goods> results = new ArrayList<Goods>();
}
