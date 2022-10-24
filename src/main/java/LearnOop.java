public class LearnOop {
        private String name;
        private String diaChi;
        private int age;
  public LearnOop(String name,String diaChi,int age){
      this.name = name;
      this.diaChi = diaChi;
      this.age = age;
  }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void showinfo(){
        System.out.println(getName());
        System.out.println(getAge());
        System.out.println(getDiaChi());
    }
}

