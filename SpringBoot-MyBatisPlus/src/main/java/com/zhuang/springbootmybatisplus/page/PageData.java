package com.zhuang.springbootmybatisplus.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class PageData<T> implements Serializable {

    // 总记录数
    private long total = 0;

    // 每页条数
    private long pageSize = 0;

    // 页码
    private long pageNum = 0;

    // 分页数据
    private List<T> data = new ArrayList<>();


    /**
     * @param data  数据集合
     * @param total 总数
     * @param <T>   类型
     * @return 转换后的数据
     */
    public static <T> PageData<T> build(List<T> data, long total) {
        PageData<T> item = new PageData<>();
        item.setData(data);
        item.setTotal(total);
        return item;
    }

    public static <T> PageData<T> build(IPage<T> pageInfo) {
        if (pageInfo == null || pageInfo.getRecords() == null) {
            return build(new ArrayList<>(), 0);
        }
        if (pageInfo.getTotal() == 0) {
            return build(pageInfo.getRecords(), pageInfo.getRecords().size());
        }
        return build(pageInfo.getRecords(), pageInfo.getTotal());
    }
}
