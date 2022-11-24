package top.jiangliuhong.olcp.data.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import top.jiangliuhong.olcp.data.bean.GroovyFileDO;

import java.util.List;

@Repository
public interface GroovyFileRepository extends CrudRepository<GroovyFileDO, String> {

    /**
     * 查询应用下得某一个文件
     *
     * @param appId  应用ID
     * @param folder 文件目录
     * @param name   文件名
     * @return groovyFileDO
     */
    public GroovyFileDO findByAppIdAndFolderAndName(String appId, String folder, String name);

    /**
     * 查询应用下文件是否存在
     *
     * @param appId  应用ID
     * @param folder 文件目录
     * @param name   文件名
     * @return 大于等于1 ： 存在，小于1 ：不存在
     */
    public Integer countByAppIdAndFolderAndName(String appId, String folder, String name);

    public List<GroovyFileDO> findAllByAppIdIn(String[] appIds);

    @Query("select  new GroovyFileDO(g.name,g.folder,g.appId) from GroovyFileDO g where g.appId in (?1)")
    public List<GroovyFileDO> findFolderNameByAppId(String[] appIds);
}
