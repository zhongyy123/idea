package com.yr.mysql;

import java.sql.*;

/**
 * ����mysql��Ⱥ
 * 
 * @author
 * 
 * @ʱ��:2017��6��12������7:47:59
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
	 * �������ݿ�
	 * 
	 * @return conn ���Ӷ���
	 */
	public Connection getCon() {
		Connection conn = null;
		try {
			// ��������
			Class.forName("com.mysql.jdbc.Driver");

			// 1:���Ӽ�Ⱥ�ؼ���loadbalance
			// 2:roundRobinLoadBalanceʹ��roundRobin�㷨,��������һ̨����down���Ժ�,�����Զ������ܷ��ʵ����ݿ�������߳�,�����ԭ�󽫼�������ѹ�����ء�
			// 3:�������ӵ�������sql�ڵ� ����֮ǰһ��Ҫ��Ȩ

			conn = DriverManager.getConnection(
					"jdbc:mysql:loadbalance://192.168.1.178:3306,192.168.1.180:3306/aa?roundRobinLoadBalance=true",
					"root", "123");

			System.out.println("���ӳɹ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * �ر���������
	 * 
	 * @param conn
	 * @param ps
	 * @param st
	 * @param rs
	 */
	public void closeAll(Connection conn, PreparedStatement ps, Statement st, ResultSet rs) {
		try {
			// �����Ӳ����ڿյ�ʱ��ر�
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
	 * �������
	 */
	public void insert() {
		try {
			Connection conn = getCon();
			String sql = "insert into test1(id)values(111);";
			// ͨ�����ݿ����Ӽ���ָ����SQL��䣬Ԥ����sql��һ���ʹ��Ԥ����sql���Ƚϰ�ȫ
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql); // ִ��sql
			ps.executeUpdate();
			// Statement st = (Statement) conn.createStatement();
			// ��Ԥ����sql���Ƽ�ʹ��
			// st.executeUpdate(sql);
			System.out.println("��ӳɹ���");
			closeAll(conn, ps, null, null);// �ر�����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ѯ����
	 */
	protected void select() {
		try {
			Connection conn = getCon();// �������ݿ�
			String sql = "select * from aa";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeQuery();// ִ�в�ѯ
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				System.out.println("id:" + rs.getInt("id"));
				System.out.println("name:" + rs.getString("name"));
			}
			closeAll(conn, ps, null, rs);// �ر�����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ɾ������
	 */
	public void delete() {
		try {
			Connection conn = getCon();
			String sql = "delete from test where id = 110;";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.executeUpdate();// ִ��ɾ��
			System.out.println("ɾ���ɹ���");
			closeAll(conn, ps, null, null);// �ر�����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �޸�����
	 * 
	 * @param
	 */
	public void update() {
		try {
			Connection conn = getCon();
			String sql = "update test set name = ? where id  =?";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, "admin");// ��ֵ
			ps.setInt(2, 111);
			ps.executeUpdate();// ִ���޸�
			System.out.println("�޸ĳɹ�");
			closeAll(conn, ps, null, null);// �ر�����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
