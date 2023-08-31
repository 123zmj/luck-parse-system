/*
package com.luck.parse.util;


import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

@Component
@Log4j2
public class HBaseTemplate {
    private Configuration configuration; // Hadoop配置对象，用于创建HBase配置
    private Connection connection; // HBase连接对象，用于与HBase建立连接


    */
/**
     * 如果需要就可以去写一个时加载的配置类读取配置中的数据
     * *//*

    @PostConstruct
    public void init() throws IOException {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "117.72.8.62");
        configuration.setInt("hbase.zookeeper.property.clientPort", 2181);
        // 等等其他HBase的配置设置
        connection = ConnectionFactory.createConnection(configuration);
    }
    */
/**
     * 插入数据
     * 注意：如果向一个不存在的表中添加数据的时候会直接新建这个表
     * 所以在向有的表中进行添加数据的时候要确定表名是否存在
     * *//*

//    public void put(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException {
//        // 对指定表执行插入数据操作
//        Table table = connection.getTable(TableName.valueOf(tableName)); // 获取指定表的Table对象
//        Put put = new Put(Bytes.toBytes(rowKey)); // 创建Put对象，设置行键
//        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value)); // 设置列族、列和值
//        table.put(put); // 执行插入操作
//        table.close(); // 关闭表连接
//    }
    public void put(String tableName, String rowKey, String columnFamily, String column, String value)  {
        // 对指定表执行插入数据操作
        Table table = null; // 获取指定表的Table对象
        try {
            table = connection.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            throw new RuntimeException("连接"+tableName+"表出现异常"+e);
        }
        Put put = new Put(Bytes.toBytes(rowKey)); // 创建Put对象，设置行键
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value)); // 设置列族、列和值
        try {
            table.put(put); // 执行插入操作
        } catch (IOException e) {
            try {
                log.info("新建表操作"+tableName);
                createTableIfNotExists(tableName, columnFamily);
                this.put(tableName,rowKey,columnFamily,column,value);
                //使用递归调用方法
                return;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            table.close(); // 关闭表连接
        } catch (IOException e) {
            throw new RuntimeException("关闭表连接出现错误"+e);
        }
    }
    */
/**
     * 新建表操作
     * *//*

    private void createTableIfNotExists(String tableName, String columnFamily) throws IOException {
        try (Admin admin = connection.getAdmin()) {
            TableName hbaseTable = TableName.valueOf(tableName);

            if (!admin.tableExists(hbaseTable)) {
                HTableDescriptor tableDescriptor = new HTableDescriptor(hbaseTable);
                tableDescriptor.addFamily(new HColumnDescriptor(Bytes.toBytes(columnFamily)));

                admin.createTable(tableDescriptor);
            }
        }
    }
    */
/**
     * 查找获取
     * *//*

    public String get(String tableName, String rowKey, String columnFamily, String column) {
        // 对指定表执行查找数据操作
        Table table = null; // 获取指定表的Table对象
        try {
            table = connection.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            throw new RuntimeException("连接表出现异常"+e);
        }
        Get get = new Get(Bytes.toBytes(rowKey)); // 创建Get对象，设置行键
        Result result = null; // 执行查找操作，获取Result对象
        try {
            result = table.get(get);
        } catch (IOException e) {
            throw new RuntimeException("获取返回值出现异常"+e);
        }
        byte[] valueBytes = result.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(column)); // 获取指定列的值
        try {
            table.close(); // 关闭表连接
        } catch (IOException e) {
            throw new RuntimeException("关闭连接出现问题"+e);
        }
        return Bytes.toString(valueBytes); // 将值转换为字符串并返回
    }
    */
/**
     * 删除一条数据
     * *//*

    public void delete(String tableName, String rowKey) throws IOException {
        // 对指定表执行删除数据操作
        Table table = connection.getTable(TableName.valueOf(tableName)); // 获取指定表的Table对象
        Delete delete = new Delete(Bytes.toBytes(rowKey)); // 创建Delete对象，设置行键
        table.delete(delete); // 执行删除操作
        table.close(); // 关闭表连接
    }
    */
/**
     * 删除表
     * *//*

    public void deleteTable(String tableName) throws IOException {
        Admin admin = connection.getAdmin(); // 获取 Admin 对象
        TableName table = TableName.valueOf(tableName); // 获取表名
        if (admin.tableExists(table)) { // 检查表是否存在
            admin.disableTable(table); // 禁用表
            admin.deleteTable(table); // 删除表
            System.out.println("成功删除表: " + tableName);
        } else {
            System.out.println("表 " + tableName + " 不存在！");
        }
        admin.close(); // 关闭 Admin 连接
    }


    // 其他操作，如 scan 方法等

    // 关闭连接的方法
    public void close() throws IOException {
        connection.close(); // 关闭HBase连接
    }

    */
/**
     * 条件查询方法，根据指定的列族、列和值执行查询操作
     *
     * @param tableName    表名
     * @param columnFamily 列族名
     * @param column       列名
     * @param value        值
     * @return 匹配的行数据列表
     * @throws IOException 发生 IO 错误时抛出异常
     *//*

    public List<String> findbyConditions(String tableName, String columnFamily, String column, String value) throws IOException {
        List<String> results = new ArrayList<>(); // 创建结果列表
        Table table = connection.getTable(TableName.valueOf(tableName)); // 获取指定表的 Table 对象
        Filter filter = (Filter) new SingleColumnValueFilter(Bytes.toBytes(columnFamily), // 创建单列值过滤器
                Bytes.toBytes(column), CompareFilter.CompareOp.EQUAL, new SubstringComparator(value));
        Scan scan = new Scan(); // 创建扫描对象
        scan.setFilter((org.apache.hadoop.hbase.filter.Filter) filter); // 设置扫描过滤器
        ResultScanner scanner = table.getScanner(scan); // 获取结果扫描器
        for (Result result : scanner) {
            StringBuilder row = new StringBuilder(); // 创建字符串构建器
            row.append(Bytes.toString(result.getRow())).append(": "); // 拼接行键
            for (Cell cell : result.listCells()) {
                String family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()); // 获取列族
                String qualifier = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                        cell.getQualifierLength()); // 获取列名
                String cellValue = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()); // 获取值
                row.append(family).append(":").append(qualifier).append("=").append(cellValue).append(", "); // 拼接行数据
            }
            results.add(row.toString()); // 将行数据添加到结果列表
        }
        table.close(); // 关闭表连接
        return results; // 返回结果列表
    }

    */
/**
     * 全表查询方法，返回表中所有行的数据列表
     *
     * @param tableName 表名
     * @return 所有行的数据列表
     * @throws IOException 发生 IO 错误时抛出异常
     *//*

    public List<String> allTheTable(String tableName) throws IOException {
        List<String> rows = new ArrayList<>(); // 创建行列表
        Table table = connection.getTable(TableName.valueOf(tableName)); // 获取指定表的 Table 对象
        Scan scan = new Scan(); // 创建扫描对象
        ResultScanner scanner = table.getScanner(scan); // 获取结果扫描器
        for (Result result : scanner) {
            StringBuilder row = new StringBuilder(); // 创建字符串构建器
            row.append(Bytes.toString(result.getRow())).append(": "); // 拼接行键
            for (Cell cell : result.listCells()) {
                String family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()); // 获取列族
                String qualifier = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()); // 获取列名
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()); // 获取值
                row.append(family).append(":").append(qualifier).append("=").append(value).append(", "); // 拼接行数据
            }
            rows.add(row.toString()); // 将行数据添加到行列表
        }
        table.close(); // 关闭表连接
        return rows; // 返回行列表
    }

}
*/
