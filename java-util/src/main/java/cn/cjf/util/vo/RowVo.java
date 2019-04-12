package cn.cjf.util.vo;

import java.io.Serializable;
import java.util.List;

public class RowVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> columnValues; //该行所有列的值列表
	
	

	@Override
	public String toString() {
		return "RowVo [columnValues=" + columnValues + "]";
	}

	public List<String> getColumnValues() {
		return columnValues;
	}

	public void setColumnValues(List<String> columnValues) {
		this.columnValues = columnValues;
	}

}
