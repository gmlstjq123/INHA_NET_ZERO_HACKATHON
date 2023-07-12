package com.example.hello_there.board.dto;

import com.example.hello_there.board.BoardType;
import com.example.hello_there.board.comment.Comment;
import com.example.hello_there.board.comment.dto.GetCommentByBoardRes;
import com.example.hello_there.board.photo.dto.GetS3Res;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetBoardDetailRes {
    private Long boardId;
    private BoardType boardType;
    private String createDate; // ex) 2023-07-04
    private String createTime; // ex) 3분 전
    private String nickName;
    private String title;
    private String content;
    private List<GetS3Res> getS3Res;
    private List<GetCommentByBoardRes> getCommentByBoardRes;
}