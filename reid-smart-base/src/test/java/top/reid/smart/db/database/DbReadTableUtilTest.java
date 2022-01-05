package top.reid.smart.db.database;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DbReadTableUtilTest {


    @Test
    void readAllTableNames() {
        List<String> list = DbReadTableUtil.readAllTableNames();
        System.out.println(list);
    }
}
