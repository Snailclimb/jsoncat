package com.github.demo.config;

import com.github.jsoncat.annotation.config.Value;
import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.springmvc.GetMapping;
import com.github.jsoncat.annotation.springmvc.RequestParam;
import com.github.jsoncat.annotation.springmvc.RestController;
import com.github.jsoncat.core.config.ConfigurationManager;

/**
 * @author shuang.kou
 * @createTime 2020年10月19日 13:34:00
 **/
@RestController("/config")
public class ConfigController {
    @Autowired
    private ConfigurationManager configurationManager;

    @Value("project.info")
    private String projectInfo;

    @GetMapping
    public String getConfig(@RequestParam("key") String key) {
        return configurationManager.getString(key);
    }

    @GetMapping("/project-info")
    public String getProjectInfo() {
        return projectInfo;
    }

}
