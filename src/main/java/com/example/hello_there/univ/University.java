package com.example.hello_there.univ;

import com.example.hello_there.board.Board;
import com.example.hello_there.board.BoardType;
import com.example.hello_there.user.User;
import lombok.*;
import reactor.netty.udp.UdpServer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class University {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long univId; // 대학교의 식별자

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SchoolType schoolType;

    @Column(nullable = false)
    private String univName; // 대학명

    @Column(nullable = false)
    private int userCount; // 해당 대학교의 가입 인원

    @Column(nullable = false)
    private int totalScore; // 해당 대학교의 총점

    @Column(nullable = false)
    private double partRate; // 해당 대학교의 참여율

    @OneToMany(mappedBy = "univ", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();
}
