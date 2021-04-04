package com.yyft.blog.tools.listener;

import com.yyft.blog.entity.Label;
import com.yyft.blog.service.LabelService;
import com.yyft.common.utils.mapper.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/3 17:50
 */
@Component
@Slf4j
public class ApplicationStartCacheListener implements ApplicationListener<ContextRefreshedEvent> {

    private LabelService labelService;
    private volatile Map<String, String> labelsMap;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            labelsMap = new ConcurrentHashMap<>();
            List<Label> labels = labelService.findByType("");
            if (labels != null) {
                for (Label l : labels) {
                    labelsMap.put(l.getLabelId().toString(), l.getName());
                }
            }
        } catch (Exception e) {
            log.error("启动缓存失败", e);
        }
    }

    @Autowired
    public void setLabelService(LabelService labelService) {
        this.labelService = labelService;
    }

    public String getLablesCache(String key) {
        if (StringUtils.isNotBlank(key)) {
            StringBuffer sb = new StringBuffer();
            String[] keys = key.substring(1, key.length() - 1).split("\\|\\|");
            for (int i = 0; i < keys.length; i++) {
                if (labelsMap.containsKey(keys[i])) {
                    sb.append(labelsMap.get(keys[i])).append("  ");
                }
            }
            String res = sb.toString();
            sb = null;
            keys = null;
            return res;
        } else {
            return "";
        }
    }


    public void refreshLabels() {
        List<Label> labels = labelService.findByType("");
        if (labels != null) {
            for (Label l : labels) {
                labelsMap.put(l.getLabelId().toString(), l.getName());
            }
        }
    }
}
