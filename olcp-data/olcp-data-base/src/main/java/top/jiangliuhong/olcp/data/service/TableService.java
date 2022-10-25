package top.jiangliuhong.olcp.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jiangliuhong.olcp.data.dao.TableFieldRepository;
import top.jiangliuhong.olcp.data.dao.TableRepository;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private TableFieldRepository tableFieldRepository;

    public void addTable(){

    }
}
