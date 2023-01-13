public class products {
    private
    String name, Description,id;
    boolean flag = false;

    public products() {
    }

    public products(String Name, String Description,String id, boolean flag) {
        this.name = Name;
        this.Description = Description;
        this.flag = flag;
        this.id=id;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        description = Description;
    }

    public boolean getflag() {
        return flag;
    }

    public void setflag(boolean Flag) {
        flag = Flag;
    }

}
