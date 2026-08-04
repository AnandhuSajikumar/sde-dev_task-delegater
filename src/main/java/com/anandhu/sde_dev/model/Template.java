package com.anandhu.sde_dev.model;

import jakarta.persistence.*;

@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String templateKey;

    @Column(nullable = false)
    private String channel;

    @Column(nullable = false)
    private String label;

    protected Template() {
    }

    public Template(String templateKey, String channel, String label) {
        this.templateKey = templateKey;
        this.channel = channel;
        this.label = label;
    }

    public Long getId() { return id; }
    public String getTemplateKey() { return templateKey; }
    public String getChannel() { return channel; }
    public String getLabel() { return label; }

    public void setTemplateKey(String templateKey) { this.templateKey = templateKey; }
    public void setChannel(String channel) { this.channel = channel; }
    public void setLabel(String label) { this.label = label; }
}
