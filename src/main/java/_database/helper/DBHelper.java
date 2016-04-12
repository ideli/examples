package _database.helper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import _database.helper.SqlParam.SqlParamDirection;

public class DBHelper {

    public static void executeNonQuery(Connection conn,
                                       CommandType ctCommandType, String strCommandText,
                                       SqlParam[] sqlParams) throws SQLException {

        if (ctCommandType == CommandType.Text) {
            executeTextNonQuery(conn, strCommandText, sqlParams);
            return;
        }

        //调用存储过程的格式为 {?=call SomeProc(?,?,?,?)}
        if (sqlParams != null && sqlParams.length > 0) {
            int iInOutParamsStart = 0;
            // 是否有返回值
            String strReturnParam = "";
            if (sqlParams[0].getDirection() == SqlParamDirection.Return) {
                strReturnParam = "?=";
                iInOutParamsStart++;
            }

            String strParam = "(";
            for (int i = iInOutParamsStart; i < sqlParams.length; i++) {
                strParam += "?,";
            }
            strParam = strParam.substring(0, strParam.length() - 1);
            strParam += ")";
            strCommandText = "{" + strReturnParam + "call " + strCommandText
                    + strParam + "}";
        }

        CallableStatement cs = conn.prepareCall(strCommandText);
        setParams(cs, sqlParams);
        cs.execute();

        ResultSet rs = cs.getResultSet();
        if (rs != null)
            rs.close();
        while (cs.getMoreResults()) {
            rs = cs.getResultSet();
            if (rs != null)
                rs.close();
        }

        if (sqlParams != null && sqlParams.length > 0) {
            for (int i = 0; i < sqlParams.length; i++) {
                if (sqlParams[i].getDirection() == SqlParamDirection.Out
                        || sqlParams[i].getDirection() == SqlParamDirection.Return) {
                    sqlParams[i]
                            .setValue(cs.getObject(sqlParams[i].getIndex()));
                }

            }
        }

        cs.close();
    }

    private static void executeTextNonQuery(Connection conn, String strCommandText, SqlParam[] sqlParams) throws SQLException {

        PreparedStatement ps = conn.prepareStatement(strCommandText);
        setParams(ps, sqlParams);
        ps.execute();

        ResultSet rs = ps.getResultSet();
        if (rs != null)
            rs.close();
        while (ps.getMoreResults()) {
            rs = ps.getResultSet();
            if (rs != null)
                rs.close();
        }
        ps.close();
    }

    public static DataTable executeDataTable(Connection conn,
                                             CommandType ctCommandType, String strCommandText,
                                             SqlParam[] sqlParams) throws SQLException {
        List<DataTable> tables = executeDataSet(conn, ctCommandType,
                strCommandText, sqlParams, true);
        if (tables != null && tables.size() > 0) {
            return tables.get(0);
        } else {
            return null;
        }
    }

    public static List<DataTable> executeDataSet(Connection conn,
                                                 CommandType ctCommandType, String strCommandText,
                                                 SqlParam[] sqlParams, boolean bSingleTable) throws SQLException {

        if (conn == null) {
            System.out.println("conn null");
            return null;
        }

        if (ctCommandType.equals(CommandType.Text)) {
            return executeTextForDataSet(conn, strCommandText, sqlParams,
                    bSingleTable);
        }

        CallableStatement cs = null;
        if (sqlParams != null && sqlParams.length > 0) {
            int iInOutParamsStart = 0;
            // 是否有返回值
            String strReturnParam = "";
            if (sqlParams[0].getDirection() == SqlParamDirection.Return) {
                strReturnParam = "?=";
                iInOutParamsStart++;
            }

            String strParam = "(";
            for (int i = iInOutParamsStart; i < sqlParams.length; i++) {
                strParam += "?,";
            }
            strParam = strParam.substring(0, strParam.length() - 1);
            strParam += ")";
            strCommandText = "{" + strReturnParam + "call " + strCommandText
                    + strParam + "}";
        }
        cs = conn.prepareCall(strCommandText);
        setParams(cs, sqlParams);
        cs.execute();

        List<DataTable> tables = new ArrayList<DataTable>();
        String columnName;
        Object value;
        int iRowCount = 0;
        DataTable dt = null;
        List<DataColumn> columns = null;
        List<DataRow> rows = new ArrayList<DataRow>();
        DataColumn column = null;
        DataRow row = null;
        ResultSetMetaData metaData = null;

        ResultSet rs = cs.getResultSet();
        if (rs != null) {
            metaData = rs.getMetaData();

            //开始循环读取，每次往表中插入一行记录
            while (rs.next()) {
                iRowCount++;
                columns = new ArrayList<DataColumn>(); //初始化列集合
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columnName = metaData.getColumnName(i);
                    value = rs.getObject(columnName);
                    column = new DataColumn(columnName, value);//初始化单元列
                    columns.add(column); //将列信息加入到列集合
                }
                row = new DataRow(columns);//初始化单元行
                rows.add(row);//将行信息加入到行集合
            }
            dt = new DataTable(rows);
            dt.setRowCount(iRowCount);
            dt.setColumnCount(metaData.getColumnCount());
            tables.add(dt);
            rs.close();
        }

