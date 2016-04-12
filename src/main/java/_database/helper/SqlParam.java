package _database.helper;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class SqlParam {

	private int iIndex;
	private Object objValue;
	private int iSize;
	private SqlParamDirection direction;
	private int dbType;
	
	private SqlParam(int iIndex, SqlParamDirection direction, int dbType, Object objValue, int iSize) {
		setIndex(iIndex);
		setValue(objValue);
		setDirection(direction);
		setDbType(dbType);
		setSize(iSize);
	}
	
	public static SqlParam makeSqlParam(int iIndex, SqlParamDirection direction, int dbType, Object objValue, int iSize) throws NumberFormatException, IllegalArgumentException {
		if(direction == SqlParamDirection.In) {
			Object objParse = parseValue(dbType, objValue, iSize);
			return new SqlParam(iIndex, direction, dbType, objParse, iSize);
		} else {
			return new SqlParam(iIndex, direction, dbType, objValue, iSize);
		}
	}
	
	public static SqlParam makeReturnSqlParam(int dbType) throws NumberFormatException, IllegalArgumentException {
		return new SqlParam(1, SqlParamDirection.Return, dbType, null, Integer.MAX_VALUE);
	}
	
	public static SqlParam makeInSqlParam(int iIndex, int dbType, Object objValue, int iSize) throws NumberFormatException, IllegalArgumentException {
		Object objParse = parseValue(dbType, objValue, iSize);
		return new SqlParam(iIndex, SqlParamDirection.In, dbType, objParse, iSize);
	}
	
	public static SqlParam makeInSqlParam(int iIndex, int dbType, Object objValue) throws NumberFormatException, IllegalArgumentException {
		Object objParse = parseValue(dbType, objValue, 0);
		return new SqlParam(iIndex, SqlParamDirection.In, dbType, objParse, 0);
	}
	
	public static SqlParam makeOutSqlParam(int iIndex, int dbType, int iSize) throws NumberFormatException, IllegalArgumentException {
		return new SqlParam(iIndex, SqlParamDirection.Out, dbType, null, iSize);
	}
	
	public static SqlParam makeOutSqlParam(int iIndex, int dbType) throws NumberFormatException, IllegalArgumentException {
		return new SqlParam(iIndex, SqlParamDirection.Out, dbType, null, Integer.MAX_VALUE);
	}
	
	private static Object parseValue(int dbType, Object objValue, int iSize) throws NumberFormatException, IllegalArgumentException {
		Object objParse = null;
		switch(dbType) {
		case java.sql.Types.TINYINT:
			if(objValue == null)
				throw new IllegalArgumentException();
			int vTinyInt = Integer.parseInt(objValue.toString());
			if(vTinyInt < 0 || vTinyInt > 255) {
				throw new IllegalArgumentException();
			}
			objParse = vTinyInt;
			break;
		case java.sql.Types.INTEGER:
			if(objValue == null)
				throw new IllegalArgumentException();
			objParse = Integer.parseInt(objValue.toString());
			break;
		case java.sql.Types.BIGINT:
			if(objValue == null)
				throw new IllegalArgumentException();
			objParse = Long.parseLong(objValue.toString());
			break;
		case java.sql.Types.VARCHAR:
			if(objValue == null) {
				objParse = null;
			} else {
				if(iSize <= 0)
					throw new IllegalArgumentException("size is not specified");
				int iStringSize = 0;
				try {
					iStringSize = objValue.toString().getBytes("UTF-8").length;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if(iStringSize > iSize) {
					throw new IllegalArgumentException("input value is too long");
				}
				objParse = objValue.toString();
			}
			break;
		case java.sql.Types.FLOAT:
			if(objValue == null)
				throw new IllegalArgumentException();
			objParse = Float.parseFloat(objValue.toString());
			break;
		case java.sql.Types.DOUBLE:
			if(objValue == null)
				throw new IllegalArgumentException();
			objParse = Double.parseDouble(objValue.toString());
			break;
		case java.sql.Types.DATE:
			if(objValue == null)
				throw new IllegalArgumentException();
			objParse = Date.valueOf(objValue.toString());
			break;
		case java.sql.Types.TIME:
			if(objValue == null)
				throw new IllegalArgumentException();
			objParse = Time.valueOf(objValue.toString());
			break;
		case java.sql.Types.TIMESTAMP:
			if(objValue == null)
				objParse = null;
				//throw new IllegalArgumentException();
			else
				objParse = Timestamp.valueOf(objValue.toString());
			break;
		default:
			throw new IllegalArgumentException("undefined dbtype of " + dbType);
		}
		return objParse;
	}
	
	public Object getValue() throws IllegalArgumentException {
		switch(dbType) {
		case java.sql.Types.TINYINT:
			if(objValue == null)
				throw new IllegalArgumentException();
			int vTinyInt = -1;
			try {
				vTinyInt = Integer.parseInt(objValue.toString());
			} catch (java.lang.NumberFormatException e) {
				throw new IllegalArgumentException();
			}
			if(vTinyInt < 0 || vTinyInt > 255) {
				throw new IllegalArgumentException();
			}
			return vTinyInt;
		case java.sql.Types.INTEGER:
			if(objValue == null)
				throw new IllegalArgumentException();
			try {
				int vInt = Integer.parseInt(objValue.toString());
				return vInt;
			} catch (java.lang.NumberFormatException e) {
				throw new IllegalArgumentException();
			}
		case java.sql.Types.BIGINT:
			if(objValue == null)
				throw new IllegalArgumentException();
			try {
				long vBigInt = Long.parseLong(objValue.toString());
				return vBigInt;
			} catch (java.lang.NumberFormatException e) {
				throw new IllegalArgumentException();
			}
		case java.sql.Types.VARCHAR:
			if(objValue == null)
				return null;
			return objValue.toString();
		case java.sql.Types.FLOAT:
			if(objValue == null)
				throw new IllegalArgumentException();
			try {
				float vFloat = Float.parseFloat(objValue.toString());
				return vFloat;
			} catch (java.lang.NumberFormatException e) {
				throw new IllegalArgumentException();
			}
		case java.sql.Types.DOUBLE:
			if(objValue == null)
				throw new IllegalArgumentException();
			try {
				double vDouble = Double.parseDouble(objValue.toString());
				return vDouble;
			} catch (java.lang.NumberFormatException e) {
				throw new IllegalArgumentException();
			}
		case java.sql.Types.DATE:
			if(objValue == null)
				return null;
			return Date.valueOf(objValue.toString());
		case java.sql.Types.TIME:
			if(objValue == null)
				return null;
			return Time.valueOf(objValue.toString());
		case java.sql.Types.TIMESTAMP:
			if(objValue == null)
				return null;
			return Timestamp.valueOf(objValue.toString());
		default:
			return objValue;
		}
	}
	
	public void setValue(Object objValue) {
		this.objValue = objValue;
	}
	
	public int getSize() {
		return this.iSize;
	}
	
	public void setSize(int iSize) {
		this.iSize = iSize;
	}
	
	public SqlParamDirection getDirection() {
		return this.direction;
	}
	
	public void setDirection(SqlParamDirection direction) {
		this.direction = direction;
	}
	
	public int getIndex() {
		return this.iIndex;
	}
	
	public void setIndex(int iIndex) {
		this.iIndex = iIndex;
	}
	
	public int getDbType() {
		return this.dbType;
	}
	
	public void setDbType(int dbType) {
		this.dbType = dbType;
	}
	
	public enum SqlParamDirection {
		In, Out, Return
	}
	
	/*
	public enum SqlParamDbType {
		TinyInt, Int, BigInt, Float, Double, Money, DateTime, VarChar
	}
	*/
}
