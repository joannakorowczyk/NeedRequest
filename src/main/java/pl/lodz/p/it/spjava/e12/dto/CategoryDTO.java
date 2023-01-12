package pl.lodz.p.it.spjava.e12.dto;

import java.util.Objects;

public class CategoryDTO {

    private Long id;
    private String categoryName;
    private int availability;

    public CategoryDTO(Long id) {
        this.id = id;
    }

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String categoryName, int availability) {
        this.id = id;
        this.categoryName = categoryName;
        this.availability = availability;
    }

    public Long getId() {
        return id;
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
    public String toString() {
        return categoryName;
    }
    
      @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CategoryDTO other = (CategoryDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
