package com.test.util;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: kuoye.gao
 * @since: 2019/11/7 13:07
 * @history 2019/11/7 created by kuoye.gao
 */
public class DataFormatUtil {
    public static String XmlToJson(String xmlString){

        StringReader input = new StringReader(xmlString);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();
        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output.toString();
    }

    /**
     *
     * @param jsonString json字符串
     * @return
     */
    public static String JsonToXml(String jsonString){
        StringReader input = new StringReader(jsonString);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).repairingNamespaces(false).namespaceDeclarations(false).build();
        try {
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
            writer = new PrettyXMLEventWriter(writer);
            writer.add(reader);
            reader.close();
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // remove <?xml version="1.0" encoding="UTF-8"?>
        if (output.toString().length() >= 38) {
            return output.toString().substring(39);
        }
        return output.toString();
    }
    /**
     * @Description: 去掉xml中的换行和空格
     * @author wangyan_z
     * @date 2019年7月11日 下午4:05:40
     */
    public static String JsonToXmlReplaceBlank(String jsonString) {
        String str = DataFormatUtil.JsonToXml(jsonString);
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static void main(String[] args) {
        String STR_JSON = "{\n" +
                "\t\"root\" : {\n" +
                "\t\t\"head\" : {\n" +
                "\t\t\t\"OrgNum\" : \"0000101\",\n" +
                "\t\t\t\"TermiId\" : null\n" +
                "\t\t},\n" +
                "\t\t\"body\" : {\n" +
                "\t\t\t\"cust_id\" : \"QY201600006082\",\n" +
                "\t\t\t\"cust_no\" : 9542108405,\n" +
                "\t\t\t\"loan_mortgages\" : null\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}\n";
//        String STR_XML = "<root>\n" +
//                "\t<head>\n" +
//                "\t\t<OrgNum>0000101</OrgNum>\n" +
//                "\t\t<TermiId></TermiId>\n" +
//                "\t</head>\n" +
//                "\t<body>\n" +
//                "\t\t<cust_id>QY201600006082</cust_id>\n" +
//                "\t\t<cust_no>9542108405</cust_no>\n" +
//                "\t\t<loan_mortgages></loan_mortgages>\n" +
//                "\t</body>\n" +
//                "</root>"+"<root>\n" +
//                "\t<head>\n" +
//                "\t\t<OrgNum>0000101</OrgNum>\n" +
//                "\t\t<TermiId></TermiId>\n" +
//                "\t</head>\n" +
//                "\t<body>\n" +
//                "\t\t<cust_id>QY201600006082</cust_id>\n" +
//                "\t\t<cust_no>9542108405</cust_no>\n" +
//                "\t\t<loan_mortgages></loan_mortgages>\n" +
//                "\t</body>\n" +
//                "</root>";
//        String STR_XML="\t<head>\n" +
//                "\t\t<OrgNum>0000101</OrgNum>\n" +
//                "\t\t<TermiId></TermiId>\n" +
//                "\t</head>\n" +
//                "\t<body>\n" +
//                "\t\t<cust_id>QY201600006082</cust_id>\n" +
//                "\t\t<cust_no>9542108405</cust_no>\n" +
//                "\t\t<loan_mortgages></loan_mortgages>\n" +
//                "\t</body>\n" +"\t<body>\n" +
//                "\t\t<cust_id>QY201600006082</cust_id>\n" +
//                "\t\t<cust_no>9542108405</cust_no>\n" +
//                "\t\t<loan_mortgages></loan_mortgages>\n" +
//                "\t</body>\n";

        String STR_XML="<tns:data xmlns:apachesoap=\"http://xml.apache.org/xml-soap\" xmlns:wsdlsoap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:tns1=\"http://util.psdi\" xmlns:tns2=\"http://lang.java\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:tns=\"http://www.fiam.com/maximo\"><Bank/><Company>CGB20002</Company><Name>测试ksn111</Name><address/><bankAccount/><cellEmail/><legalPerson/><opType>I</opType><postCode/><property/><registerCapital/><shortName/></tns:data>";
//        System.out.println(JsonToXml(STR_JSON));
        System.out.println(XmlToJson(STR_XML));
    }
}
