package de.onestydirect.openiban.data.loader.services;

import de.onestydirect.openiban.data.loader.model.BankData;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@PropertySource("classpath:excel.properties")
@Service
@Slf4j
public class ExcelLoaderServiceImpl implements ExcelLoaderService {

    private ResourceLoader resourceLoader;

    @Value("${excel.file.path}")
    private String pathToExcelFile;

    @Value("${excel.file.delete}")
    private boolean deleteExcelFileAfterRead;

    @Value("${excel.list.distinct}")
    private boolean distinctList;

    @Value("${excel.list.hack}")
    private boolean hackSpecialBic;

    @Autowired
    public ExcelLoaderServiceImpl(final ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Reads a excel file specified in the properties and returns a list of BankData distinct by bankcode. Also a special BIC
     * is changed cause this BIC seems to be not correct in the past excel files provided by the Bundesbank.
     *
     * @return List<BankData>
     */
    @Override
    public List<BankData> getAllBankDataFromExcelFile() {
        List<BankData> bankDataList = new ArrayList<>();
        final Optional<Workbook> workbook = getWorkbook();
        workbook.ifPresent(wb -> {
            log.info("Bankdata read.");
            Iterator<Sheet> sheetIterator = wb.sheetIterator();
            if (sheetIterator.hasNext()) {
                DataFormatter dataFormatter = new DataFormatter();
                final Sheet sheet = workbook.get().getSheetAt(0);
                final Iterator<Row> rowIterator = sheet.rowIterator();
                int i = 0;
                while (rowIterator.hasNext()) {
                    final Row row = rowIterator.next();
                    if (i > 0) {
                        Iterator<Cell> cellIterator = row.cellIterator();
                        final BankData bankData = new BankData();
                        bankData.setSource("1");
                        final Date date = new Date();
                        bankData.setCreated(new Timestamp(date.getTime()));
                        bankData.setCountry("DE");
                        int cellCounter = 0;
                        while (cellIterator.hasNext()) {
                            final Cell cell = cellIterator.next();
                            final String cellValue = dataFormatter.formatCellValue(cell);
                            switch (cellCounter) {
                            case 0:
                                bankData.setBankcode(cellValue);
                                break;
                            case 2:
                                bankData.setName(cellValue);
                                break;
                            case 3:
                                bankData.setZip(cellValue);
                                break;
                            case 4:
                                bankData.setCity(cellValue);
                                break;
                            case 7:
                                bankData.setBic(cellValue);
                                break;
                            case 8:
                                bankData.setAlgorithm(cellValue);
                                break;
                            default:
                                break;
                            }
                            cellCounter++;
                        }
                        bankDataList.add(bankData);
                    }
                    i++;
                }
            }
        });
        if (distinctList && hackSpecialBic) {
            List<BankData> returnList = filterBankDataList(bankDataList);
            log.info("Bankdata list filtered.");
            return hackSpecialBics(returnList);
        }
        if (distinctList) {
            log.info("Bankdata list filtered.");
            return filterBankDataList(bankDataList);
        }
        if (hackSpecialBic) {
            return hackSpecialBics(bankDataList);
        }
        return bankDataList;
    }

    private Optional<Workbook> getWorkbook() {
        try {
            File file = resourceLoader.getResource(pathToExcelFile).getFile();
            Workbook workbook = WorkbookFactory.create(file);
            if (file.exists() && deleteExcelFileAfterRead) {
                if (file.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Fail to delete file");
                }
            }
            return Optional.of(workbook);
        } catch (FileNotFoundException e) {
            log.info(e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            log.error(e.toString());
            return Optional.empty();
        }
    }

    private List<BankData> filterBankDataList(final List<BankData> bankDataList) {
        return bankDataList.stream().filter(distinctByBankCode(BankData::getBankcode)).collect(Collectors.toList());
    }

    private static <T> Predicate<T> distinctByBankCode(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private List<BankData> hackSpecialBics(final List<BankData> bankDataList) {
        bankDataList.forEach(entry -> {
            if (entry.getBic().equals("COBADEBB120") && entry.getBankcode().equals("12040000")) {
                entry.setBic("COBADEFFXXX");
            }
        });
        log.info("Bankdata list hacked.");
        return bankDataList;
    }
}
