package top.reid.smart.base.db.database;

import org.junit.Test;

import java.util.List;

public class DbReadTableUtilTest {


    @Test
    public void readAllTableNames() {
        List<String> list = DbReadTableUtil.readAllTableNames();
        System.out.println(list);
    }
}
