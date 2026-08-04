package com.anandhu.sde_dev.dto.template;

public class TemplateResponse {
    private Long id;
    private String templateKey;
    private String channel;
    private String label;

    public TemplateResponse(Long id, String templateKey, String channel, String label) {
        this.id = id;
        this.templateKey = templateKey;
        this.channel = channel;
        this.label = label;
    }

    public Long getId() { return id; }
    public String getTemplateKey() { return templateKey; }
    public String getChannel() { return channel; }
    public String getLabel() { return label; }
}
