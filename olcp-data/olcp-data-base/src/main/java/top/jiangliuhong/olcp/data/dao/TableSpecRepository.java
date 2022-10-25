package top.jiangliuhong.olcp.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import top.jiangliuhong.olcp.data.bean.dto.TableDTO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class TableSpecRepository {

    @Autowired
    private TableRepository tableRepository;

    public TableDTO findTable(String id) {
        Specification<TableDTO> spec = new Specification<TableDTO>() {

            @Override
            public Predicate toPredicate(Root<TableDTO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
//        tableRepository
        return null;
    }
}
