
/**
 * Project Name : Tools
 * File Name    : CalendarTypeHandler.java
 * Package Name : net.yuanmomo.tools.db.orm.mybatis.generator.plugin.typehandler
 * Created on   : 2014-2-20下午4:58:06
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.generator.plugin.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedJdbcTypes({JdbcType.TIMESTAMP,JdbcType.TIME,JdbcType.DATE})
@MappedTypes(Calendar.class)
public class CalendarTypeHandler  extends BaseTypeHandler<Calendar> {

	/**
	 * setNonNullParameter:. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param ps
	 * @param i
	 * @param parameter
	 * @param jdbcType
	 * @throws SQLException
	 * @see BaseTypeHandler#setNonNullParameter(PreparedStatement, int, Object, JdbcType)
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Calendar parameter, JdbcType jdbcType) throws SQLException {
		ps.setTimestamp(i, new Timestamp(parameter.getTimeInMillis()));
	}

	/**
	 * getNullableResult:. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param rs
	 * @param columnName
	 * @return
	 * @throws SQLException
	 * @see BaseTypeHandler#getNullableResult(ResultSet, String)
	 */
	@Override
	public Calendar getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Timestamp sqlTimestamp = rs.getTimestamp(columnName);
	    if (sqlTimestamp != null) {
	    	Calendar cal = Calendar.getInstance();
	    	cal.setTimeInMillis(sqlTimestamp.getTime());
	    	return cal;
	    }
	    return null;
	}

	/**
	 * getNullableResult:. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param rs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 * @see BaseTypeHandler#getNullableResult(ResultSet, int)
	 */
	@Override
	public Calendar getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
	    if (sqlTimestamp != null) {
	    	Calendar cal = Calendar.getInstance();
	    	cal.setTimeInMillis(sqlTimestamp.getTime());
	    	return cal;
	    }
	    return null;
	}

	/**
	 * getNullableResult:. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param cs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 * @see BaseTypeHandler#getNullableResult(CallableStatement, int)
	 */
	@Override
	public Calendar getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
	    if (sqlTimestamp != null) {
	    	Calendar cal = Calendar.getInstance();
	    	cal.setTimeInMillis(sqlTimestamp.getTime());
	    	return cal;
	    }
	    return null;
	}
}
