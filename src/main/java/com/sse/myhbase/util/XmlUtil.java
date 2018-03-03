package com.sse.myhbase.util;

import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.exception.MyHBaseException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:09 2017/11/11
 * @modified by:
 */
public class XmlUtil {
    /**
     * logger
     */
    private static Logger logger = Logger.getLogger(XmlUtil.class);

    /**
     * @author: Cai Shunda
     * @description:  找到指定节点名称的最顶层节点(只遍历根节点的孩子节点)
     *                只找到一个才返回，找到多个不返回
     * @date: 20:13 2017/11/11
     */
    @Nullable
    public static Node findTopLevelNode(InputStream inputStream,String nodeName) {
        List<Node> nodeList = findTopLevelNodes(inputStream, nodeName);
        if (nodeList.size() == 1) {
            return nodeList.get(0);
        } else {
            return null;
        }
    }

    public static Node findTopLevelNode(String filePath, String nodeName) {
        Util.checkEmptyString(filePath);
        try {
            return findTopLevelNode(new BufferedInputStream(new FileInputStream(new File(filePath))), nodeName);
        } catch (FileNotFoundException e) {
            throw new MyHBaseException("can not find specified xml file file=" + filePath, e);
        }

    }
    /**
     * @author: Cai Shunda
     * @description: 找到指定节点名称的最顶层节点们(只遍历根节点的孩子节点)
     * @date: 20:43 2017/11/11
     */
    public static List<Node> findTopLevelNodes(InputStream inputStream, String nodeName) {
        List<Node> nodeList = new ArrayList<>();
        try {
            //获取Dom解析的工厂
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //是否删除空格
            documentBuilderFactory.setIgnoringElementContentWhitespace(true);
            //是否删除注释
            documentBuilderFactory.setIgnoringComments(true);

            //获取解析器
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //把xml文件传入解析器，并获取xml文档中的Document对象
            Document document = documentBuilder.parse(inputStream);
            //获取根结点
            Node rootNode = document.getDocumentElement();
            //获取根节点的孩子节点们
            NodeList childNodes = rootNode.getChildNodes();
            //在根节点的孩子节点中找到对应的节点名称的节点
            for (int i = 0; i < childNodes.getLength(); i ++) {
                if (childNodes.item(i).getNodeName().equals(nodeName)) {
                    nodeList.add(childNodes.item(i));
                }
            }

        }  catch (Exception e) {
            logger.error("parse error.", e);
            throw new MyHBaseException("parse error.", e);
        }
        return nodeList;
    }

    /**
     * @author: Cai Shunda
     * @description: 获取指定节点的指定属性的值
     * @date: 20:45 2017/11/11
     */
    @Nullable
    public static String getAttribute(Node node, String attrName) {
        Util.checkNull(node);
        Util.checkEmptyString(attrName);

        NamedNodeMap attrs = node.getAttributes();
        if (attrs == null) {
            return null;
        }
        Node attrNode = attrs.getNamedItem(attrName);
        if (attrNode == null) {
            return null;
        }
        return attrNode.getNodeValue();
    }
}
