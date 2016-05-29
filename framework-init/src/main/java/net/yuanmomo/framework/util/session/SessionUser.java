package net.yuanmomo.framework.util.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Hongbin.Yuan on 2015-11-15 16:11.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionUser {
    /**
     * session 中存放 操作人 ID。
     */
    private long userID;

    /**
     * Seesion 中存放 操作人的用户名。
     */
    private String userName;


}
