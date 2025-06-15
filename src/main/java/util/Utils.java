// Utils
// This Class is used for all common Functions of the browsers
// 08-07-2020
// ----------------------

package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
///// migrated imports for sorting methods
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class Utils {

    static ArrayList<String> treeMdsNames;
    static String compNode = "";
    static String nodeValue = "";
    static String mainNode = "";

    public static boolean setProperty(String key, String value) {
        boolean propertySaved;
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                                                                                                                                  //                                       ./src/test/resources/Properties/testconfig.properties
                                                                                                                                  PropertiesConfiguration.class).configure(
                                                                                                                                                                           params.properties()
                                                                                                                                                                                 .setFileName("./src/test/resources/TestProp/testconfig.properties"));
        Configuration config;
        try {
            config = builder.getConfiguration();
            config.setProperty(key, value);
            builder.save();
            propertySaved = true;
            System.out.println("property with key = " + key + " changed");
        } catch (ConfigurationException e) {
            System.out.println("property with key = " + key + " couldn't be changed");
            propertySaved = false;
        }

        return propertySaved;
    }

    public static String readFromXMLByXpath(String fileName, String ExpressionXpathFormat, String AttributeName) {
        String testinput = null;

        // String ExpressionFormat = "/MainDeclaration/Product/ProductID[@itemName='" +
        // id + "']/Name";
        // String ExpressionFormat = "//MainDeclaration/Product/ProductID";
        // String ExpressionFormat =
        // "//MainDeclaration/Product/MaterialInfo/MaterialList/Material";

        // String projectPath=System.getProperty("user.dir");
        // System.out.println(projectPath+File.separator+fileName);

        File fXmlFile = new File(fileName);
        System.out.println("fXmlFile: " + fXmlFile);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = String.format(ExpressionXpathFormat);
        System.out.println(expression);
        Node node = null;
        try {
            node = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        testinput = node != null ? (node.getTextContent()) : "cannot read the test case xml file";

        String nodeName = node.getNodeName();
        // String localnodeName = node.getLocalName();
        // String nodeValue = node.getNodeValue();

        System.out.println("Node name, Local, Value --> " + nodeName);

        // node.getChildNodes().item(1).getAttributes().getNamedItem("itemName").getTextContent();
        testinput = node.getAttributes().getNamedItem(AttributeName).getTextContent();

        return testinput;

    }

    public static ArrayList<String> readNodeValueinXMLByNodeName(String FileName, String NodeName,
                                                                 String AttributeName) {
        ArrayList<String> AttValue = new ArrayList<String>();
        try {

            File fXmlFile = new File(FileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            // System.out.println("Root element :" +
            // doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName(NodeName);
            // System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    AttValue.add(eElement.getAttribute(AttributeName));
                    // AttValue = eElement.getNodeValue();
                    // System.out.println("Value : " + AttValue);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return AttValue;
    }

    public static ArrayList<String> parseXml(String FileName) {
        try {

            treeMdsNames = new ArrayList<String>();
            File file = new File(FileName);

            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            // --System.out.println("Root element :" +
            // doc.getDocumentElement().getNodeName());
            // --System.out.println("----------------------------");

            if (doc.hasChildNodes()) {
                printNote(doc.getChildNodes());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        PrintArray(treeMdsNames);
        return treeMdsNames;
    }

    private static void printNote(NodeList nodeList) {

        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                // get node name and value  || tempNode.getNodeName() == "Substance" 
                if (tempNode.getNodeName() == "ProductID" || tempNode.getNodeName() == "Component" || tempNode.getNodeName() == "Material"
                    || tempNode.getNodeName() == "Amount") {
                    // System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
                    // System.out.println("Node Value =" + tempNode.getTextContent());

                    if (tempNode.hasAttributes()) {
                        // get attributes names and values
                        NamedNodeMap nodeMap = tempNode.getAttributes();
                        for (int i = 0; i < nodeMap.getLength(); i++) {
                            Node att = nodeMap.item(i);

                            if (att.getNodeName() == "name") {
                                mainNode = tempNode.getNodeName();
                            }
                            // System.out.println("attr name : " + att.getNodeName());
                            nodeValue = att.getNodeValue();
                            System.out.println("attr value : <" + mainNode + "> : " + nodeValue);
                            // if (att.getNodeName() == "UOM") {nodeValue = nodeValue + " ";}
                            compNode = nodeValue + compNode;
                            treeMdsNames.add(compNode.trim());
                        }
                    }

                }
            }
            if (tempNode.hasChildNodes()) {
                // loop again if has child nodes
                printNote(tempNode.getChildNodes());
            }

            // if (tempNode.getNodeName() == "ProductID" || tempNode.getNodeName() ==
            // "Material"|| tempNode.getNodeName() == "Substance")
            // {
            // --System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
            // }

        }
    }

    public static void PrintArray(ArrayList<String> Ar) {
        System.out.println("<-- Array -->" + Arrays.toString(Ar.toArray()) + "<--Array End-->");
    }

    public static void PrintArray(String[] Ar) {
        System.out.println("--------------- Array -----------------");
        System.out.println(" 1D Array --> " + Arrays.deepToString(Ar));
        System.out.println("--------------- Array End ----------------");
    }

    public static void PrintArray(String[][] Ar) {
        System.out.println(" 2D Array --> " + Arrays.deepToString(Ar));
    }

    public static boolean CompareArrays(String[][] Ar1, String[][] Ar2) {
        System.out.println("--------------- Compare 2D Arrays-----------------");
        PrintArray(Ar1);
        PrintArray(Ar2);
        System.out.println("--------------------------------------------------");
        return Arrays.deepEquals(Ar1, Ar2);
    }

    public static boolean CompareArrays(String[] Ar1, String[] Ar2) {
        System.out.println("--------------- Compare 1D Arrays-----------------");
        PrintArray(Ar1);
        PrintArray(Ar2);
        System.out.println("--------------------------------------------------");
        return Arrays.deepEquals(Ar1, Ar2);
    }

    public static boolean compareLists(ArrayList<String> List1, ArrayList<String> List2) {
        boolean matched;
        Collections.sort(List1);
        Collections.sort(List2);

        Utils.PrintArray(List1);
        Utils.PrintArray(List2);

        matched = List1.equals(List2);
        return matched;
    }

    public static boolean comapreStrings(String s1, String s2, String ValidatedItem) {
        boolean flag = false;
        // System.out.println(">>> start comparing strings <<<");
        // System.out.println("comparing values s1:[" + s1 + "] , s2:["+s2+"]");

        if (s1 == null) {
            s1 = "";
        }
        if (s2 == null) {
            s2 = "";
        }

        s1 = s1.trim();
        s2 = s2.trim();

        if (s1.equalsIgnoreCase(s2))
            flag = true;
        else
            flag = false;

        if (ValidatedItem.equalsIgnoreCase("")) {
            return flag;
        } else {
            if (flag == true)
                System.out.println(ValidatedItem + " : is validated successfully ");
            else
                System.out.println(ValidatedItem + " : is failed !! where the screen value: [" + s1 + "] and the expected value: [" + s2 + "]");
        }

        return flag;
    }

    public static boolean checkStringinString(String fullString, String substring) {

        if (fullString == null) {
            fullString = "";
        }
        if (substring == null) {
            substring = "";
        }

        if (fullString.contains(substring) == true) {
            System.out.println("the String [" + fullString + "] is containing the subString [" + substring + "]");
            return true;
        }
        System.out.println("the String [" + fullString + "] doesn't contain the subString [" + substring + "]");
        return false;
    }

    public static boolean checkStringinString(String fullString, String substring, String customMsg) {

        if (fullString == null) {
            fullString = "";
        }
        if (substring == null) {
            substring = "";
        }

        fullString = fullString.trim();
        substring = substring.trim();

        if (fullString.equalsIgnoreCase(substring) == true) {
            System.out.println("Successfully: " + customMsg);
            return true;
        }

        if (fullString.contains(substring) == true) {
            System.out.println("Successfully: " + customMsg);
            return true;
        }

        if (substring.contains(fullString) == true) {
            System.out.println("Successfully: " + customMsg);
            return true;
        }

        System.out.println("Failed: " + customMsg + ", FullString: [" + fullString + "] and Substring: [" + substring + "]");
        return false;
    }

    public static List<String> readTextFileByLines(String filePath, String fileName) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get(getAbsolutePathfromRelativePath(filePath + fileName)));

        return lines;
    }

    public static String getAbsolutePathfromRelativePath(String relativePath) {
        File relFilepath = new File(relativePath);
        String absPath = relFilepath.getAbsolutePath();
        System.out.println("Relative path" + relativePath + " --> AbsolutePath : " + absPath);
        return absPath;
    }

    // -------- Random Functions ---------------------- //
    public static String getRandomString(int NoOfChars) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int N = alphabet.length();
        Random RandomObj = new Random();
        char[] nameCharArr = new char[NoOfChars];

        for (int i = 0; i < NoOfChars; i++) {
            nameCharArr[i] = alphabet.charAt(RandomObj.nextInt(N));
        }

        String nameString = String.copyValueOf(nameCharArr);
        nameString = "AT" + nameString.toLowerCase();
        return nameString;
    }

    public static String getRandomStringNumber(int NoOfChars, int MaxRndNumber) {
        Random RandomObj = new Random();
        // Obtain a number between [0 - MaxRndNumber].
        int N = RandomObj.nextInt(MaxRndNumber);

        String generatedName = getRandomString(NoOfChars) + N;

        return generatedName;
    }

    public static String getRandomStringNumber(int NoOfChars) {
        // Random RandomObj = new Random();
        // Obtain a number between [0 - MaxRndNumber].
        // int N= RandomObj.nextInt(MaxRndNumber);

        String generatedName = getRandomString(NoOfChars) + getCurrentDate("ddMMHHmmss");

        return generatedName;
    }

    public static String getRandomNumber() {
        String generatedName = getCurrentDate("ddMMHHmmss");
        return generatedName;
    }

    // -------- Date Functions ---------------------- //
    public static String getCurrentDate(String format) {
        /* String MyDate;
         * LocalDateTime now = LocalDateTime.now();
         * /* now.getDayOfMonth(); now.getMonth(); now.getYear(); */

        /* MyDate = now.getMonth().getValue()+"/"+now.getDayOfMonth()+"/"+now.getYear();
         * System.out.println(" The current date: "+ MyDate); return MyDate ; */

        Format formatter = new SimpleDateFormat(format);
        String today = formatter.format(new Date());
        System.out.println(" The current date: " + today);
        return today;
    }

    public static String getCurrentDateSpecificTimeZone(String format, String country, String timeZone) {
        DateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        String today = formatter.format(new Date());

        System.out.println("Date and time in " + country + ": " + today);
        System.out.println(" The current date: " + today);
        return today;
    }

    public static String getCurrentDayMonthYear(String format) {
        // format
        // dd: return current day
        // mm: return current Month
        // yy : return current year
        // hr : return current hr
        // mi : return current minute
        // ss : return current second

        String MyDate = "";

        LocalDateTime now = LocalDateTime.now();

        /* now.getDayOfMonth(); now.getMonthValue(); now.getYear(); now.getHour();
         * now.getMinute(); now.getSecond() */

        if (format.equalsIgnoreCase("dd"))
            MyDate = Integer.toString(now.getDayOfMonth());
        else if (format.equalsIgnoreCase("mm"))
            MyDate = Integer.toString(now.getMonthValue());
        else if (format.equalsIgnoreCase("yy"))
            MyDate = Integer.toString(now.getYear());
        else if (format.equalsIgnoreCase("hr"))
            MyDate = Integer.toString(now.getHour());
        else if (format.equalsIgnoreCase("mi"))
            MyDate = Integer.toString(now.getMinute());
        else if (format.equalsIgnoreCase("ss"))
            MyDate = Integer.toString(now.getSecond());

        // MyDate = now.getMonth().getValue()+"/"+now.getDayOfMonth()+"/"+now.getYear();
        System.out.println(" The current : [" + format + "] is : " + MyDate);
        return MyDate;

    }

    public static String convertDateFormatToDBFromat(String InputDate) {

        // YYYY-MM-DD Format should be returned 
        String Day, Month, Year;

        String[] splittedDate = InputDate.split("/");

        Day = splittedDate[1];
        Month = splittedDate[0];
        Year = splittedDate[2];

        String formatedDate = Year + "-" + Month + "-" + Day;

        System.out.println("Old Date format :[" + InputDate + " : " + "MM/DD/YYYY" + "] --> [" +
            " New format ==> [" + formatedDate + " : " + "YYYY-MM-DD" + "]");

        return formatedDate;
    }

    public static String getCurrentDayMonthYear() {
        String mycurrddmmyyhrmiss = "";

        mycurrddmmyyhrmiss = getCurrentDayMonthYear("dd") + getCurrentDayMonthYear("mm") + getCurrentDayMonthYear("yy") + getCurrentDayMonthYear("hr")
            + getCurrentDayMonthYear("mi") + getCurrentDayMonthYear("ss");
        return mycurrddmmyyhrmiss;
    }

    public static Date convertToDate(String inputFormat, String outputFormat, String dateValue) {
        Date date = null;
        if (dateValue != null) {

            SimpleDateFormat fromFormat = new SimpleDateFormat(inputFormat);
            SimpleDateFormat toFormat = new SimpleDateFormat(outputFormat);

            try {

                String reformattedStr = toFormat.format(fromFormat.parse(dateValue));
                date = toFormat.parse(reformattedStr);
                System.out.println(" The date is: " + date);

            } catch (ParseException e) {
                System.out.println(" The date : " + dateValue + "couldn't be parsed");
            }
        }
        return date;

    }

    public static String convertDateFormatToAnotherDateFormat(String inputFormat, String outputFormat,
                                                              String dateValue) {
        Date retdate = null;
        String strDate = "";
        if (dateValue != null) {

            SimpleDateFormat fromFormat = new SimpleDateFormat(inputFormat);
            SimpleDateFormat toFormat = new SimpleDateFormat(outputFormat);

            try {

                String reformattedStr = toFormat.format(fromFormat.parse(dateValue));
                retdate = toFormat.parse(reformattedStr);

                strDate = toFormat.format(retdate);
                System.out.println("Old Date format :[" + dateValue + " : " + inputFormat + "] --> Date: [" + retdate
                    + "] and New format ==> [" + strDate + " : " + outputFormat + "]");

            } catch (ParseException e) {
                System.out.println(" The date : [" + dateValue + "] couldn't be parsed");
            }
        }
        return strDate;

    }

    public static boolean dateBetween(String dateValue, String dateFrom, String dateTo, String inputFormat,
                                      String outputFormat) {
        Date dateFromDate = null;
        Date dateToDate = null;
        Date checkDate;
        if (dateValue != null) {
            checkDate = Utils.convertToDate(inputFormat, outputFormat, dateValue);
            if (dateFrom != null) {
                dateFromDate = Utils.convertToDate(inputFormat, outputFormat, dateFrom);
            }
            if (dateTo != null) {
                dateToDate = Utils.convertToDate(inputFormat, outputFormat, dateTo);
            }
            if (checkDate != null && dateFrom != null && dateTo == null) {
                if (checkDate.after(dateFromDate) || checkDate.equals(dateFromDate)) {
                    return true;
                } else {
                    return false;
                }
            }
            if (checkDate != null && dateFromDate == null && dateToDate != null) {
                if (checkDate.before(dateToDate) || checkDate.equals(dateToDate)) {
                    return true;
                } else {
                    return false;
                }
            }
            if (checkDate != null && dateFromDate != null && dateToDate != null) {
                if (checkDate.equals(dateFromDate) || checkDate.equals(dateToDate)
                    || (checkDate.after(dateFromDate) && checkDate.before(dateToDate))) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static String AddDayMonthYeartoCurrentDate(String format, String calendarType, int calendarNo,
                                                      String timezone) {

        DateFormat formatter = new SimpleDateFormat(format);

        if (timezone.equalsIgnoreCase("")) {} else {
            formatter.setTimeZone(TimeZone.getTimeZone(timezone));
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        if (calendarType.equalsIgnoreCase("dd")) {
            c.add(Calendar.DAY_OF_YEAR, calendarNo);
        } else if (calendarType.equalsIgnoreCase("ww")) {
            c.add(Calendar.WEEK_OF_YEAR, calendarNo);
        } else if (calendarType.equalsIgnoreCase("mm")) {
            c.add(Calendar.MONTH, calendarNo);
        } else if (calendarType.equalsIgnoreCase("yy")) {
            c.add(Calendar.YEAR, calendarNo);
        }

        String MyDate = sdf.format(c.getTime());
        System.out.println("selected  day is " + MyDate);

        return MyDate;

    }
    // -------------------------------------------

    @SuppressWarnings("deprecation")
    public static void runBatchFile(String Filename) {
        try {
            System.out.println("============== RunBatchFile Started ===========");

            String command = "cmd /C start " + System.getProperty("user.dir") + "/" + Filename;
            System.out.println("command : [" + command + "]");
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(command);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));

            // read the output from the command
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println("Here is the standard output of the command:\n");
                System.out.println(s);
            }
            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                System.out.println("Here is the standard error of the command (if any):\n");
                System.out.println(s);
            }

            pr.destroy();

        } catch (IOException e) {
            System.out.println("Here is the standard error of the command (if any):\n");
            System.out.println(e.getMessage());
        }

        System.out.println("============== RunBatchFile Ended ===========");
    }

    @SuppressWarnings("deprecation")
    public static void runWindowsCommand(String command) {
        try {

            System.out.println("command : [" + command + "]");
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(command);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));

            // read the output from the command
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println("Here is the standard output of the command:\n");
                System.out.println(s);
            }
            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                System.out.println("Here is the standard error of the command (if any):\n");
                System.out.println(s);
            }
            pr.destroy();
        } catch (IOException e) {
            System.out.println("Here is the standard error of the command (if any):\n");
            System.out.println(e.getMessage());
        }

    }

    public static boolean doesFileExist(String fileFolderName, String fileName, String fileExtension, int numberOfRetries, boolean delete) {
        Boolean doesFileExit = false;
        String absolutePath = null;
        int i = 0;
        while (i < numberOfRetries) {
            try {

                File directory = new File(fileFolderName);
                // get all the files from a directory
                File[] fList = directory.listFiles();
                for (File file : fList) {
                    if (file.isFile() && fileName.contains(file.getName())) { //file.getName().contains(fileName)

                        System.out.println(file.getAbsolutePath());
                        absolutePath = file.getAbsolutePath();
                        doesFileExit = true;
                        break;
                    }
                }
                if (delete && absolutePath != null) {
                    //FileActions.deleteFile(absolutePath);	

                    File myObj = new File(absolutePath);
                    if (myObj.delete()) {
                        System.out.println("Deleted the file: " + myObj.getName());
                    } else {
                        System.out.println("Failed to delete the file.");
                    }

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (!doesFileExit) {
                try {
                    Thread.sleep(500);
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                }
            }

            i++;
        }
        return doesFileExit;
    }

    @SuppressWarnings("deprecation")
    public static void getCurrentThreaddata() {
        System.out.println("Current Thread Id: [" + Thread.currentThread().getId() + ", Name:"
            + Thread.currentThread().getName() + "- Periority:" + Thread.currentThread().getPriority() + "]");
    }

    public static int getCountOfSubStringInList(List<String> companyNames, String subString) {
        int count = 0;
        for (int i = 0; i < companyNames.size(); i++) {
            if (checkStringinString(companyNames.get(i), subString)) {
                count++;
            }
        }

        return count;
    }

    public static Integer getMaxNumber(ArrayList<String> list) {
        Integer maxNumber;
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            numbers.add(Integer.parseInt(list.get(i)));
        }
        maxNumber = Collections.max(numbers);
        return maxNumber;

    }

    public static ArrayList<String> sortListAlphabetically(ArrayList<String> unOrderedList, boolean asc) {

        Utils.PrintArray(unOrderedList);
        Collections.sort(unOrderedList);
        if (!asc)//sort descending
        {
            Collections.reverse(unOrderedList);
        }
        Utils.PrintArray(unOrderedList);
        return unOrderedList;
    }

    public static String getDaysBetweenDates(String dateFrom, String dateTo, String dateFormat) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate dateBefore = LocalDate.parse(dateFrom, format);
        LocalDate dateAfter = LocalDate.parse(dateTo, format);
        long days = java.time.temporal.ChronoUnit.DAYS.between(dateBefore, dateAfter);
        String daysbetween = String.valueOf(days);
        return daysbetween;
    }

    //////////////////////update daily execution suites common methods///////////////////
    public static List<List<String>> sortRecords(List<List<String>> records, int sortColumnIndex) {
        return records.stream()
                      .sorted(Comparator.comparing((List<String> r) -> r.get(sortColumnIndex)).reversed())
                      .collect(Collectors.toList());
    }

    public static List<List<String>> deleteColumns(List<List<String>> records, List<Integer> columnsToRemove) {
        List<Integer> sortedColumns = columnsToRemove.stream().sorted(Comparator.reverseOrder()).toList();
        List<List<String>> filteredRecords = new ArrayList<>();
        for (List<String> record : records) {
            List<String> filteredRecord = new ArrayList<>();
            for (int i = 0; i < record.size(); i++) {
                if (!sortedColumns.contains(i)) {
                    filteredRecord.add(record.get(i));
                }
            }
            filteredRecords.add(filteredRecord);
        }
        return filteredRecords;
    }

    public static List<List<String>> readCSV(String filePath) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (Reader reader = new FileReader(filePath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : csvParser) {
                List<String> record = new ArrayList<>();
                csvRecord.forEach(record::add);
                records.add(record);
            }
        }
        return records;
    }

    public static List<List<String>> filterRecords(List<List<String>> records) {
        return records.stream()
                      .filter(record -> record.stream().noneMatch(cell -> cell.equalsIgnoreCase("AfterMethod")))
                      .filter(record -> record.stream().noneMatch(cell -> cell.equalsIgnoreCase("BeforeMethod")))
                      .filter(record -> record.getFirst().toLowerCase().contains("passed") ||
                          record.getFirst().toLowerCase().contains("failed") ||
                          record.getFirst().toLowerCase().contains("broken") ||
                          record.getFirst().toLowerCase().contains("skipped"))
                      .collect(Collectors.toList());
    }

    static String passedColorHex = "#00b050"; //Green
    static String brokenColorHex = "#ffd966"; // Yellow
    static String failedColorHex = "#c00000"; // Red
    static String skippedColorHex = "#a5a5a5"; //Gray

    public static void writeSheet(Workbook workbook, List<List<String>> records, String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName);
        CellStyle greenStyle = createCellStyle(workbook, passedColorHex);
        CellStyle yellowStyle = createCellStyle(workbook, brokenColorHex);
        CellStyle redStyle = createCellStyle(workbook, failedColorHex);
        CellStyle grayStyle = createCellStyle(workbook, skippedColorHex);

        int rowIndex = 0;
        for (List<String> record : records) {
            Row row = sheet.createRow(rowIndex++);
            CellStyle rowStyle = null;
            for (String cellValue : record) {
                if (cellValue.equalsIgnoreCase("passed")) {
                    rowStyle = greenStyle;
                    break;
                } else if (cellValue.equalsIgnoreCase("broken")) {
                    rowStyle = yellowStyle;
                    break;
                } else if (cellValue.equalsIgnoreCase("failed")) {
                    rowStyle = redStyle;
                    break;
                } else if (cellValue.equalsIgnoreCase("skipped")) {
                    rowStyle = grayStyle;
                    break;
                }
            }

            for (int i = 0; i < record.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(record.get(i));
                if (rowStyle != null) {
                    cell.setCellStyle(rowStyle);
                }
            }
        }
    }

    private static XSSFCellStyle createCellStyle(Workbook workbook, String hexColor) {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        byte[] rgb = hexToRgb(hexColor);
        XSSFColor color = new XSSFColor(rgb, null);
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private static byte[] hexToRgb(String hexColor) {
        return new byte[] {
                            (byte) Integer.parseInt(hexColor.substring(1, 3), 16),
                            (byte) Integer.parseInt(hexColor.substring(3, 5), 16),
                            (byte) Integer.parseInt(hexColor.substring(5, 7), 16)
        };
    }

    public static String getFileNameWithoutExtension(String fileName) {
        return new File(fileName).getName().replaceFirst("[.][^.]+$", "");
    }

    public void copyRow(Row srcRow, Row destRow) {
        for (int i = 0; i < srcRow.getLastCellNum(); i++) {
            Cell oldCell = srcRow.getCell(i);
            Cell newCell = destRow.createCell(i);
            if (oldCell != null) {
                newCell.setCellValue(oldCell.getStringCellValue());
                newCell.setCellStyle(oldCell.getCellStyle());
            }
        }
    }
}
