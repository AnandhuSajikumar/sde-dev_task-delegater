package com.anandhu.sde_dev.dto.template;

import jakarta.validation.constraints.NotBlank;

public class TemplateRequest {
    @NotBlank
    private String templateKey;
    @NotBlank
    private String channel;
    @NotBlank
    private String label;

    public String getTemplateKey() { return templateKey; }
    public void setTemplateKey(String templateKey) { this.templateKey = templateKey; }

    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}
