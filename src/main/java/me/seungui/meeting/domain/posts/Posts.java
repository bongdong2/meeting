package me.seungui.meeting.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.seungui.meeting.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity
public class Posts extends BaseTimeEntity {

    // PK필드를 나타냄
    @Id
    // @GeneratedValues : PK 생성규칙을 나타냄
    // springboot 2.0부터 IDENTITY옵션을 추가해야 auto_increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 굳이 @Comumn을 추가하지 않아도 컬럼이되지만 기본값 외에 변경할 옵션이 있는 경우 사용한다.
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    // 해당 클래스의 빌더 패턴 클래스를 생성
    // 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
