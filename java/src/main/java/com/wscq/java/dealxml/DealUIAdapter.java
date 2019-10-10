package com.wscq.java.dealxml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019/3/1
 * @describe
 */
public class DealUIAdapter {

    public static void main(String[] args) {
        String filePath = "java/src/main/java/com/wscq/java/dealxml/dimen/dimens_ui_adapter.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            //更新xml
            updateElementValue(doc);

            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("java/src/main/java/com/wscq/java/dealxml/dimen/dimens_ui_adapter_copy.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("XML file updated successfully");
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
            e1.printStackTrace();
        }
    }

    private static void updateElementValue(Document doc) {
        NodeList employees = doc.getElementsByTagName("resources");
        Element emp = (Element) employees.item(0);
        NodeList dimenNodeList = emp.getElementsByTagName("dimen");
        int dimenLength = dimenNodeList.getLength();
        for (int j = 0; j < dimenLength; j++) {
            Node item = dimenNodeList.item(j);
            String aaa = item.getTextContent();
            String size = aaa;
            String name = item.getAttributes().getNamedItem("name").getNodeValue();
            System.out.println(name);
            if (size.contains("dp")) {
                size = size.substring(0, size.length() - 2);
                if (ReplyDp2X.isHeight(name)) {
                    size = "y" + size;
                } else {
                    size = "x" + size;
                }
                if (size.contains("-") || size.contains(".")) {
                    size = size.replaceAll("\\.", "_");
                    size = size.replaceAll("-", "_sub_");
                }
                item.setTextContent("@dimen/" + size);
                if (!sb.toString().contains(size)) {
                    sb.append("    <dimen name=\"" + size + "\">" + aaa + "</dimen>\n");
                }
                System.out.println(size);
            }
        }
        System.out.println(sb.toString());
    }

    private static void addElement(Document doc) {
        NodeList employees = doc.getElementsByTagName("Employee");
        Element emp = null;

        //loop for each employee
        for (int i = 0; i < employees.getLength(); i++) {
            emp = (Element) employees.item(i);
            Element salaryElement = doc.createElement("salary");
            salaryElement.appendChild(doc.createTextNode("10000"));
            emp.appendChild(salaryElement);
        }
    }

    private static void deleteElement(Document doc) {
        NodeList employees = doc.getElementsByTagName("Employee");
        Element emp = null;
        //loop for each employee
        for (int i = 0; i < employees.getLength(); i++) {
            emp = (Element) employees.item(i);
            Node genderNode = emp.getElementsByTagName("gender").item(0);
            emp.removeChild(genderNode);
        }

    }

    private static void updateAttributeValue(Document doc) {
        NodeList employees = doc.getElementsByTagName("Employee");
        Element emp = null;
        //loop for each employee
        for (int i = 0; i < employees.getLength(); i++) {
            emp = (Element) employees.item(i);
            String gender = emp.getElementsByTagName("gender").item(0).getFirstChild().getNodeValue();
            if (gender.equalsIgnoreCase("male")) {
                //prefix id attribute with M
                emp.setAttribute("id", "M" + emp.getAttribute("id"));
            } else {
                //prefix id attribute with F
                emp.setAttribute("id", "F" + emp.getAttribute("id"));
            }
        }
    }


