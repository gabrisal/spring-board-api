package com.gabrisal.api.board.service;

import com.gabrisal.api.board.dto.AddBoardIn;
import com.gabrisal.api.board.dto.SearchBoardIn;
import com.gabrisal.api.board.dto.SearchBoardOut;
import com.gabrisal.api.board.model.Board;
import com.gabrisal.api.board.model.BoardTag;
import com.gabrisal.api.board.model.Tag;
import com.gabrisal.api.board.repository.BoardRepository;
import com.gabrisal.api.common.util.MaskingUtil;
import com.gabrisal.api.mail.service.MailService;
import com.gabrisal.api.mail.vo.SendMailInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository repository;
    private final MailService mailService;
    private final String RECEIVER_MAIL = "upskilling@bbubbush.com";
    @Value("${file.upload.path}")
    private String UPLOAD_FILE_PATH;

    @Transactional(readOnly = true)
    public SearchBoardOut getBoardById(int boardId) {
        SearchBoardIn in = SearchBoardIn.builder().boardId(boardId).build();
        return repository.selectBoardOne(in);
    }

    @Transactional(readOnly = true)
    public List<SearchBoardOut> getBoardList() {
        // XXX : map 은 왜 안될까
        List<SearchBoardOut> boardList = repository.selectBoardList();
        boardList.forEach(v -> v.setWriter(MaskingUtil.getMaskedName(v.getWriter())));
        return boardList;
    }

    private int insertBoard(Board board) {
        return repository.insertBoard(board);
    }

    private int updateBoard(Board board) {
        return repository.updateBoard(board);
    }

    private int deleteBoardById(int boardId) {
        return repository.deleteBoardById(boardId);
    }

    @Transactional(readOnly = true)
    public Tag selectTagByName(Tag tag) {
        return repository.selectTagByName(tag);
    }

    private int insertTag(Tag tag) {
        return repository.insertTag(tag);
    }

    private int updateTag(Tag tag) {
        return repository.updateTag(tag);
    }

    private int insertBoardTagRel(BoardTag boardTag) {
        return repository.insertBoardTagRel(boardTag);
    }

    private int deleteBoardTagRelByBoardId(BoardTag boardTag) {
        return repository.deleteBoardTagRelByBoardId(boardTag);
    }

    public int saveBoard(AddBoardIn in) {
        Board board = Board.builder()
                    .boardTitle(in.getBoardTitle())
                    .boardContent(in.getBoardContent())
                    .frstRegUserId(in.getRegUserId())
                    .lastUpdUserId(in.getRegUserId())
                    .build();
        int result = insertBoard(board);
        saveHashTagList(in, board);
        return result;
    }

    public int modifyBoard(AddBoardIn in) {
        // TODO: 유효한 게시글이 아닐 경우 처리
        Board board = Board.builder()
                .boardId(in.getBoardId())
                .boardTitle(in.getBoardTitle())
                .boardContent(in.getBoardContent())
                .frstRegUserId(in.getRegUserId())
                .lastUpdUserId(in.getRegUserId())
                .build();
        int result = updateBoard(board);

        BoardTag boardTag = BoardTag.builder()
                .boardId(board.getBoardId())
                .build();
        deleteBoardTagRelByBoardId(boardTag);

        // XXX: 해시태그를 참조하는 게시글이 사라지면 해시태그도 지워야하지않을까?

        saveHashTagList(in, board);

        return result;
    }

    private void saveHashTagList(AddBoardIn in, Board board) {
        if (!CollectionUtils.isEmpty(in.getTagList())) {
            for (Tag tag : in.getTagList()) {
                Tag existTag = selectTagByName(tag);

                // XXX: 태그에 시스템 컬럼이 필요할까?
                tag.setFrstRegUserId(in.getRegUserId());
                tag.setLastUpdUserId(in.getRegUserId());
                if (existTag != null) {
                    tag.setTagId(existTag.getTagId());
                    updateTag(tag);
                } else {
                    insertTag(tag);
                }

                BoardTag boardTag = BoardTag.builder()
                        .boardId(board.getBoardId())
                        .tagId(tag.getTagId())
                        .frstRegUserId(in.getRegUserId())
                        .lastUpdUserId(in.getRegUserId())
                        .build();
                insertBoardTagRel(boardTag);
            }
        }
    }

    public int deleteBoard(SearchBoardIn board) {
        mailService.sendMail(createMailInfoForBoard(board));
        int result = deleteBoardById(board.getBoardId());
        BoardTag boardTag = BoardTag.builder()
                .boardId(board.getBoardId())
                .build();
        deleteBoardTagRelByBoardId(boardTag);
        return result;
    }

    /**
     * 게시글 정보 이메일 발송 내용 생성
     *
     * 게시글이 삭제되는 경우, 삭제될 게시글 정보를 이메일로 발송하기위해 메일 세팅 정보를 생성한다.
     * @param board
     * @return SendMailInfo
     */
    private SendMailInfo createMailInfoForBoard(SearchBoardIn board) {
        // TODO: 게시글 정보가 존재하지않는 경우 예외처리
        SearchBoardOut boardInfo = getBoardById(board.getBoardId());
        // 메일 수신자
        // TODO: 관리자 이메일 조회 기능 추가
        List<String> receiverMailList = new ArrayList<>();
        receiverMailList.add(RECEIVER_MAIL);
        // 메일 제목
        StringBuilder subject = new StringBuilder();
        subject.append("[게시글 삭제]\"");
        subject.append(boardInfo.getBoardTitle());
        subject.append("\"이(가) 삭제 되었습니다.");
        // 메일 내용
        DateTime dateTime = new DateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder content = new StringBuilder();
        content.append(dateTime.toString(dateTimeFormatter));
        content.append(" 에 해당 게시글이 삭제되었습니다.");
        return SendMailInfo.builder()
                            .receiverMailList(receiverMailList)
                            .subject(subject.toString())
                            .content(content.toString())
                            .build();
    }

    /**
     * 엑셀 업로드로 게시글 등록
     *
     * 게시글 등록에 실패한 경우, 관리자에게 이메일을 보낸다.
     * @param excelFile
     * @return count
     */
    public int saveBoardByUploadExcel(MultipartFile excelFile) throws Exception {
        int successCount = 0;
        int failCount = 0;
//        String filePath = UPLOAD_FILE_PATH + "//" + excelFile.getOriginalFilename();
        OPCPackage opcPackage = OPCPackage.open(excelFile.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int i=1; i<sheet.getLastRowNum() + 1; i++) {
            try {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = null;

                if (row == null)
                    continue;

                // 엑셀 데이터로 게시글 정보 세팅
                // XXX: 이게 맞냐..?
                AddBoardIn boardIn = new AddBoardIn();
                cell = row.getCell(0);
                if (cell != null) {
                    boardIn.setBoardTitle(cell.getStringCellValue());
                }
                cell = row.getCell(1);
                if (cell != null) {
                    boardIn.setBoardContent(cell.getStringCellValue());
                }
                cell = row.getCell(2);
                if (cell != null) {
                    boardIn.setRegUserId(cell.getStringCellValue());
                }
                cell = row.getCell(3);
                if (cell != null) {
                    String tags = row.getCell(3).getStringCellValue();
                    List<Tag> tagList = new ArrayList<>();
                    if (!StringUtils.isEmpty(row.getCell(3).getStringCellValue())) {
                        for (String tag: tags.split(",")){
                            Tag tagInfo = Tag.builder()
                                    .tagName(tag)
                                    .build();
                            tagList.add(tagInfo);
                        }
                    }
                    boardIn.setTagList(tagList);
                }
                // 게시판 정보 저장
                // TODO: 성공/실패 건 처리
                successCount += saveBoard(boardIn);
            } catch (Exception ex) {
                failCount++;
            }
        }

        return successCount;
    }


    /**
     * 엑셀 다운로드
     *
     * @return excelFile
     */
    public void getBoadListByExcel(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = null;
        XSSFCell cell = null;
        int rowNum = 0;

        // 엑셀 헤더
        List<String> header = Arrays.asList("게시글ID", "제목", "내용", "작성자", "태그");
        row = sheet.createRow(rowNum++);
        for (int i=0; i<header.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(header.get(i));
        }

        // 엑셀 다운로드 할 데이터
        List<SearchBoardOut> boardList = getBoardList();
        for (int i=0; i<boardList.size(); i++) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(boardList.get(i).getBoardId());
            cell = row.createCell(1);
            cell.setCellValue(boardList.get(i).getBoardTitle());
            cell = row.createCell(2);
            cell.setCellValue(boardList.get(i).getBoardContent());
            cell = row.createCell(3);
            cell.setCellValue(boardList.get(i).getWriter());
            cell = row.createCell(4);
            cell.setCellValue(boardList.get(i).getTags());
        }

        String fileName = "게시글목록조회";
        String outputFileName = new String(fileName.getBytes("KSC5601"), "8859_1");
        response.setHeader("Content-Disposition", "attachment;filename=" + outputFileName + ".xlsx");
        response.setContentType("ms-vnd/excel");
        workbook.write(response.getOutputStream());
        response.getOutputStream().close();
    }
}
