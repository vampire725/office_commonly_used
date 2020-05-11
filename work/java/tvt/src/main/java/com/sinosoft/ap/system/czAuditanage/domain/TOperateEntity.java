package com.sinosoft.ap.system.czAuditanage.domain;

import java.util.Date;

public class TOperateEntity {
	//主键
		private String opId;
		//操作类型
		private Integer opType;
		//操作用户名字
		private String opName;
		//操作详情
		private String opDetail;
		//操作结果
		private Integer opResult;
		//操作时间
		private Date opTime;
		private String startTimeStr;
		private String endTimeStr;
		private Date startTime;
		private Date endTime;
		private Integer fssort;//1 升序 2降序
		//操作子系统名称
		private String opSystem;
		//操作子系统编码
		private String opSystemCode;
		//操作用户id
		private String opUserId;
		//模块名称
		private String opSystemModule;
		public String getOpId() {
			return opId;
		}
		public void setOpId(String opId) {
			this.opId = opId;
		}
		public Integer getOpType() {
			return opType;
		}
		public void setOpType(Integer opType) {
			this.opType = opType;
		}
		public String getOpName() {
			return opName;
		}
		public void setOpName(String opName) {
			this.opName = opName;
		}
		public String getOpDetail() {
			return opDetail;
		}
		public void setOpDetail(String opDetail) {
			this.opDetail = opDetail;
		}
		
		
		public Integer getOpResult() {
			return opResult;
		}
		public void setOpResult(Integer opResult) {
			this.opResult = opResult;
		}
		public Date getOpTime() {
			return opTime;
		}
		public void setOpTime(Date opTime) {
			this.opTime = opTime;
		}
		public Date getStartTime() {
			return startTime;
		}
		public Date getEndTime() {
			return endTime;
		}
		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		public Integer getFssort() {
			return fssort;
		}
		public void setFssort(Integer fssort) {
			this.fssort = fssort;
		}
		public String getOpSystem() {
			return opSystem;
		}
		public void setOpSystem(String opSystem) {
			this.opSystem = opSystem;
		}
		public String getOpSystemCode() {
			return opSystemCode;
		}
		public void setOpSystemCode(String opSystemCode) {
			this.opSystemCode = opSystemCode;
		}
		public String getOpUserId() {
			return opUserId;
		}
		public void setOpUserId(String opUserId) {
			this.opUserId = opUserId;
		}
		public String getOpSystemModule() {
			return opSystemModule;
		}
		public void setOpSystemModule(String opSystemModule) {
			this.opSystemModule = opSystemModule;
		}
		public String getStartTimeStr() {
			return startTimeStr;
		}
		public String getEndTimeStr() {
			return endTimeStr;
		}
		public void setStartTimeStr(String startTimeStr) {
			this.startTimeStr = startTimeStr;
		}
		public void setEndTimeStr(String endTimeStr) {
			this.endTimeStr = endTimeStr;
		}
		@Override
		public String toString() {
			return "OperateEntity [opId=" + opId + ", opType=" + opType + ", opName=" + opName + ", opDetail=" + opDetail
					+ ", opResult=" + opResult + ", opTime=" + opTime + ", opSystem=" + opSystem + ", opSystemCode="
					+ opSystemCode + ", opUserId=" + opUserId + ", opSystemModule=" + opSystemModule + "]";
		}
		
}
