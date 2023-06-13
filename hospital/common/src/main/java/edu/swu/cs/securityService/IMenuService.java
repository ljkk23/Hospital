package edu.swu.cs.securityService;



import com.baomidou.mybatisplus.extension.service.IService;
import edu.swu.cs.domain.securityEntity.Menu;
import edu.swu.cs.domain.securityEntity.MenuVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujian
 * @since 2022-11-16
 */
public interface IMenuService extends IService<Menu> {
    List<MenuVo> getRoutersByUserID(Long userID);


    List<String> getPermissions(Long userID);
    List<String> getRoles(Long userID);

}
