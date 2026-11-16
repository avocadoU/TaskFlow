package ru.gorodnichev_ilya.TaskFlow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "templates")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String templateName;
    private String description;
    private Integer usageCount;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Template() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getUsageCount() { return usageCount; }
    public void setUsageCount(Integer usageCount) { this.usageCount = usageCount; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
}