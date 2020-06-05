package kg.geektech.taskapprestored.models;

public class User {
    private String name, avatar ;
    private int age;

    public User() {
    }

    public User (String name, String avatar, int age) {
        if (name.trim().equals("")){
            this.name = "no name";
        }
        this.name = name;
        this.avatar = avatar;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
