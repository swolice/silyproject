package comm.page;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import comm.SpringFactory;
import dms.dao.BaseDao;
import exception.PageSqlException;
import com.sun.rowset.CachedRowSetImpl;

public class PageUtil {
        // ͨ��SessionList��÷�ҳ����
        public static List getDataByList(List list, int startRow, int rows) {
                List data = new ArrayList();
                int leng = list.size();
                if ((leng - startRow) < rows) {
                        rows = leng - startRow;
                }
                for (int i = 0; i < rows; i++) {
                        data.add(list.get(startRow + i));
                }
                return data;

        }

        // ͨ��ResultSet��÷�ҳ����
        public static ResultSet getDataByResultSet(ResultSet rs, int startRow,
                        int rows) throws SQLException, PageSqlException {
                startRow++;// ������д�1��ʼ
                CachedRowSet crsi = new CachedRowSetImpl();
                crsi.setPageSize(rows);
                crsi.populate(rs, startRow);
                return crsi;
        }

        // ͨ��ResultSet��÷�ҳ����,ResultSet�Ѿ���ͨ�������ҳ��ȡ�Ľ����
        public static ResultSet getDataByResultSet(ResultSet rs)
                        throws SQLException {
                CachedRowSet crsi = new CachedRowSetImpl();
                crsi.populate(rs);
                return crsi;
        }


        /**
         * ͨ��HQL���������
         *
         * @param hql
         *            ����ΪHQL��ѯ����
         * @return Object
         */
        public static Object findUniqueResult(String hql) {
                BaseDao dao = (BaseDao) SpringFactory.getInstance().getBean("baseDao");
                Object object = dao.findUniqueResult(hql);
                return object;
        }



        /**
         * ͨ��HQL��÷�ҳ����
         *
         * @param hql
         *            ����ΪHQL��ѯ����
         * @param startRow
         *            ��ʼ��
         * @param rows
         *            ��ʾ����
         * @return List
         */
        public static List getDataByHql(String hql, int startRow, int rows) {
                BaseDao dao = (BaseDao) SpringFactory.getInstance().getBean("baseDao");
                List list = dao.find(hql, startRow, rows);
                return list;
        }

        /**
         * ͨ��ԭʼSQL��ѯ,����List���󣬶�����Ԫ��ΪMap���õ����еĶ�����Ҫ��map.get("�ֶ���")��
         * @param sql ʹ��ԭʼsql��sql�е�����select��Ҫ�ƶ��ֶ�����
         *
         * @return List
         */
        public static List getDataBySql(final String sql) {
                BaseDao dao = (BaseDao) SpringFactory.getInstance().getBean("baseDao");
                List list=dao.findBySql(sql);
                return list;
        }



        /**
         * @param args
         */
        public static void main(String[] args) {

        }

}