    private static StringBuffer sb = new StringBuffer("    <dimen name=\"x37_5\">37.5dp</dimen>\n" +
            "    <dimen name=\"y37_5\">37.5dp</dimen>\n" +
            "    <dimen name=\"y475\">475dp</dimen>\n" +
            "    <dimen name=\"x17_5\">17.5dp</dimen>\n" +
            "    <dimen name=\"y45\">45dp</dimen>\n" +
            "    <dimen name=\"x50\">50dp</dimen>\n" +
            "    <dimen name=\"y10\">10dp</dimen>\n" +
            "    <dimen name=\"y1\">1dp</dimen>\n" +
            "    <dimen name=\"x5\">5dp</dimen>\n" +
            "    <dimen name=\"x10\">10dp</dimen>\n" +
            "    <dimen name=\"y22_5\">22.5dp</dimen>\n" +
            "    <dimen name=\"y38\">38dp</dimen>\n" +
            "    <dimen name=\"y36\">36dp</dimen>\n" +
            "    <dimen name=\"y16\">16dp</dimen>\n" +
            "    <dimen name=\"y28\">28dp</dimen>\n" +
            "    <dimen name=\"y8\">8dp</dimen>\n" +
            "    <dimen name=\"y30\">30dp</dimen>\n" +
            "    <dimen name=\"y25\">25dp</dimen>\n" +
            "    <dimen name=\"y4\">4dp</dimen>\n" +
            "    <dimen name=\"y35\">35dp</dimen>\n" +
            "    <dimen name=\"x19\">19dp</dimen>\n" +
            "    <dimen name=\"x157\">157dp</dimen>\n" +
            "    <dimen name=\"y22\">22dp</dimen>\n" +
            "    <dimen name=\"y17_5\">17.5dp</dimen>\n" +
            "    <dimen name=\"y21\">21dp</dimen>\n" +
            "    <dimen name=\"y18\">18dp</dimen>\n" +
            "    <dimen name=\"x23\">23dp</dimen>\n" +
            "    <dimen name=\"y23\">23dp</dimen>\n" +
            "    <dimen name=\"x1\">1dp</dimen>\n" +
            "    <dimen name=\"x8\">8dp</dimen>\n" +
            "    <dimen name=\"x89\">89dp</dimen>\n" +
            "    <dimen name=\"x67\">67dp</dimen>\n" +
            "    <dimen name=\"y0\">0dp</dimen>\n" +
            "    <dimen name=\"x0\">0dp</dimen>\n" +
            "    <dimen name=\"x24\">24dp</dimen>\n" +
            "    <dimen name=\"y65\">65dp</dimen>\n" +
            "    <dimen name=\"y82_5\">82.5dp</dimen>\n" +
            "    <dimen name=\"x28\">28dp</dimen>\n" +
            "    <dimen name=\"y55\">55dp</dimen>\n" +
            "    <dimen name=\"y15\">15dp</dimen>\n" +
            "    <dimen name=\"x80\">80dp</dimen>\n" +
            "    <dimen name=\"y5\">5dp</dimen>\n" +
            "    <dimen name=\"y42\">42dp</dimen>\n" +
            "    <dimen name=\"x270\">270dp</dimen>\n" +
            "    <dimen name=\"y60\">60dp</dimen>\n" +
            "    <dimen name=\"y137\">137dp</dimen>\n" +
            "    <dimen name=\"x60\">60dp</dimen>\n" +
            "    <dimen name=\"x35\">35dp</dimen>\n" +
            "    <dimen name=\"x30\">30dp</dimen>\n" +
            "    <dimen name=\"y300\">300dp</dimen>\n" +
            "    <dimen name=\"y190\">190dp</dimen>\n" +
            "    <dimen name=\"y39\">39dp</dimen>\n" +
            "    <dimen name=\"x57\">57dp</dimen>\n" +
            "    <dimen name=\"y140\">140dp</dimen>\n" +
            "    <dimen name=\"x175_5\">175.5dp</dimen>\n" +
            "    <dimen name=\"x300\">300dp</dimen>\n" +
            "    <dimen name=\"y27\">27dp</dimen>\n" +
            "    <dimen name=\"y11\">11dp</dimen>\n" +
            "    <dimen name=\"x20\">20dp</dimen>\n" +
            "    <dimen name=\"x45\">45dp</dimen>\n" +
            "    <dimen name=\"x13\">13dp</dimen>\n" +
            "    <dimen name=\"y13\">13dp</dimen>\n" +
            "    <dimen name=\"x3\">3dp</dimen>\n" +
            "    <dimen name=\"y130\">130dp</dimen>\n" +
            "    <dimen name=\"x100\">100dp</dimen>\n" +
            "    <dimen name=\"y91\">91dp</dimen>\n" +
            "    <dimen name=\"x7\">7dp</dimen>\n" +
            "    <dimen name=\"y80\">80dp</dimen>\n" +
            "    <dimen name=\"x90\">90dp</dimen>\n" +
            "    <dimen name=\"y40\">40dp</dimen>\n" +
            "    <dimen name=\"y52\">52dp</dimen>\n" +
            "    <dimen name=\"x170\">170dp</dimen>\n" +
            "    <dimen name=\"x25\">25dp</dimen>\n" +
            "    <dimen name=\"x_sub_240\">-240dp</dimen>\n" +
            "    <dimen name=\"y280\">280dp</dimen>\n" +
            "    <dimen name=\"x11_5\">11.5dp</dimen>\n" +
            "    <dimen name=\"x240\">240dp</dimen>\n" +
            "    <dimen name=\"y70\">70dp</dimen>\n" +
            "    <dimen name=\"x63\">63dp</dimen>\n" +
            "    <dimen name=\"x40\">40dp</dimen>\n" +
            "    <dimen name=\"x450\">450dp</dimen>\n" +
            "    <dimen name=\"y64\">64dp</dimen>\n" +
            "    <dimen name=\"y72\">72dp</dimen>\n" +
            "    <dimen name=\"x65\">65dp</dimen>\n" +
            "    <dimen name=\"y82\">82dp</dimen>\n" +
            "    <dimen name=\"x244\">244dp</dimen>\n" +
            "    <dimen name=\"y77\">77dp</dimen>\n" +
            "    <dimen name=\"y50\">50dp</dimen>\n" +
            "    <dimen name=\"x525\">525dp</dimen>\n" +
            "    <dimen name=\"y700\">700dp</dimen>\n" +
            "    <dimen name=\"y98\">98dp</dimen>\n" +
            "    <dimen name=\"y12\">12dp</dimen>\n" +
            "    <dimen name=\"x525_5\">525.5dp</dimen>\n" +
            "    <dimen name=\"y100\">100dp</dimen>\n" +
            "    <dimen name=\"x28_5\">28.5dp</dimen>\n" +
            "    <dimen name=\"y59\">59dp</dimen>\n" +
            "    <dimen name=\"y35_5\">35.5dp</dimen>\n" +
            "    <dimen name=\"y41_5\">41.5dp</dimen>\n" +
            "    <dimen name=\"y260\">260dp</dimen>\n" +
            "    <dimen name=\"y195\">195dp</dimen>\n" +
            "    <dimen name=\"x34_5\">34.5dp</dimen>\n" +
            "    <dimen name=\"y20\">20dp</dimen>\n" +
            "    <dimen name=\"x11\">11dp</dimen>\n" +
            "    <dimen name=\"y48\">48dp</dimen>\n" +
            "    <dimen name=\"x180\">180dp</dimen>\n" +
            "    <dimen name=\"y180\">180dp</dimen>\n" +
            "    <dimen name=\"x17\">17dp</dimen>\n" +
            "    <dimen name=\"x200\">200dp</dimen>\n" +
            "    <dimen name=\"y3\">3dp</dimen>\n" +
            "    <dimen name=\"x400\">400dp</dimen>\n" +
            "    <dimen name=\"y265\">265dp</dimen>\n" +
            "    <dimen name=\"x1_5\">1.5dp</dimen>\n" +
            "    <dimen name=\"y182\">182dp</dimen>\n" +
            "    <dimen name=\"x187\">187dp</dimen>\n" +
            "    <dimen name=\"y33\">33dp</dimen>\n" +
            "    <dimen name=\"x16\">16dp</dimen>\n" +
            "    <dimen name=\"y75\">75dp</dimen>\n" +
            "    <dimen name=\"x338\">338dp</dimen>\n" +
            "    <dimen name=\"x15\">15dp</dimen>\n" +
            "    <dimen name=\"y18_75\">18.75dp</dimen>\n" +
            "    <dimen name=\"x12\">12dp</dimen>\n" +
            "    <dimen name=\"x6\">6dp</dimen>\n" +
            "    <dimen name=\"x274_5\">274.5dp</dimen>\n" +
            "    <dimen name=\"y274_5\">274.5dp</dimen>\n" +
            "    <dimen name=\"y24\">24dp</dimen>\n" +
            "    <dimen name=\"y153\">153dp</dimen>\n" +
            "    <dimen name=\"y145\">145dp</dimen>\n" +
            "    <dimen name=\"x4\">4dp</dimen>\n" +
            "    <dimen name=\"y2\">2dp</dimen>\n" +
            "    <dimen name=\"x999\">999dp</dimen>\n" +
            "    <dimen name=\"y63\">63dp</dimen>\n" +
            "    <dimen name=\"y225\">225dp</dimen>\n" +
            "    <dimen name=\"y99\">99dp</dimen>\n" +
            "    <dimen name=\"x200_5\">200.5dp</dimen>\n" +
            "    <dimen name=\"x42\">42dp</dimen>\n" +
            "    <dimen name=\"x57_2\">57.2dp</dimen>\n" +
            "    <dimen name=\"y43\">43dp</dimen>\n" +
            "    <dimen name=\"x210\">210dp</dimen>\n" +
            "    <dimen name=\"y205\">205dp</dimen>\n" +
            "    <dimen name=\"y56\">56dp</dimen>\n" +
            "    <dimen name=\"x97\">97dp</dimen>\n" +
            "    <dimen name=\"y116\">116dp</dimen>\n" +
            "    <dimen name=\"y37\">37dp</dimen>\n" +
            "    <dimen name=\"y350\">350dp</dimen>\n" +
            "    <dimen name=\"x_sub_200\">-200dp</dimen>\n" +
            "    <dimen name=\"x165\">165dp</dimen>\n" +
            "    <dimen name=\"x420_0\">420.0dp</dimen>\n" +
            "    <dimen name=\"y100_0\">100.0dp</dimen>\n" +
            "    <dimen name=\"x30_0\">30.0dp</dimen>\n" +
            "    <dimen name=\"y30_0\">30.0dp</dimen>\n" +
            "    <dimen name=\"x15_0\">15.0dp</dimen>\n" +
            "    <dimen name=\"x310_0\">310.0dp</dimen>\n" +
            "    <dimen name=\"x45_0\">45.0dp</dimen>\n" +
            "    <dimen name=\"y27_0\">27.0dp</dimen>\n" +
            "    <dimen name=\"y9_0\">9.0dp</dimen>\n" +
            "    <dimen name=\"x5_0\">5.0dp</dimen>\n" +
            "    <dimen name=\"y1_5\">1.5dp</dimen>\n" +
            "    <dimen name=\"y75_0\">75.0dp</dimen>");
}
