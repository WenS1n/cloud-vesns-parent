package cn.vesns.eduservice.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExcelTest {

    @Test
    public void writeTest(){
        // 写法1
        String fileName = "F:\\11.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(data());
    }


    @Test
    public void  readTest(){
        String fileName = "F:\\11.xlsx";
        EasyExcel.read(fileName, DemoData.class,new ExcelListener()).sheet().doRead();
    }


    //循环设置要添加的数据，最终封装到list集合中
    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }

}
