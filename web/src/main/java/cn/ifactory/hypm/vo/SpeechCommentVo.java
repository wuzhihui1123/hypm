package cn.ifactory.hypm.vo;

import cn.ifactory.hypm.entity.SpeechComment;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 * 
 * @author yaha
 *
 */
public class SpeechCommentVo {
	
	
	public SpeechCommentVo(SpeechComment comment) {
		this.id = comment.getId();
		this.speecherName = comment.getUser().getNickname();
		this.createDate = comment.getCreateDate();
		this.content = comment.getContent();
	}

	private String id;
	private String speecherName;
	private Date createDate;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

	public String getSpeecherName() {
		return speecherName;
	}
	public void setSpeecherName(String speecherName) {
		this.speecherName = speecherName;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * po-->vo 集合转换
	 * @param comments
	 * @return
	 */
	public static Collection<SpeechCommentVo> convert(Collection<SpeechComment> comments) {
		Collection<SpeechCommentVo> ret = new ArrayList<SpeechCommentVo>();
		for(SpeechComment comment : comments) {
			ret.add(new SpeechCommentVo(comment));
		}
		return ret;
	}
	
}
