package com.yyft.blog.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.vo.TQColumn;
import com.yyft.blog.entity.vo.TQOrder;
import com.yyft.blog.entity.vo.TableQuery;
import org.apache.commons.lang3.StringUtils;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/3 12:40
 */
public class QueryConvert {
    public static <T> QueryWrapper<T> convertWrapper(TableQuery tq) {
        QueryWrapper<T> qw = new QueryWrapper<>();
        if (StringUtils.isNotBlank(tq.getSearch().getValue()) && tq.getColumns() != null) {
            qw.and(true, a -> {
                for (TQColumn tqColumn : tq.getColumns()) {
                    if (tqColumn.isSearchable() && StringUtils.isNotBlank(tq.getSearch().getValue())) {
                        a.or().like(StringUtils.isNotBlank(tqColumn.getName()) ? tqColumn.getName() : tqColumn.getData(), tq.getSearch().getValue());
                    }
                }
                return a;
            });
        }
        if (tq.getColumns() != null) {
            if (tq.getOrder() != null && tq.getOrder().size() > 0) {
                TQOrder tqo = tq.getOrder().get(0);
                TQColumn tqc = tq.getColumns().get(tqo.getColumn().intValue());
                String columnName = StringUtils.isNotBlank(tqc.getName()) ? tqc.getName() : tqc.getData();
                if ("asc".equals(tqo.getDir())) {
                    qw.orderByAsc(columnName);
                } else if ("desc".equals(tqo.getDir())) {
                    qw.orderByDesc(columnName);
                }
            }
        }
        return qw;
    }

    public static <T> Page<T> convertPage(TableQuery tq) {
        return new Page<>(tq.getStart() / tq.getLength() + 1, tq.getLength());
    }
}
