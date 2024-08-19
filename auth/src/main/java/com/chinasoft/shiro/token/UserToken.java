package com.chinasoft.shiro.token;


import com.chinasoft.shiro.virtual.VirtualType;
import org.apache.shiro.authc.UsernamePasswordToken;
//token
public class UserToken extends UsernamePasswordToken {
    private VirtualType virtualType;

    public UserToken(final String username, final String password, VirtualType virtualType) {
        super(username, password);
        this.virtualType = virtualType;
    }

    public VirtualType getVirtualType() {
        return virtualType;
    }

    public void setVirtualType(VirtualType virtualType) {
        this.virtualType = virtualType;
    }
}
