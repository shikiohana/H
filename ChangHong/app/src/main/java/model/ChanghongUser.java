package model;

/**
 * Created by Administrator on 2016/7/15.
 */
public class ChanghongUser {
    /*  public string MTel { get; set; }//手机
      public string Gender { get; set; }//性别 值为：男，女
      public string CNName { get; set; }//姓名
      public string openID { get; set; }//微信号
      public string pwd { get; set; }//密码*/
    public String mTel;
    public String gender;
    public String cnName;
    public String openId;
    public String pwd;

    public String getmTel() {
        return mTel;
    }

    public void setmTel(String mTel) {
        this.mTel = mTel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
