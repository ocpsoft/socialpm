package com.ocpsoft.socialpm.domain.project;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.ocpsoft.socialpm.domain.PersistentObject;

/**
 * @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
 * 
 */
@Entity
public class Story extends PersistentObject<Story> 
{
    private static final long serialVersionUID = 1L;
    @ManyToOne
    private Project project;
    private String title;
    
    public Project getProject()
    {
        return project;
    }
    public void setProject(Project project)
    {
        this.project = project;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
