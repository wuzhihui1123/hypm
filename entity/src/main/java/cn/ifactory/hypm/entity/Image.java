package cn.ifactory.hypm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * 图片
 * @author yaha
 *
 */
@Entity
@Table(name="hypm_image")
public class Image extends BaseEntity {
	/**
	 * 图片名称
	 */
	private String name;
	/**
	 * 图片数据
	 */
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(columnDefinition="MEDIUMBLOB",nullable=false)
	@JsonIgnore  //防止在不知情的情况下(非本意)对image对象数据Json化所带来的更大化问题
	private byte[] datas;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getDatas() {
		return datas;
	}
	public void setDatas(byte[] datas) {
		this.datas = datas;
	}
}
