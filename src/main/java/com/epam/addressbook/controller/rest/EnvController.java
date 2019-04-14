package com.epam.addressbook.controller.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class EnvController {
    private final String port;
    private final String memoryLimit;
    private final String cfInstanceIndex;
    private final String cfInstanceAddress;

    public EnvController(@Value("${PORT:NOT SET}") String port,
                         @Value("${MEMORY_LIMIT:NOT SET}") String memoryLimit,
                         @Value("${CF_INSTANCE_INDEX:NOT SET}") String cfInstanceIndex,
                         @Value("${CF_INSTANCE_ADDR:NOT SET}") String cfInstanceAddress) {
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = cfInstanceIndex;
        this.cfInstanceAddress = cfInstanceAddress;
    }
    @GetMapping("/env")
    public Map<String, String> getEnv() {
        TreeMap<String, String> envVariables = new TreeMap<>(System.getenv());
        envVariables.put("PORT", port);
        envVariables.put("MEMORY_LIMIT", memoryLimit);
        envVariables.put("CF_INSTANCE_INDEX", cfInstanceIndex);
        envVariables.put("CF_INSTANCE_ADDR", cfInstanceAddress);
        return envVariables;
    }
}
