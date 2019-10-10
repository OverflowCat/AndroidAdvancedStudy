package com.wscq.java.dealxml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019/3/1
 * @describe
 */
public class DealDimen {
    static List<Phone> phones;
    static Phone srcPhone = new Phone(1776, 1200);
    //    static Phone srcPhone = new Phone(3, 3);
//        static Phone phone = new Phone(2, 2);
    static Phone phone = new Phone(1860, 1080);

    public static void main(String[] args) {
        String filePath = "java/src/main/java/com/wscq/java/dealxml/dimen/dimens_src.xml";
        initPhones();
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            //更新xml
            for (Phone phone : phones) {
                outPutDimen(doc, (int) phone.height, (int) phone.width);
            }
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
            e1.printStackTrace();
        }
    }

    private static void initPhones() {
        phones = new ArrayList<>();
        phones.add(phone);
    }

    private static void outPutDimen(Document doc, int height, int width) throws TransformerException {
        updateElementValue(doc, height, width);
        doc.getDocumentElement().normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        File f = new File("java/src/main/java/com/wscq/java/dealxml/dimen/" +
                "values-" + height + "x" + width);
        if (!f.exists()) {
            f.mkdir();
        }
        StreamResult result = new StreamResult(new File(f, "dimens.xml"));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        System.out.println("XML file updated successfully");
    }

    private static void updateElementValue(Document doc, int height, int width) {
        NodeList employees = doc.getElementsByTagName("resources");
        Element emp = (Element) employees.item(0);
        NodeList dimenNodeList = emp.getElementsByTagName("dimen");
        int dimenLength = dimenNodeList.getLength();
        for (int j = 0; j < dimenLength; j++) {
            updateForDPI(height, width, dimenNodeList.item(j));
        }
    }
    //Lenovo TB-8X04F 1280*800 y*0.45 x*0.75 sp*0.7

    private static void updateForDPI(int height, int width, Node item) {
        String value = item.getTextContent();
        String name = item.getAttributes().getNamedItem("name").getNodeValue();
        if (value.contains("dp")) {
            String numValue = value.substring(0, value.length() - 2);
            String pxValue;
            double v;
            Double doubleNum = Double.valueOf(numValue);
            if (name.contains("y")) {
                v = (phone.height / srcPhone.height) * doubleNum * 2;
//                v = (1172.0/1776.0) * doubleNum * 2;
            } else {
                v = (phone.width / srcPhone.width) * doubleNum * 2;
            }

            int df = (int) v;
            pxValue = df + "px";
            item.setTextContent(pxValue);
        }
        if (value.contains("sp")) {
            String numValue = value.substring(0, value.length() - 2);
            String pxValue = (int) (phone.width / srcPhone.width * Double.valueOf(numValue) * 2) + "px";
            item.setTextContent(pxValue);
        }
    }

    static class Phone {
        double height;
        double width;

        public Phone(double height, double width) {
            this.height = height;
            this.width = width;
        }
    }
}
