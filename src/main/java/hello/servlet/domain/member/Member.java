package hello.servlet.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private Long id;
    private String username;
    private int age;

    public Member() {

    }

    public Member(String name, int age) { // 우클릭 generate constructor
        this.username = name;
        this.age = age;
    }
}
