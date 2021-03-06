/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.documentexamination.model.dto.base;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Базовый класс для сущностей БД, у которых из основных полей толдько имя
 * @author VVolgina
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity implements Serializable{
     //<editor-fold defaultstate="collapsed" desc="поля">
    
    //имя
    @Column(name = "NAME", length = 100)
    @NotNull(message = "{valid.exists}")
    @Size(min = 1,max=100, message = "{valid.size}")
    private String name;
    
    //</editor-fold>
    
     //<editor-fold defaultstate="collapsed" desc="get-set">
    
    /**
     * возвращает название 
     *
     * @return название
     */
    public String getName() {
        return name;
    }
    
    /**
     * назначает название 
     *
     * @param name название 
     */
    public void setName(String name) {
        this.name = name;
    }

    //</editor-fold>

    public NamedEntity(long id) {
        super(id);
    }

    public NamedEntity() {
    }
    
    
    
    @Override
    public String toString() {
        return super.toString()+"name=" + name + ' ';
    }
}
