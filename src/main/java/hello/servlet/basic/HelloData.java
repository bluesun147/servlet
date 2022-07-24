package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter // 자동으로 getter, setter 생성
public class HelloData {
    private String username;
    private int age;

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
}
