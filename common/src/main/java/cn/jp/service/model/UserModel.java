package cn.jp.service.model;

public class UserModel {

    private int id;

    private String nickName;

    private String mobile;

    private String email;

    private String account;

    private int gender;

    private int age;

    private UserPasswordModel passwordModel;

    public UserModel(){

    }

    public UserModel(String nick){
        this.nickName = nick;
    }

    public UserModel(String mobile,String pwd){
        this.mobile = mobile;
        this.passwordModel = new UserPasswordModel();
        this.passwordModel.setPassword(pwd);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserPasswordModel getPasswordModel() {
        return passwordModel;
    }

    public void setPasswordModel(UserPasswordModel passwordModel) {
        this.passwordModel = passwordModel;
    }
}
