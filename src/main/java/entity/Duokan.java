package entity;

/**
 * Created by IDEA
 * User:    tiztm
 * Date:    2016/9/21.
 */
public class Duokan {

    private Integer id;

    private String  name;

    private String url;

    private Integer isread;

    private Integer isdown;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }

    public Integer getIsdown() {
        return isdown;
    }

    public void setIsdown(Integer isdown) {
        this.isdown = isdown;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
