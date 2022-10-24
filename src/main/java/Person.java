import com.google.gson.internal.bind.util.ISO8601Utils;

import java.util.ArrayList;
import java.util.List;

public class Person {
    public static void main(String[] args) {
//     LearnOop newLearnOop = new LearnOop("Nguyễn Trí Diện","Tỉnh Hà Tĩnh",12);
//     LearnOop newLearnOop2 = newLearnOop;
////          newLearnOop2.setName("Đặng Huyền Trang");
////          newLearnOop2.setDiaChi("Hà Tĩnh");
////          newLearnOop2.setAge(12);
////          newLearnOop.showinfo();
        LearnOop[] b = new LearnOop[5];
          b[0] = new LearnOop("","", 12);
//        b[0].setAge(12);
//        b[0].setName("Lê Thị Thương");
        b[0].setDiaChi("Hà Tĩnh");
//       b[0].showinfo();
        System.out.println(b[0].getDiaChi());
    }
}
