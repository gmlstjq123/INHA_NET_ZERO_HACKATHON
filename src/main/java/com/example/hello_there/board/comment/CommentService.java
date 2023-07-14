//package com.example.hello_there.board.comment;
//
//import com.example.hello_there.board.Board;
//import com.example.hello_there.board.BoardRepository;
//import com.example.hello_there.board.BoardType;
//import com.example.hello_there.board.comment.dto.*;
//import com.example.hello_there.board.dto.DeleteBoardReq;
//import com.example.hello_there.board.dto.PatchBoardReq;
//import com.example.hello_there.board.photo.PostPhoto;
//import com.example.hello_there.board.photo.dto.GetS3Res;
//import com.example.hello_there.exception.BaseException;
//import com.example.hello_there.exception.BaseResponseStatus;
//import com.example.hello_there.user.User;
//import com.example.hello_there.user.UserRepository;
//import com.example.hello_there.utils.UtilService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import static com.example.hello_there.exception.BaseResponseStatus.*;
//
//@Service
//@RequiredArgsConstructor
//public class CommentService {
//    private final UserRepository userRepository;
//    private final BoardRepository boardRepository;
//    private final CommentRepository commentRepository;
//    private final UtilService utilService;
//
//    /** 댓글 작성하기 **/
//    public PostCommentRes addComment(Long userId, PostCommentReq postCommentReq) throws BaseException {
//        User user = utilService.findByUserIdWithValidation(userId);
//        Board board = utilService.findByBoardIdWithValidation(postCommentReq.getBoardId());
//        Comment comment;
//        comment = Comment.builder()
//                .nickName(user.getNickName())
//                .reply(postCommentReq.getReply())
//                .user(user)
//                .board(board)
//                .build();
//        commentRepository.save(comment);
//        return new PostCommentRes(user.getNickName(), postCommentReq.getReply());
//    }
//
//    /** 같은 게시글에 작성한 댓글은 한번에 모아서 출력 **/
//    public List<GetCommentRes> getComments(Long userId) throws BaseException {
//        try {
//            User user = utilService.findByUserIdWithValidation(userId);
//            List<Comment> comments = commentRepository.findCommentsByUserId(userId);
//            Map<Long, List<Comment>> commentsByBoard = comments.stream()
//                    .collect(Collectors.groupingBy(comment -> comment.getBoard().getBoardId()));
//
//            List<GetCommentRes> getCommentRes = new ArrayList<>();
//            for (Map.Entry<Long, List<Comment>> entry : commentsByBoard.entrySet()) {
//                Long boardId = entry.getKey();
//                Board board = utilService.findByBoardIdWithValidation(boardId);
//                BoardType boardType = board.getBoardType();
//                String title = board.getTitle(); // boardId에 해당하는 게시물의 제목을 가져오는 메서드를 호출
//                String nickName = user.getNickName();
//                List<String> replies = entry.getValue().stream()
//                        .map(Comment::getReply)
//                        .collect(Collectors.toList());
//                getCommentRes.add(new GetCommentRes(boardType, title, nickName, replies));
//            }
//
//            return getCommentRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    /** 게시글에 달린 댓글 전체 조회 **/
//    public List<GetCommentByBoardRes> getCommentsByBoard(Long boardId) {
//            List<Comment> comments = commentRepository.findCommentsByBoardId(boardId);
//            List<GetCommentByBoardRes> getCommentByBoardRes = new ArrayList<>();
//
//            // createDate 필드를 기준으로 정렬
//            comments.sort(Comparator.comparing(Comment::getCreateDate));
//
//            for (Comment comment : comments) {
//                String nickName = comment.getNickName();
//                String imgUrl = null;
//                String fileName = null;
//                if (comment.getUser().getProfile() != null) {
//                    imgUrl = comment.getUser().getProfile().getProfileUrl();
//                    fileName = comment.getUser().getProfile().getProfileFileName();
//                }
//                String reply = comment.getReply();
//                LocalDateTime createDate = comment.getCreateDate();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
//                String formattedDate = createDate.format(formatter);
//                getCommentByBoardRes.add(new GetCommentByBoardRes(nickName, imgUrl,
//                        fileName, reply, formattedDate));
//            }
//            return getCommentByBoardRes;
//    }
//
//    /** 댓글 삭제 **/
//    @Transactional
//    public String deleteComment(DeleteCommentReq deleteCommentReq) throws BaseException {
//        Comment comment = utilService.findByCommentIdWithValidation(deleteCommentReq.getCommentId());
//        User writer = comment.getUser();
//        User visitor = utilService.findByUserIdWithValidation(deleteCommentReq.getUserId());
//        if(writer.getId() == visitor.getId()) {
//            // 대댓글 기능을 구현하여 댓글에 일대다 매핑을 시킬 경우 대댓글도 같이 삭제하거나
//            // 대댓글 때문에 삭제할 수 없다는 예외를 호출해야 함.
//
//            // 게시글을 삭제하는 명령
//            commentRepository.deleteCommentByCommentId(comment.getCommentId());
//            String result = "요청하신 댓글에 대한 삭제가 완료되었습니다.";
//            return result;
//        }
//        else {
//            throw new BaseException(MEMBER_WITHOUT_PERMISSION);
//        }
//    }
//
//    /** 댓글 수정 **/
//    @Transactional
//    public String modifyComment(Long userId, PatchCommentReq patchCommentReq) throws BaseException {
//        try {
//            Long commentId = patchCommentReq.getCommentId();
//            Comment comment = utilService.findByCommentIdWithValidation(commentId);
//            User writer = comment.getUser();
//            User visitor = utilService.findByUserIdWithValidation(userId);
//            if(writer.getId() == visitor.getId()){
//                comment.updateComment(patchCommentReq.getReply());
//                return "댓글 수정이 완료되었습니다.";
//            }
//            else {
//                throw new BaseException(MEMBER_WITHOUT_PERMISSION);
//            }
//        } catch(BaseException exception) {
//            throw new BaseException(exception.getStatus());
//        }
//    }
//}