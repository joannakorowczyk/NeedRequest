package pl.lodz.p.it.spjava.e12.nr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
@SequenceGenerator(name = "CategoryIdGen", initialValue = 100)

@NamedQueries({
    @NamedQuery(name = "Category.findByName", query = "SELECT t FROM Category t WHERE t.categoryName = :name")})
@Table(name = "Category")

@Entity
public class Category extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
     @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = "CategoryIdGen")
    private Long id;

    @Version
    private Long version;

    @Column(length = 45, nullable = false, unique = true, updatable = false)
    private String categoryName;

    @Column(nullable = false, updatable = true)
    private int availability;

    @OneToMany(mappedBy = "categoryId", cascade = CascadeType.ALL)
    private List<RequestedNeed> listRequestedNeed = new ArrayList<>();
    
    private List<Category> listAllCategory = new ArrayList<>();

    public List<Category> getListAllCategory() {
        return listAllCategory;
    }

    public void setListAllCategory(List<Category> listAllCategory) {
        this.listAllCategory = listAllCategory;
    }
    
    

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

 @Override
    public Long getId() {
        return id;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.spjava.e12.nr.model.Category[ id=" + id + " ]";
    }

    public List<RequestedNeed> getListRequestedNeed() {
        return listRequestedNeed;
    }

    public void setListRequestedNeed(List<RequestedNeed> listRequestedNeed) {
        this.listRequestedNeed = listRequestedNeed;
    }
    
    @Override
    protected Object getBusinessKey() {
        return categoryName;
    }
  

}
