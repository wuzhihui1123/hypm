package cn.ifactory.hypm.filter;

import cn.ifactory.hypm.entity.Node;
import cn.ifactory.hypm.facade.service.NodeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2015/12/11.
 */
public class SpringTest {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        NodeService nodeService = (NodeService)ctx.getBean("nodeServiceImpl");

        Node node = nodeService.get("aed5af23-a807-45c0-aa80-c884c65eb74e");
        if(node != null) {
            System.out.println("查找到节点： "  + node.getName());
        }else {
            System.out.println("xxxxxxxxxxxx 没有找到节点 xxxxxxxxxxxxxxx");
        }

    }
}
