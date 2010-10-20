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
        // 通过SessionList获得分页数据
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

        // 通过ResultSet获得分页数据
        public static ResultSet getDataByResultSet(ResultSet rs, int startRow,
                        int rows) throws SQLException, PageSqlException {
                startRow++;// 这里的行从1开始
                CachedRowSet crsi = new CachedRowSetImpl();
                crsi.setPageSize(rows);
                crsi.populate(rs, startRow);
                return crsi;
        }

        // 通过ResultSet获得分页数据,ResultSet已经是通过物理分页获取的结果集
        public static ResultSet getDataByResultSet(ResultSet rs)
                        throws SQLException {
                CachedRowSet crsi = new CachedRowSetImpl();
                crsi.populate(rs);
                return crsi;
        }


        /**
         * 通过HQL获得总条数
         *
         * @param hql
         *            必须为HQL查询语言
         * @return Object
         */
        public static Object findUniqueResult(String hql) {
                BaseDao dao = (BaseDao) SpringFactory.getInstance().getBean("baseDao");
                Object object = dao.findUniqueResult(hql);
                return object;
        }



        /**
         * 通过HQL获得分页数据
         *
         * @param hql
         *            必须为HQL查询语言
         * @param startRow
         *            开始行
         * @param rows
         *            显示行数
         * @return List
         */
        public static List getDataByHql(String hql, int startRow, int rows) {
                BaseDao dao = (BaseDao) SpringFactory.getInstance().getBean("baseDao");
                List list = dao.find(hql, startRow, rows);
                return list;
        }

        /**
         * 通过原始SQL查询,返回List对象，对象中元素为Map，得到其中的对象需要用map.get("字段名")；
         * @param sql 使用原始sql，sql中的所有select都要制定字段名称
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
