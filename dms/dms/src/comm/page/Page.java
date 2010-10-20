package comm.page;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Page {
    public int cur = 1; // ��ǰҳ,Ĭ�ϵ�һҳ

    public int sumPages; // ��ҳ��

    public int sumCounts; // ������

    public int rows = 10; // ÿҳ��¼����Ĭ��ÿҳ10��

    public int prePage; // ǰһҳ

    public int nextPage; // ��һҳ

    public Object data; // ��ҳ����

    // �������������Ĺ��캯��

    public Page(int sumCounts) {
        setPage(cur, rows, sumCounts);
    }

    /**
     * @param cur ��ǰҳ��
     * @param rows ÿҳ��ʾ����
     * @param sumCounts �ܼ�¼��
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
     * @param cur  ��ǰҳ��
     * @param rows ÿҳ��ʾ����
     */
    public Page(String cur, String rows) {
        if (isInt(cur) && Integer.parseInt(cur) > 0)
            setCur(Integer.parseInt(cur));
        if (isInt(rows) && Integer.parseInt(rows) > 0)
            setRows(Integer.parseInt(rows));
    }

    /**
     * ����Page������������Ϣ
     * @param curPage ��ǰҳ
     * @param rows ÿҳ��ʾ����
     * @param sumCounts �ܼ�¼��
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
     * ͨ��HQL�õ��ܼ�¼��
     * @param String ��ѯhql
     *
     * <BR> �����ܼ�¼��sumCounts
     */
    private void doCountHQL(String hql) {
        try {
            StringBuffer strBuf = new StringBuffer();
            strBuf.append("select count(*)  ");
            strBuf.append(hql);
            strBuf.append(") ");
            Object obj = PageUtil.findUniqueResult(strBuf.toString());
            sumCounts = Integer.parseInt(obj.toString()); // �����ܼ�¼��
//            System.out.println("~~~~~~~~~~~~~~~" + sumCounts +
//                               "~~~~~~~~~~~~~~~~~~~");
            setSumCounts(sumCounts);
        } catch (Exception ex) {

        }
    }

    /**
     * ͨ��SQL�õ��ܼ�¼��
     * @param String ��ѯsql
     *
     * <BR> �����ܼ�¼��sumCounts
     */
    private void doCountSQL(String sql) {
        try {
            StringBuffer strBuf = new StringBuffer();
            strBuf.append("select count(*) SUMCOUNT from (");
            strBuf.append(sql);
            strBuf.append(") ");
            List obj = PageUtil.getDataBySql(strBuf.toString());
            sumCounts = Integer.parseInt(((HashMap) obj.get(0)).get("sumcount").
                                         toString()); // �����ܼ�¼��
            System.out.println("~~~~~~~~~~~~~~~" + sumCounts +
                               "~~~~~~~~~~~~~~~~~~~");
            setSumCounts(sumCounts);
        } catch (Exception ex) {

        }
    }

    /**
     *  ͨ��HQL���÷�ҳ����
     *  @param String ��ѯhql
     *
     *  <BR> ��������date
     *  <BR> �����ܼ�¼��sumCounts
     *  <BR> ����������Page��Ϣ
     */
    public void setDataByHql(String hql) {
        doCountHQL(hql);
        List list = PageUtil.getDataByHql(hql, getStartRow(), rows);
        setDate(list);
        setPage(cur, rows, sumCounts);
    }

    /**
     *  ͨ��SQL���÷�ҳ����
     *  @param String ��ѯsql
     *
     *  <BR> ��������
     *  <BR> �����ܼ�¼��
     *  <BR> ����������Page��Ϣ
     */
    public void setDataBySql(String sql) {
        doCountSQL(sql);
        List list = PageUtil.getDataBySql(getRecordQuerySql(sql, getStartRow(),
                getStartRow() + rows));
        setDate(list);
        setPage(cur, rows, sumCounts);
    }

    /**
     *  ͨ��SQLƴװ��ҳSQL
     *  @param String sql    ��ѯsql
     *  @param int startNum  ��ʼ����
     *  @param int endNum    ��������
     *
     *  @return String ����ƴװ�õķ�ҳSQL
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
     * ��ÿ�ʼ����
     * @return int ���ؿ�ʼ��
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

    // ͨ��SessionList���÷�ҳ����
    public void setDataByList(List sessionList) {
        List list = PageUtil.getDataByList(sessionList, getStartRow(), rows);
        setDate(list);

    }

    // ͨ���Ѿ��ֺ�ҳ��List���÷�ҳ����
    public void setDataByHasList(List list) {
        setDate(list);
    }

    // ͨ��ResultSet���÷�ҳ����
    public void setDataByResultSet(ResultSet rs) throws Exception {
        ResultSet crsi = PageUtil.getDataByResultSet(rs, getStartRow(), rows);
        setDate(crsi);
    }

    // ͨ��ResultSet���÷�ҳ����,ResultSet�Ѿ���ͨ�������ҳ��ȡ�Ľ����
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
