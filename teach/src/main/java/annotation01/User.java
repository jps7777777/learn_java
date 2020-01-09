package annotation01;

public class User {

    @TestTable("和")
    private String table;

    @TestColumn(value = "天")
    private String column;




    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }


    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