        //如果多个表, 遍历并重复上面的过程
        while (cs.getMoreResults()) {
            rs = cs.getResultSet();
            metaData = rs.getMetaData();
            rows = new ArrayList<DataRow>();
            iRowCount = 0;
            while (rs.next()) {
                iRowCount++;
                columns = new ArrayList<DataColumn>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columnName = metaData.getColumnName(i);
                    value = rs.getObject(columnName);
                    column = new DataColumn(columnName, value);
                    columns.add(column);
                }
                row = new DataRow(columns);
                rows.add(row);
            }
            dt = new DataTable(rows);
            dt.setRowCount(iRowCount);
            dt.setColumnCount(metaData.getColumnCount());
            tables.add(dt);
            rs.close();
        }

        if (sqlParams != null && sqlParams.length > 0) {
            for (int i = 0; i < sqlParams.length; i++) {
                if (sqlParams[i].getDirection() == SqlParamDirection.Out
                        || sqlParams[i].getDirection() == SqlParamDirection.Return) {
                    sqlParams[i]
                            .setValue(cs.getObject(sqlParams[i].getIndex()));
                }

            }
        }

        cs.close();
        return tables;
    }

    private static List<DataTable> executeTextForDataSet(Connection conn,
                                                         String strCommandText, SqlParam[] sqlParams, boolean bSingleTable)
            throws SQLException {

        if (conn == null) {
            return null;
        }

        PreparedStatement ps = conn.prepareStatement(strCommandText);
        setParams(ps, sqlParams);
        ps.execute();

        List<DataTable> tables = new ArrayList<DataTable>();
        String columnName;
        Object value;
        int iRowCount = 0;
        DataTable dt = null;
        List<DataColumn> columns = null;
        List<DataRow> rows = new ArrayList<DataRow>();
        DataColumn column = null;
        DataRow row = null;
        ResultSetMetaData metaData = null;

        ResultSet rs = ps.getResultSet();
        if (rs != null) {
            metaData = rs.getMetaData();

            while (rs.next()) {
                iRowCount++;
                columns = new ArrayList<DataColumn>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columnName = metaData.getColumnName(i);
                    value = rs.getObject(columnName);
                    column = new DataColumn(columnName, value);
                    columns.add(column);
                }
                row = new DataRow(columns);
                rows.add(row);
            }
            dt = new DataTable(rows);
            dt.setRowCount(iRowCount);
            dt.setColumnCount(metaData.getColumnCount());
            tables.add(dt);
            rs.close();
        }

        while (ps.getMoreResults()) {
            rs = ps.getResultSet();
            metaData = rs.getMetaData();
            rows = new ArrayList<DataRow>();
            iRowCount = 0;
            while (rs.next()) {
                iRowCount++;
                columns = new ArrayList<DataColumn>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columnName = metaData.getColumnName(i);
                    value = rs.getObject(columnName);
                    column = new DataColumn(columnName, value);
                    columns.add(column);
                }
                row = new DataRow(columns);
                rows.add(row);
            }
            dt = new DataTable(rows);
            dt.setRowCount(iRowCount);
            dt.setColumnCount(metaData.getColumnCount());
            tables.add(dt);
            rs.close();
        }
        ps.close();
        return tables;
    }

    private static void setParams(CallableStatement ps, SqlParam[] sqlParams)
            throws SQLException {
        if (sqlParams != null && sqlParams.length > 0) {
            for (int i = 0; i < sqlParams.length; i++) {

                if (sqlParams[i].getDirection() == SqlParamDirection.Out
                        || sqlParams[i].getDirection() == SqlParamDirection.Return) {
                    ps.registerOutParameter(sqlParams[i].getIndex(),
                            sqlParams[i].getDbType());
                } else {
                    switch (sqlParams[i].getDbType()) {
                        case java.sql.Types.TINYINT:
                            ps.setInt(sqlParams[i].getIndex(),
                                    (Integer) sqlParams[i].getValue());
                            break;
                        case java.sql.Types.INTEGER:
                            ps.setInt(sqlParams[i].getIndex(),
                                    (Integer) sqlParams[i].getValue());
                            break;
                        case java.sql.Types.BIGINT:
                            ps.setLong(sqlParams[i].getIndex(),
                                    (Long) sqlParams[i].getValue());
                            break;
                        case java.sql.Types.VARCHAR:
                            ps.setString(sqlParams[i].getIndex(), sqlParams[i]
                                    .getValue() == null ? null
                                    : (String) sqlParams[i].getValue());
                            break;
                        case java.sql.Types.FLOAT:
                            ps.setFloat(sqlParams[i].getIndex(),
                                    (Float) sqlParams[i].getValue());
                            break;
                        case java.sql.Types.DOUBLE:
                            ps.setDouble(sqlParams[i].getIndex(),
                                    (Double) sqlParams[i].getValue());
                            break;
                        case java.sql.Types.DATE:
                            ps.setDate(sqlParams[i].getIndex(), sqlParams[i]
                                    .getValue() == null ? null
                                    : (Date) sqlParams[i].getValue());
                            break;
                        case java.sql.Types.TIME:
                            ps.setTime(sqlParams[i].getIndex(), sqlParams[i]
                                    .getValue() == null ? null
                                    : (Time) sqlParams[i].getValue());
                            break;
                        case java.sql.Types.TIMESTAMP:
                            ps.setTimestamp(sqlParams[i].getIndex(), sqlParams[i]
                                    .getValue() == null ? null
                                    : (Timestamp) sqlParams[i].getValue());
                            break;
                        default:
                            ps.setObject(sqlParams[i].getIndex(),
                                    sqlParams[i].getValue());
                            break;
                    }
                }
            }
        }
    }

    private static void setParams(PreparedStatement ps, SqlParam[] sqlParams)
            throws SQLException {
        if (sqlParams != null && sqlParams.length > 0) {
            for (int i = 0; i < sqlParams.length; i++) {
                switch (sqlParams[i].getDbType()) {
                    case java.sql.Types.TINYINT:
                        ps.setInt(sqlParams[i].getIndex(),
                                (Integer) sqlParams[i].getValue());
                        break;
                    case java.sql.Types.INTEGER:
                        ps.setInt(sqlParams[i].getIndex(),
                                (Integer) sqlParams[i].getValue());
                        break;
                    case java.sql.Types.BIGINT:
                        ps.setLong(sqlParams[i].getIndex(),
                                (Long) sqlParams[i].getValue());
                        break;
                    case java.sql.Types.VARCHAR:
                        ps.setString(sqlParams[i].getIndex(),
                                sqlParams[i].getValue() == null ? null
                                        : (String) sqlParams[i].getValue());
                        break;
                    case java.sql.Types.FLOAT:
                        ps.setFloat(sqlParams[i].getIndex(),
                                (Float) sqlParams[i].getValue());
                        break;
                    case java.sql.Types.DOUBLE:
                        ps.setDouble(sqlParams[i].getIndex(),
                                (Double) sqlParams[i].getValue());
                        break;
                    case java.sql.Types.DATE:
                        ps.setDate(sqlParams[i].getIndex(),
                                sqlParams[i].getValue() == null ? null
                                        : (Date) sqlParams[i].getValue());
                        break;
                    case java.sql.Types.TIME:
                        ps.setTime(sqlParams[i].getIndex(),
                                sqlParams[i].getValue() == null ? null
                                        : (Time) sqlParams[i].getValue());
                        break;
                    case java.sql.Types.TIMESTAMP:
                        ps.setTimestamp(sqlParams[i].getIndex(), sqlParams[i]
                                .getValue() == null ? null
                                : (Timestamp) sqlParams[i].getValue());
                        break;
                    default:
                        ps.setObject(sqlParams[i].getIndex(),
                                sqlParams[i].getValue());
                        break;
                }
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection connection, Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConnection(connection);
    }

    public enum CommandType {
        Text, Procedure
    }
}
