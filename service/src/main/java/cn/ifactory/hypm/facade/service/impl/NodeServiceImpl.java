package cn.ifactory.hypm.facade.service.impl;

import cn.ifactory.hypm.dao.NodeDao;
import cn.ifactory.hypm.entity.Node;
import cn.ifactory.hypm.facade.service.NodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service("nodeService")
@Transactional
public class NodeServiceImpl implements NodeService {
    @Resource(name = "nodeDao")
    private NodeDao nodeDao;

    public Node save(Node node) {
        return nodeDao.save(node);
    }

    public Node get(String id) {
        return nodeDao.get(id);
    }

    public void update(Node node) {
        nodeDao.update(node);
    }

    public void merge(Node node) {
        nodeDao.merge(node);
    }

    public void delete(Node node) {
        //逻辑删除
        if (node == null) return;
        node.setUseable(false);
        nodeDao.update(node);
    }

    public Collection<Node> findAll() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("useable", true);
        Collection<Node> rets = nodeDao.findByParams(params);
        for (Node ret : rets) {
            int userCount = 0;
            try{
                userCount = ret.getUsers().size();
            }catch(Throwable e) {
                e.printStackTrace();
            }
            System.out.println("user size:" + userCount);
        }
        return rets;
    }


}
