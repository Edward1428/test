package carSystem.com.bean;

import java.io.Serializable;

public class BaseBean implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
