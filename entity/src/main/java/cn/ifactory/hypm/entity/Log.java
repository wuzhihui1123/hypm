package cn.ifactory.hypm.entity;

import javax.persistence.*;


/**
 * 日志
 * @author yaha
 *
 */
@SuppressWarnings("all")
@Entity
@Table(name="hypm_log")
public class Log extends BaseEntity {
	/**
	 * 操作对象
	 */
	private String target;
	/**
	 * 操作人
	 */
	private String operator;
	@Enumerated(EnumType.ORDINAL)
	/**
	 * 操作类型
	 */
	private OperateType operateType;
	/**
	 * 描述
	 */
	@Column(length=1000)
	private String descp;
	
	public Log() {}

	public Log(String target, String operator,
			OperateType operateType, String descp) {
		super();
		this.target = target;
		this.operator = operator;
		this.operateType = operateType;
		this.descp = descp;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public OperateType getOperateType() {
		return operateType;
	}
	public String getOperateTypeStr() {
		return getOperateType() == null ? "" : getOperateType().getName();
	}

	public void setOperateType(OperateType operateType) {
		this.operateType = operateType;
	}
	
	
	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public enum TargetObj {
		NODE(Node.class,"项目"),
		SPEECH(Node.class,"发言");
		private Class objClass;
		private String name;

		private TargetObj(Class objClass,String name) {
			this.objClass = objClass;
			this.name = name;
		}

		public Class getObjClass() {
			return objClass;
		}

		public String getName() {
			return name;
		}
	}

	public enum OperateType {
		ADD("新增"),
		UPDATE("修改"),
		MOVE("移动"),
		REMOVE("删除");
		private String name;

		private OperateType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

}


