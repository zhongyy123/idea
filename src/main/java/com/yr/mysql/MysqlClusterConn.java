package com.yr.mysql;

import java.sql.*;

/**
 * 连接mysql集群
 * 
 * @author
 * 
 * @时间:2017年6月12日下午7:47:59
 */
public class MysqlClusterConn {
	public static void main(String[] args) {
		MysqlClusterConn mysqlClusterConn = new MysqlClusterConn();
		mysqlClusterConn.getCon();
		// mysqlClusterConn.insert();
		// mysqlClusterConn.delete();
		// mysqlClusterConn.update();
		mysqlClusterConn.select();
	}

	/**
	 * 链接数据库
	 * 
	 * @return conn 连接对象
	 */
	public Connection getCon() {
		Connection conn = null;
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 1:连接集群关键字loadbalance
			// 2:roundRobinLoadBalance使用roundRobin算法,若是其中一台机器down掉以后,将会自动将不能访问的数据库服务器踢除,如果还原后将继续进行压力分载。
			// 3:这里连接的是两个sql节点 连接之前一定要授权

			conn = DriverManager.getConnection(
					"jdbc:mysql:loadbalance://192.168.1.178:3306,192.168.1.180:3306/aa?roundRobinLoadBalance=true",
					"root", "123");

			System.out.println("连接成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭所有连接
	 * 
	 * @param conn
	 * @param ps
	 * @param st
	 * @param rs
	 */
	public void closeAll(Connection conn, PreparedStatement ps, Statement st, ResultSet rs) {
		try {
			// 当连接不等于空的时候关闭
			if (null != conn) {
				conn.close();
			}
			if (null != ps) {
				ps.close();
			}
			if (null != st) {
				st.close();
			}
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加数据
	 */
	public void insert() {
		try {
			Connection conn = getCon();
			String sql = "insert into test1(id)values(111);";
			// 通过数据库连接加载指定的SQL语句，预编译sql，一般会使用预编译sql，比较安全
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql); // 执行sql
			ps.executeUpdate();
			// Statement st = (Statement) conn.createStatement();
			// 非预编译sql不推荐使用
			// st.executeUpdate(sql);
			System.out.println("添加成功。");
			closeAll(conn, ps, null, null);// 关闭连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询数据
	 */
	protected void select() {
		try {
			Connection conn = getCon();// 连接数据库
			String sql = "select * from aa";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeQuery();// 执行查询
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				System.out.println("id:" + rs.getInt("id"));
				System.out.println("name:" + rs.getString("name"));
			}
			closeAll(conn, ps, null, rs);// 关闭连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除数据
	 */
	public void delete() {
		try {
			Connection conn = getCon();
			String sql = "delete from test where id = 110;";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.executeUpdate();// 执行删除
			System.out.println("删除成功。");
			closeAll(conn, ps, null, null);// 关闭连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改数据
	 * 
	 * @param
	 */
	public void update() {
		try {
			Connection conn = getCon();
			String sql = "update test set name = ? where id  =?";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, "admin");// 设值
			ps.setInt(2, 111);
			ps.executeUpdate();// 执行修改
			System.out.println("修改成功");
			closeAll(conn, ps, null, null);// 关闭连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
