package top.jiangliuhong.olcp.data.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import top.jiangliuhong.olcp.common.jpa.CommonSpecRepository;
import top.jiangliuhong.olcp.data.bean.TableFieldDO;
import top.jiangliuhong.olcp.data.bean.query.TableFieldQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;

@Repository
public interface TableFieldRepository extends PagingAndSortingRepository<TableFieldDO, String>, CommonSpecRepository<TableFieldDO> {

    /**
     * 根据数据表ID查询
     *
     * @return 不为系统默认字段的列表
     */
    public List<TableFieldDO> findAllByTableIdAndSystemFieldIsTrue(String tableId);

    /**
     * 通过ID批量查询
     *
     * @param ids id集合
     * @return 字段列表
     */
    public List<TableFieldDO> findAllByIdIn(List<String> ids);

    /**
     * 根据数据表ID查询
     *
     * @param tableFieldQuery 查询对象
     * @return 字段集合
     */
    public default List<TableFieldDO> findAllByQuery(TableFieldQuery tableFieldQuery) {
        return this.findAll((Specification<TableFieldDO>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new LinkedList<>();
            if (StringUtils.isNotBlank(tableFieldQuery.getTableId())) {
                predicates.add(criteriaBuilder.equal(root.get("tableId"), tableFieldQuery.getTableId()));
            }
            if (tableFieldQuery.getTableIds() != null && tableFieldQuery.getTableIds().length > 0) {
                CriteriaBuilder.In<Object> tableId = criteriaBuilder.in(root.get("tableId"));
                for (String id : tableFieldQuery.getTableIds()) {
                    tableId.value(id);
                }
                predicates.add(tableId);
            }
            if (tableFieldQuery.isSystemField()) {
                predicates.add(criteriaBuilder.isTrue(root.get("systemField")));
            }
            if (tableFieldQuery.isUserField()) {
                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.isFalse(root.get("systemField")),
                                criteriaBuilder.isNull(root.get("systemField"))
                        )
                );
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
