/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.edu.sumdu.j2ee.Zhulynska.mdb;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name="Loggs")
public class MessageEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="logId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long logId;
    

	@Column(name="message", length=100)
	private String message;
    
    

    public Long getId() {
        return logId;
    }

    public void setId(Long id) {
        this.logId = id;
    }
    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String m) {
        this.message = m;
    }

    
}
