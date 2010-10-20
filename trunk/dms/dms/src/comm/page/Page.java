package comm.page;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Page {
    public int cur = 1; // 当前页,默认第一页

    public int sumPages; // 总页数

    public int sumCounts; // 总行数

    public int rows = 10; // 每页记录数，默认每页10条

    public int prePage; // 前一页

    public int nextPage; // 下一页

    public Object data; // 分页数据

    // 不允许不带参数的构造函数

    public Page(int sumCounts) {
        setPage(cur, rows, sumCounts);
    }

    /**
     * @param cur 当前页数
     * @param rows 每页显示条数
     * @param sumCounts 总记录数
     */
    public Page(int cur, int rows, int sumCounts) {
        setPage(cur, rows, sumCounts);
    }

    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * @param cur  当前页数
     * @param rows 每页显示条数
     */
    public Page(String cur, String rows) {
        if (isInt(cur) && Integer.parseInt(cur) > 0)
            setCur(Integer.parseInt(cur));
        if (isInt(rows) && Integer.parseInt(rows) > 0)
            setRows(Integer.parseInt(rows));
    }

    /**
     * 保存Page的所有配置信息
     * @param curPage 当前页
     * @param rows 每页显示条数
     * @param sumCounts 总记录数
     */
    public void setPage(int curPage, int rows, int sumCounts) {
        int sumPages = new Long(Math.round(sumCounts / (rows + 0.0) + 0.49999))
                       .intValue();
        if (curPage > sumPages) {
            curPage = sumPages;
        }
        if (curPage <= 0) {
            curPage = 1;
        }
        int prePage = curPage - 1;
        int nextPage = curPage + 1;
        if (prePage <= 0) {
            prePage = 1;
        }
        if (nextPage > sumPages) {
            nextPage = sumPages;
        }
        if (sumPages == 0)
            sumPages = 1;
        setCur(curPage);
        setRows(rows);
        setSumPages(sumPages);
        setSumCounts(sumCounts);
        setPrePage(prePage);
        setNextPage(nextPage);
    }


    /**
     * 通过HQL得到总记录数
     * @param String 查询hql
     *
     * <BR> 保存总记录数sumCounts
     */
    private void doCountHQL(String hql) {
        try {
            StringBuffer strBuf = new StringBuffer();
            strBuf.append("select count(*)  ");
            strBuf.append(hql);
            strBuf.append(") ");
            Object obj = PageUtil.findUniqueResult(strBuf.toString());
            sumCounts = Integer.parseInt(obj.toString()); // 设置总记录数
//            System.out.println("~~~~~~~~~~~~~~~" + sumCounts +
//                               "~~~~~~~~~~~~~~~~~~~");
            setSumCounts(sumCounts);
        } catch (Exception ex) {

        }
    }

    /**
     * 通过SQL得到总记录数
     * @param String 查询sql
     *
     * <BR> 保存总记录数sumCounts
     */
    private void doCountSQL(String sql) {
        try {
            StringBuffer strBuf = new StringBuffer();
            strBuf.append("select count(*) SUMCOUNT from (");
            strBuf.append(sql);
            strBuf.append(") ");
            List obj = PageUtil.getDataBySql(strBuf.toString());
            sumCounts = Integer.parseInt(((HashMap) obj.get(0)).get("sumcount").
                                         toString()); // 设置总记录数
            System.out.println("~~~~~~~~~~~~~~~" + sumCounts +
                               "~~~~~~~~~~~~~~~~~~~");
            setSumCounts(sumCounts);
        } catch (Exception ex) {

        }
    }

    /**
     *  通过HQL设置分页数据
     *  @param String 查询hql
     *
     *  <BR> 保存结果集date
     *  <BR> 保存总记录数sumCounts
     *  <BR> 保存完整的Page信息
     */
    public void setDataByHql(String hql) {
        doCountHQL(hql);
        List list = PageUtil.getDataByHql(hql, getStartRow(), rows);
        setDate(list);
        setPage(cur, rows, sumCounts);
    }

    /**
     *  通过SQL设置分页数据
     *  @param String 查询sql
     *
     *  <BR> 保存结果集
     *  <BR> 保存总记录数
     *  <BR> 保存完整的Page信息
     */
    public void setDataBySql(String sql) {
        doCountSQL(sql);
        List list = PageUtil.getDataBySql(getRecordQuerySql(sql, getStartRow(),
                getStartRow() + rows));
        setDate(list);
        setPage(cur, rows, sumCounts);
    }

    /**
     *  通过SQL拼装分页SQL
     *  @param String sql    查询sql
     *  @param int startNum  开始行数
     *  @param int endNum    结束行数
     *
     *  @return String 返回拼装好的分页SQL
     */
    public String getRecordQuerySql(String sql, int startNum, int endNum) {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("SELECT * FROM (SELECT my_table.*,rownum my_rownum FROM(");
        strBuf.append(sql);
        strBuf.append(") my_table WHERE rownum <= ");
        strBuf.append(endNum);
        strBuf.append(") WHERE my_rownum > ");
        strBuf.append(startNum);

        System.out.println(strBuf);
        return strBuf.toString();
    }

    /**
     * 获得开始行数
     * @return int 返回开始行
     */
    public int getStartRow() {
        int offset = (cur - 1) * rows;
        if (offset > sumCounts) {
            if (rows > sumCounts)
                offset = 0;
            else
                offset = sumCounts - (rows * (cur - 1));
        }
        return offset;
    }

    // 通过SessionList设置分页数据
    public void setDataByList(List sessionList) {
        List list = PageUtil.getDataByList(sessionList, getStartRow(), rows);
        setDate(list);

    }

    // 通过已经分好页的List设置分页数据
    public void setDataByHasList(List list) {
        setDate(list);
    }

    // 通过ResultSet设置分页数据
    public void setDataByResultSet(ResultSet rs) throws Exception {
        ResultSet crsi = PageUtil.getDataByResultSet(rs, getStartRow(), rows);
        setDate(crsi);
    }

    // 通过ResultSet设置分页数据,ResultSet已经是通过物理分页获取的结果集
    public void setDataByHasResultSet(ResultSet rs) throws SQLException {
        ResultSet crsi = PageUtil.getDataByResultSet(rs);
        setDate(crsi);
    }

    public String toString() {
        String msg = "sumCounts=" + sumCounts + ";sumPages=" + sumPages
                     + ";rows=" + rows + ";curPage=" + cur + ";prePage=" +
                     prePage
                     + ";nextPage=" + nextPage + ";startRow=" + getStartRow()
                     + ";data=" + data.toString();
        return msg;
    }

    public int getCur() {
        return cur;
    }

    public void setCur(int cur) {
        this.cur = cur;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public int getSumCounts() {
        return sumCounts;
    }

    public void setSumCounts(int sumCounts) {
        this.sumCounts = sumCounts;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getSumPages() {
        return sumPages;
    }

    public void setSumPages(int sumPages) {
        this.sumPages = sumPages;
    }

    public Object getData() {
        return data;
    }

    public void setDate(Object data) {
        this.data = data;
    }


}
