package org.jbpm.ee.camel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.drools.persistence.info.WorkItemInfo;

@Entity
@SequenceGenerator(name="processXCamelSeq", sequenceName="PROCESS_X_CAMEL_SEQ", allocationSize=1)
public class ProcessXCamel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="processXCamelSeq")
	private Long id;
	
	@OneToOne
	private org.drools.persistence.info.WorkItemInfo workItem;
	
    @Version
    @Column(name = "OPTLOCK")
    private int version;

    public int getVersion() {
		return version;
	}
    
    public void setVersion(int version) {
		this.version = version;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public WorkItemInfo getWorkItem() {
		return workItem;
	}
	public void setWorkItem(WorkItemInfo workItem) {
		this.workItem = workItem;
	}

	
}
