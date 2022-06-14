package com.gabrisal.api.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.*;

@Slf4j
public class ExcelSheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    private int currentCol = -1;
    private int currRowNum = 0;

    // header를 제외한 데이터부분
    private List<Map<String, Object>> rows = new ArrayList<>();
    // cell Info
    private Map<String, Object> cellInfo = new HashMap<String, Object>();
    private List<String> header = Arrays.asList("boardTitle", "boardContent", "regUserId", "tagList");

    public static ExcelSheetHandler readExcel(MultipartFile excelFile) throws Exception {
        ExcelSheetHandler sheetHandler = new ExcelSheetHandler();

        try {
            OPCPackage opcPackage = OPCPackage.open(excelFile.getInputStream());
            // 메모리를 적게 사용하며 sax 형식을 사용할 수 있게 함
            XSSFReader xssfReader = new XSSFReader(opcPackage);

            // 파일의 데이터를 Table 형식으로 읽을 수 있도록 지원
            ReadOnlySharedStringsTable data = new ReadOnlySharedStringsTable(opcPackage);
            // 읽어온 Table 에 적용되어 있는 Style
            StylesTable styles = xssfReader.getStylesTable();

            // 엑셀의 첫번째 sheet 정보만 읽어오기 위해 사용 만약 다중 sheet 처리를 위해서는 반복문 필요
            InputStream inputStream = xssfReader.getSheetsData().next();
            InputSource inputSource = new InputSource(inputStream);

            // XMLHandler 생성
            ContentHandler handler = new XSSFSheetXMLHandler(styles, data, sheetHandler, false);

            // SAX 형식의 XMLReader 생성
            XMLReader parser = XMLHelper.newXMLReader();
            // XMLReader에 재정의하여 구현한 인터페이스 설정
            parser.setContentHandler(handler);
            // 파싱
            parser.parse(inputSource);
            inputStream.close();

        } catch (Exception e) {
            log.error("[ERROR] EXCEL UPLOAD ERROR :::", e.getCause(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return sheetHandler;
    }

    @Override
    public void startRow(int rowNum) {
        // empty 값을 체크하기 위한 초기 셋팅값
        this.currentCol = -1;
        this.currRowNum = rowNum;
        this.cellInfo = new HashMap<String, Object>();
    }

    @Override
    public void endRow(int rowNum) {
        if (rowNum != 0) {
            rows.add(cellInfo);
        }
//        row.clear();
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        int iCol = (new CellReference(cellReference)).getCol();
        currentCol = iCol;
        cellInfo.put(header.get(currentCol), formattedValue);
//        row.add(cellInfo);
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

}
