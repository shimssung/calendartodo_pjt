package com.example.calendartodo_pjt.openapi.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.example.calendartodo_pjt.openapi.domain.HistoryDaysDTO;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@Service
public class HistoryService {

    // public List<HistoryDaysDTO> parseXml(String xmlData) {
    //     List<HistoryDaysDTO> list = null;
    //     try {
    //         // JAXBContext 생성
    //         JAXBContext jaxbContext = JAXBContext.newInstance(HistoryDaysDTOList.class);
    //         System.out.println("service JAXVcontext");
            
    //         // Unmarshaller 생성
    //         Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    //         System.out.println("service Unmarshaller");
            
    //         // XML 데이터를 Java 객체로 변환
    //         StringReader reader = new StringReader(xmlData);
    //         HistoryDaysDTOList historyDaysDTOList = (HistoryDaysDTOList) unmarshaller.unmarshal(reader);
            
    //         // 리스트 반환
    //         list = historyDaysDTOList.getItems();
    //     } catch (JAXBException e) {
    //         e.printStackTrace();
    //     }
    //     return list;
    // }

    public List<HistoryDaysDTO> parseXml(String xmlData) {

        List<HistoryDaysDTO> list = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlData)));

            NodeList itemListNodes = document.getElementsByTagName("item");
            System.out.println("service itemlist node " + itemListNodes.getLength());

            for (int i = 0; i < itemListNodes.getLength(); i++) {
                Node itemListNode = itemListNodes.item(i);
    
                if (itemListNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemListElement = (Element) itemListNode;
    
                    HistoryDaysDTO startBusStop;
    
                    
                    String dateName = getElementValue(itemListElement, "dateName");
                    String locdate = getElementValue(itemListElement, "locdate");

                    
    
                    startBusStop = HistoryDaysDTO.builder()
                                                .dateName(dateName)
                                                .locdate(locdate)
                                                .build();
    
                    list.add(startBusStop);
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        
        return list;
    }

    private String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }

}

