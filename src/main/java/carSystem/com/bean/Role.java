package carSystem.com.bean;

public enum Role {
    ALL((short) 0, "ALL"),
    ADMIN((short) 1, "管理员"),
    CHANNEL((short) 2, "渠道"),
    VISITOR((short) 4, "游客");

    private short role;
    private String remark;

    Role(short role, String remark) {
        this.role = role;
        this.remark = remark;
    }

    public static String getRemark(short type) {
        for (Role role : Role.values()) {
            if (role.getRole() == type) {
                return role.getRemark();
            }
        }
        return null;
    }

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
